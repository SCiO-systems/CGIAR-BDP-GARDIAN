package com.scio.quantum.harvesters.route.datasets;

import com.google.api.client.json.Json;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.scio.quantum.harvesters.exceptions.AlreadyProcessedException;
import com.scio.quantum.harvesters.exceptions.ExceptionHandler;
import com.scio.quantum.harvesters.exceptions.NoTitlePresent;
import com.scio.quantum.harvesters.models.dataset.DatasetModel;
import com.scio.quantum.harvesters.models.external.dataverse.resource.Dataverse;
import com.scio.quantum.harvesters.models.external.dataverse.result.DataverseSearchResult;
import com.scio.quantum.harvesters.process.*;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.gson.GsonDataFormat;
import org.apache.camel.http.common.HttpOperationFailedException;
import org.apache.camel.model.dataformat.JsonLibrary;

public class DataverseRouter extends RouteBuilder {

    private String filePool;
    private String dataType;
    private String center;

    public DataverseRouter(String filePool, String dataType, String center) {
        super();
        this.filePool = filePool;
        this.dataType = dataType;
        this.center = center;
    }

    @Override
    public void configure() throws Exception {
        datasetRoute();
    }

    private void datasetRoute(){
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Dataverse.class, new DataverseDeserializer())
                .create();

        GsonDataFormat dataverseFormat = new GsonDataFormat(gson, Dataverse.class);

        errorHandler(deadLetterChannel(
                "log:?level=ERROR&showAll=true&showCaughtException=true"));

        onException(AlreadyProcessedException.class)
                .handled(true)
                .process(new ExceptionHandler())
                .to("log:?level=ERROR&showAll=true&showCaughtException=true");

        onException(NoTitlePresent.class)
                .handled(true)
                .process(new ExceptionHandler())
                .to("log:?level=ERROR&showAll=true&showCaughtException=true");

        onException(HttpOperationFailedException.class)
                .handled(true)
                .process(new ExceptionHandler())
                .to("log:?level=ERROR&showAll=true&showCaughtException=true");

        onException(Exception.class)
                .handled(true)
                .process(new ExceptionHandler())
                .to("log:?level=ERROR&showAll=true&showCaughtException=true");

        from("timer://startDV?repeatCount=1")
                .routeId("Dataverse Router")
                .setProperty("FILE_POOL",constant(this.filePool))
                .process(new PopulateDataverseRootURLsProcessor())
                .split(body())
                .process(new InitializeProperties())
                .setProperty("INDEX",simple("${exchangeProperty.FILE_POOL}"+System.getProperty("file.separator")+"${exchangeProperty.contentProviderName}_DATAVERSE.idx"))
                .bean(DataverseBean.class, "buildHarvestList")
                .split(body())
                .enrich()
                .simple("https://dataverse.harvard.edu/api/datasets/export?exporter=dataverse_json&persistentId=${in.body}")
                .unmarshal(dataverseFormat)
                .process(new DataverseCollectionProcessor())
                .marshal().json(JsonLibrary.Gson, DatasetModel.class)
                .process(new Base64EncoderProcessor())
                .setHeader(Exchange.FILE_NAME, simple("DATASETS_${exchangeProperty.contentProviderID}_${exchangeProperty.DATE}.json"))
                .transform(body().append(System.getProperty("line.separator")))
                .to("file://" + filePool + "?charset=utf-8&fileExist=append")
                .process(new InternalIdProcessor())
                .setHeader(Exchange.FILE_NAME, simple("${exchangeProperty.contentProviderID}_DATAVERSE.idx"))
                .transform(body().append(System.getProperty("line.separator")))
                .to("file://" + filePool + "?charset=utf-8&fileExist=append");

        /*from("timer://startGenericDV?repeatCount=1")
                .routeId("Generic Dataverse Router")
                .setProperty("FILE_POOL",constant(this.filePool))
                .process(new PopulateDataverseSearchCentersProcessor())
                .split(body())
                .process(new InitializeProperties())
                .setProperty("INDEX",simple("${exchangeProperty.FILE_POOL}"+System.getProperty("file.separator")+"${exchangeProperty.contentProviderName}_GENERIC_DATAVERSE.idx"))
                .pollEnrich()
                .simple("${exchangeProperty.ROOT_URL}")
                .unmarshal().json(JsonLibrary.Gson,DataverseSearchResult.class)
                .bean(DataverseBean.class, "buildSimpleHarvestList")
                .split(body())
                .enrich()
                .simple("https://dataverse.harvard.edu/api/datasets/export?exporter=dataverse_json&persistentId=${in.body}")
                .unmarshal(dataverseFormat)
                .process(new DataverseCollectionProcessor())
                .marshal().json(JsonLibrary.Gson, DatasetModel.class)
                .process(new Base64EncoderProcessor())
                .setHeader(Exchange.FILE_NAME, simple("DATASETS_${exchangeProperty.contentProviderID}_${exchangeProperty.DATE}_GENERIC.json"))
                .transform(body().append(System.getProperty("line.separator")))
                .to("file://" + filePool + "?charset=utf-8&fileExist=append")
                .process(new InternalIdProcessor())
                .setHeader(Exchange.FILE_NAME, simple("${exchangeProperty.contentProviderID}_GENERIC_DATAVERSE.idx"))
                .transform(body().append(System.getProperty("line.separator")))
                .to("file://" + filePool + "?charset=utf-8&fileExist=append");

        /*from("timer://startCrudeDV?repeatCount=1")
                .routeId("Crude Dataverse Router")
                .setProperty("FILE_POOL",constant(this.filePool))
                .process(new PopulateCrudeDataverseProcessor())
                .split(body())
                .process(new InitializeProperties())
                .setProperty("INDEX",simple("${exchangeProperty.FILE_POOL}"+System.getProperty("file.separator")+"${exchangeProperty.contentProviderID}_DATAVERSE_CRUDE.idx"))
                .bean(DataverseBean.class, "buildCrudeHarvestList")
                .split(body())
                .enrich()
                .simple("${exchangeProperty.exporterURL}${in.body}")
                .unmarshal(dataverseFormat).log("Processing Started")
                .process(new DataverseCollectionProcessor())
                .marshal().json(JsonLibrary.Gson, DatasetModel.class)
                .process(new Base64EncoderProcessor()).log("Encoding Complete")
                .setHeader(Exchange.FILE_NAME, simple("DATASETS_${exchangeProperty.contentProviderID}_${exchangeProperty.DATE}_DATAVERSE_CRUDE.json"))
                .transform(body().append(System.getProperty("line.separator")))
                .to("file://" + filePool + "?charset=utf-8&fileExist=append")
                .process(new InternalIdProcessor())
                .setHeader(Exchange.FILE_NAME, simple("${exchangeProperty.contentProviderID}_DATAVERSE_CRUDE.idx"))
                .transform(body().append(System.getProperty("line.separator")))
                .to("file://" + filePool + "?charset=utf-8&fileExist=append");
            */
    }
}
