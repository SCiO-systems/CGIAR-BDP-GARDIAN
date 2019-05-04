package com.scio.quantum.harvesters.route.datasets;

import com.scio.quantum.harvesters.exceptions.AlreadyProcessedException;
import com.scio.quantum.harvesters.exceptions.ExceptionHandler;
import com.scio.quantum.harvesters.exceptions.NoTitlePresent;
import com.scio.quantum.harvesters.models.dataset.DatasetModel;
import com.scio.quantum.harvesters.models.external.ckan.resources.iita.CKANIITAPackageResources;
import com.scio.quantum.harvesters.models.external.ckan.resources.ilri.CKANILRIPackageResources;
import com.scio.quantum.harvesters.models.external.ckan.result.CKANSearchResult;
import com.scio.quantum.harvesters.process.*;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.http.common.HttpOperationFailedException;
import org.apache.camel.model.dataformat.JsonLibrary;

public class CKANRouter extends RouteBuilder {

    private String filePool;

    public CKANRouter(String filePool) {
        super();
        this.filePool = filePool;
    }

    @Override
    public void configure() throws Exception {

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
                .to("log:?level=ERROR&showAll=true&showCaughtException=true");

        from("timer://startCKAN?repeatCount=1")
                .routeId("CKAN Route IITA")
                .setProperty("FILE_POOL",constant(this.filePool))
                .process(new PopulateCKANSearchCentersProcessor())
                .split(body())
                .process(new InitializeProperties())
                .setProperty("INDEX",simple("${exchangeProperty.FILE_POOL}"+System.getProperty("file.separator")+"${exchangeProperty.contentProviderID}_CKAN.idx"))
                .pollEnrich()
                .simple("${exchangeProperty.ROOT_URL}")
                .unmarshal().json(JsonLibrary.Gson,CKANSearchResult.class)
                .bean(CKANBean.class, "buildHarvestList")
                .split(body()).log("ID: ${in.body}")
                //.throttle(1).timePeriodMillis(10000)
                .enrich()
                .simple("${exchangeProperty.exporterURL}${in.body}")
                .unmarshal().json(JsonLibrary.Gson,CKANIITAPackageResources.class)
                .bean(CKANBean.class,"buildResultList")
                .split(body())
                .process(new CKANCollectionProcessor())
                .marshal().json(JsonLibrary.Gson, DatasetModel.class)
                .process(new Base64EncoderProcessor())
                .setHeader(Exchange.FILE_NAME, simple("DATASETS_${exchangeProperty.contentProviderID}_${exchangeProperty.DATE}_CKAN.json"))
                .transform(body().append(System.getProperty("line.separator")))
                .to("file://" + filePool + "?charset=utf-8&fileExist=append")
                .process(new InternalIdProcessor())
                .setHeader(Exchange.FILE_NAME, simple("${exchangeProperty.contentProviderID}_CKAN.idx"))
                .transform(body().append(System.getProperty("line.separator")))
                .to("file://" + filePool + "?charset=utf-8&fileExist=append");

        /*from("timer://startCKANILRI?repeatCount=1")
                .routeId("CKAN Route ILRI")
                .setProperty("FILE_POOL",constant(this.filePool))
                .process(new PopulateCKANSearchCentersProcessor())
                .split(body())
                .process(new InitializeProperties())
                .setProperty("INDEX",simple("${exchangeProperty.FILE_POOL}"+System.getProperty("file.separator")+"${exchangeProperty.contentProviderID}_CKAN.idx"))
                .pollEnrich()
                .simple("${exchangeProperty.ROOT_URL}")
                .unmarshal().json(JsonLibrary.Gson,CKANSearchResult.class)
                .bean(CKANBean.class, "buildHarvestList")
                .split(body()).log("ID: ${in.body}")
                //.throttle(1).timePeriodMillis(10000)
                .enrich()
                .simple("${exchangeProperty.exporterURL}${in.body}")
                .unmarshal().json(JsonLibrary.Gson,CKANILRIPackageResources.class)
                .bean(CKANBean.class,"buildResultList")
                .split(body())
                .process(new CKANCollectionProcessor())
                .marshal().json(JsonLibrary.Gson, DatasetModel.class)
                .process(new Base64EncoderProcessor())
                .setHeader(Exchange.FILE_NAME, simple("DATASETS_${exchangeProperty.contentProviderID}_${exchangeProperty.DATE}_CKAN.json"))
                .transform(body().append(System.getProperty("line.separator")))
                .to("file://" + filePool + "?charset=utf-8&fileExist=append")
                .process(new InternalIdProcessor())
                .setHeader(Exchange.FILE_NAME, simple("${exchangeProperty.contentProviderID}_CKAN.idx"))
                .transform(body().append(System.getProperty("line.separator")))
                .to("file://" + filePool + "?charset=utf-8&fileExist=append");*/

    }
}
