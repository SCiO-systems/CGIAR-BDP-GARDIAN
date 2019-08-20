package com.scio.quantum.ai.pii.route;

import com.scio.quantum.ai.pii.aggregations.DummyJobsAggregator;
import com.scio.quantum.ai.pii.exceptions.ExceptionHandler;
import com.scio.quantum.ai.pii.process.*;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.model.rest.RestBindingMode;
import org.restlet.data.MediaType;

import javax.ws.rs.BadRequestException;
import java.util.Map;


public class APIRouter extends RouteBuilder {

    private String ip;

    public APIRouter(String ip) {
        this.ip = ip;
    }

    @Override
    public void configure() throws Exception {

        onException(Exception.class)
                .handled(true)
                .process(new ExceptionHandler())
                .to("log:?level=ERROR&showAll=true&showCaughtException=true");


        onException(BadRequestException.class)
                .handled(true);

        restConfiguration()
                .component("restlet")
                .host(ip)
                .port("9067")
                .bindingMode(RestBindingMode.off);


        rest("/api").description("PII Basic API")
                .post("/submitJob")
                    .description("Submit a PII Task to PII Queue")
                    .consumes(String.valueOf(MediaType.APPLICATION_JSON))
                    .produces(String.valueOf(MediaType.APPLICATION_JSON))
                .to("direct:submitJob")
                .get("/runningJobs")
                    .description("Get Running Jobs")
                    .produces(String.valueOf(MediaType.APPLICATION_JSON))
                .to("direct:runningJobs")
                .get("/completedJobs")
                .description("Get Complete Jobs")
                .produces(String.valueOf(MediaType.APPLICATION_JSON))
                .to("direct:completedJobs")
                .get("/getCompletedJob/{id}")
                    .description("Get PII Report with given id")
                    .produces(String.valueOf(MediaType.APPLICATION_JSON))
                .to("direct:getCompletedJob");

        from("direct:getCompletedJob")
                .setBody().simple("{\"Job.JobID\": \"${in.header.id}\"}")
                .to("mongodb3:mongoConnection?database=pii_database" +
                        "&collection=completedJobs&operation=findOneByQuery")
                .process(new DocumentToJsonProcessor())
                .process(new BuildReportResponse());

        from("direct:runningJobs")
                .to("mongodb3:mongoConnection?database=pii_database" +
                        "&collection=submittedJobs&operation=findAll")
                .split(body()).aggregationStrategy(new DummyJobsAggregator())
                .process(new DocumentToJsonProcessor())
                .end()
                .process(new BuildJobsListResponse());

        from("direct:completedJobs")
                .to("mongodb3:mongoConnection?database=pii_database" +
                        "&collection=completedJobs&operation=findAll")
                .split(body()).aggregationStrategy(new DummyJobsAggregator())
                .process(new DocumentToJsonProcessor())
                .end()
                .process(new BuildJobsListResponse());


        from("direct:submitJob")
                .process(new ValidateRESTRequestProcessor())
                .marshal().json(JsonLibrary.Gson,Map.class)
                .process(new ProduceJobProcessor())
                .to("kafka:" +
                        "pii_requests" +
                        "?brokers="+this.ip+":9092"+
                        "&enableIdempotence=true" +
                        "&maxInFlightRequest=1" +
                        "&retries=1" +
                        "&requestRequiredAcks=all")
                .process(new InsertJobProcessor())
                .to("mongodb3:mongoConnection?database=pii_database" +
                        "&collection=submittedJobs&operation=insert")
                .process(new BuildSuccessResponse());

    }
}
