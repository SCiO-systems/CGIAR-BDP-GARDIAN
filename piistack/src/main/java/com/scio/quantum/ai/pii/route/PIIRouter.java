package com.scio.quantum.ai.pii.route;

import com.scio.quantum.ai.pii.aggregations.FoundTermsAggregator;
import com.scio.quantum.ai.pii.aggregations.NERAggregator;
import com.scio.quantum.ai.pii.aggregations.NamedEntityAggregator;
import com.scio.quantum.ai.pii.aggregations.OntologyAggregator;
import com.scio.quantum.ai.pii.exceptions.ExceptionHandler;
import com.scio.quantum.ai.pii.models.job.Job;
import com.scio.quantum.ai.pii.models.report.SimpleReport;
import com.scio.quantum.ai.pii.process.*;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.bson.Document;

import javax.ws.rs.BadRequestException;
import java.io.FileNotFoundException;
import java.util.Map;

public class PIIRouter extends RouteBuilder {

    private String elasticsearchClusterName;
    private String elasticsearchIP;
    private String kafkaBroker;

    public PIIRouter(String elasticsearchClusterName, String elasticsearchIP, String kafkaBroker) {
        this.elasticsearchClusterName = elasticsearchClusterName;
        this.elasticsearchIP = elasticsearchIP;
        this.kafkaBroker = kafkaBroker;
    }

    @Override
    public void configure() throws Exception {

        onException(Exception.class)
                .handled(true)
                .process(new ExceptionHandler())
                .log("log:?level=ERROR&showAll=false&showCaughtException=false");

        onException(FileNotFoundException.class)
                .handled(true)
                .process(new ExceptionHandler())
                .log("log:?level=ERROR&showAll=false&showCaughtException=false");

        from("kafka:"+this.kafkaBroker+"?topic=pii_requests&autoOffsetReset=earliest&groupId=default_group"+
                "&autoCommitEnable=true&allowManualCommit=true&consumersCount=3&maxPollRecords=1")
                .routeId("PII-Message-Bus")
                .unmarshal().json(JsonLibrary.Gson,Map.class)
                .process(new JobRequestProcessor())
                .to("direct:prepareJob");


        from("direct:prepareJob")
                .routeId("PII-D-IN")
                .setProperty("UIIP",constant(this.elasticsearchIP))
                .process(new SetPathProcessor())
                .process(new IdentifyFileTypesProcessor())
                .choice()
                    .when(simple("${exchangeProperty.MODE} == 'recall'"))
                    .split(body()).aggregationStrategy(new NERAggregator())
                        .to("direct:detectPII-LVL-1")
                    .end()
                    .process(new CompileReportProcessor())
                    .marshal().json(JsonLibrary.Gson,SimpleReport.class)
                    .unmarshal().json(JsonLibrary.Gson,Document.class)
                    .to("mongodb3:mongoConnection?database=pii_database" +
                            "&collection=completedJobs&operation=insert")
                    .process(new PrepareEmailReportProcessor())
                    .to("smtp://" +
                            "mailserver:587?" +
                            "username=&" +
                            "password=&" +
                            "to=${exchangeProperty.EMAIL}"+
                            "from=&"+
                            "mail.smtp.auth=true&"+
                            "mail.smtp.starttls.enable=true&"+
                            "subject=PII Report"
                    )
                    .process(new RemoveCompletedJobProcessor())
                    .to("mongodb3:mongoConnection?database=pii_database" +
                        "&collection=submittedJobs&operation=remove")
                .endChoice()
                .otherwise()
                    .split(body()).aggregationStrategy(new NERAggregator())
                        .to("direct:detectPII-LVL-1")
                    .end()
                    .split(body()).aggregationStrategy(new OntologyAggregator())
                        .to("direct:detectPII-LVL-2")
                    .end()
                .end()
                .process(new CompileReportProcessor())
                .marshal().json(JsonLibrary.Gson,SimpleReport.class)
                .unmarshal().json(JsonLibrary.Gson,Document.class)
                .to("mongodb3:mongoConnection?database=pii_database" +
                        "&collection=completedJobs&operation=insert")
                .process(new PrepareEmailReportProcessor())
                .to("smtp://" +
                        "in-v3.mailjet.com:587?" +
                        "username=d1596037af3407506abbf6b77a4cc9c8&" +
                        "password=8ba50d3c5224ec53dab8c11f1f570334&" +
                        "to=${exchangeProperty.EMAIL}"+
                        "from=info@scio.systems&"+
                        "mail.smtp.auth=true&"+
                        "mail.smtp.starttls.enable=true&"+
                        "subject=PII Report for ${exchangeProperty.title}"
                        )
                .process(new RemoveCompletedJobProcessor())
                .to("mongodb3:mongoConnection?database=pii_database" +
                        "&collection=submittedJobs&operation=remove");

        from("direct:detectPII-LVL-1")
                .routeId("PII-D-LVL1")
                .process(new DetectPIIProcessor())
                .end();

        from("direct:detectPII-LVL-2")
                .routeId("PII-D-LVL2")
                .setProperty("CandidateFile",body())
                .multicast(new NamedEntityAggregator())
                .parallelProcessing()
                    .to("direct:agrovoc","direct:geonames","direct:whitelist")
                .end()
                    .process(new DeduplicateNamedEntitiesProcessor())
                .end();

        from("direct:agrovoc")
                .routeId("agrovoc")
                .setProperty("INDEX",constant("agrovoc"))
                .setProperty("FIELD",constant("label"))
                .process(new PrepareNamedEntitiesProcessor())
                .split(body()).aggregationStrategy(new FoundTermsAggregator())
                    .process(new QueryLemmaProcessor())
                    .to("elasticsearch5://"+this.elasticsearchClusterName+"?ip="+this.elasticsearchIP+
                            "&user=elastic&password=changeme&operation=SEARCH" +
                            "&clientTransportSniff=false&port=9300")
                    .process(new HitsTermProcessor())
                .end()
                .bean(NamedEntityResultBean.class,"returnResult");

        from("direct:geonames")
                .routeId("geonames")
                .setProperty("INDEX",constant("geonames"))
                .setProperty("FIELD",constant("country"))
                .process(new PrepareNamedEntitiesProcessor())
                .split(body()).aggregationStrategy(new FoundTermsAggregator())
                    .process(new QueryLemmaProcessor())
                    .to("elasticsearch5://"+this.elasticsearchClusterName+"?ip="+this.elasticsearchIP+
                        "&user=elastic&password=changeme&operation=SEARCH" +
                            "&clientTransportSniff=false&port=9300")
                    .process(new HitsTermProcessor())
                .end()
                .bean(NamedEntityResultBean.class,"returnResult");

        from("direct:whitelist")
                .routeId("whitelist")
                .setProperty("INDEX",constant("whitelist"))
                .setProperty("FIELD",constant("term"))
                .process(new PrepareNamedEntitiesProcessor())
                    .split(body()).aggregationStrategy(new FoundTermsAggregator())
                    .process(new QueryLemmaProcessor())
                    .to("elasticsearch5://"+this.elasticsearchClusterName+"?ip="+this.elasticsearchIP+
                        "&user=elastic&password=changeme&operation=SEARCH&" +
                            "clientTransportSniff=false&port=9300")
                    .process(new HitsTermProcessor())
                .end()
                .bean(NamedEntityResultBean.class,"returnResult");

        //from("file://" + + "?delay=1000&idempotent=true")

    }
}
