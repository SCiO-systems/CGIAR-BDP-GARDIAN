/*
 * Copyright (C) 2019 SCiO
 *  This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *  This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 */

package com.scio.quantum.modelvalidator.route;

import com.scio.quantum.modelvalidator.exceptions.ExceptionHandler;

import com.scio.quantum.modelvalidator.models.dataset.DatasetModel;
import com.scio.quantum.modelvalidator.models.publication.PublicationModel;
import com.scio.quantum.modelvalidator.process.Base64DecoderProcessor;
import com.scio.quantum.modelvalidator.process.ModelValidationBean;
import com.scio.quantum.modelvalidator.process.PrepareMessageProcessor;
import com.scio.quantum.modelvalidator.process.PrepareMongoUpdateProcessor;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;

public class ColdModelValidationRouter extends RouteBuilder {

    private String filePool;
    private String mongoDatabase = "raw";
    private String kafkaBroker;
    private String dataType;

    public ColdModelValidationRouter(String filePool, String kafkaBroker, String dataType) {
        super();
        this.filePool = filePool;
        this.kafkaBroker = kafkaBroker;
        this.dataType = dataType;
    }

    @Override
    public void configure() throws Exception {
        try {
            if(dataType.equalsIgnoreCase("publication")){
                publicationRoute();
            }else if(dataType.equalsIgnoreCase("dataset")){
                datasetRoute();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void publicationRoute() {

        errorHandler(deadLetterChannel(
                "log:?level=ERROR&showAll=true&showCaughtException=true"));

        onException(Exception.class)
                .handled(true)
                .process(new ExceptionHandler())
                .to("log:?level=ERROR&showAll=true&showCaughtException=true");

       /* from("file://" + filePool + "?charset=iso-8859-1&delay=1000&idempotent=true")
                .log("BATCH SIZE: ${header.CamelBatchSize}")
                .log("BATCH INDEX: ${header.CamelBatchIndex}")
                .routeId("ModelValidationHarvestersRawRoute").log(LoggingLevel.INFO, "ModelValidationHarvestersRawRoute")
                .split(body().tokenize("\n")).streaming()
                .process(new Base64DecoderProcessor())
                .unmarshal().json(JsonLibrary.Gson, PublicationModel.class)
                .bean(ModelValidationBean.class, "checkModel")
                .to("mongodb3:mongoConnection?database=" + mongoDatabase + "&collection="+ dataType +"&operation=insert")
                .to("seda:findAll?size=100&blockWhenFull=true&waitForTaskToComplete=Always&timeout=0").log("Triggering ModelValidationRawKafkaRoute");*/


        //Read from mongo

        from("timer://coldRestore?repeatCount=1")
            .from("direct:coldAll")
                .to("mongodb3:mongoConnection?database=" + mongoDatabase + "&collection="+ dataType +"&operation=findAll")
                .split(body()).streaming()
                .process(new PrepareMessageProcessor())
                .to("kafka:production_deduplicate_bus_" + dataType + "?brokers=" + kafkaBroker + "&maxInFlightRequest=1&retries=1&requestRequiredAcks=all")
                .process(new PrepareMongoUpdateProcessor())
                .to("mongodb3:mongoConnection?database=" + mongoDatabase + "&collection="+dataType+"&operation=update");



        /*from("seda:findAll")
                .routeId("ModelValidationRawKafkaRoute").log(LoggingLevel.INFO, "ModelValidationRawKafkaRoute")
                .setBody().constant("{\"PubMetadata.hasBeenTransferred\": \"false\"}")
                .to("mongodb3:mongoConnection?database=" + mongoDatabase + "&collection=" + dataType + "&operation=findAll")
                .split(body()).streaming()
                .process(new PrepareMessageProcessor())
                .to("kafka:production_deduplicate_bus_" + dataType + "?brokers=" + kafkaBroker + "&maxInFlightRequest=1&retries=1&requestRequiredAcks=all")
                .process(new PrepareMongoUpdateProcessor())
                .to("mongodb3:mongoConnection?database=" + mongoDatabase + "&collection="+dataType+"&operation=update");*/

    }

    private void datasetRoute() {

        errorHandler(deadLetterChannel(
                "log:?level=ERROR&showBody=true$showException=true"));

        onException(Exception.class)
                .handled(true)
                .process(new ExceptionHandler())
                .to("log:?level=ERROR&showBody=true$showException=true");

        from("file://" + filePool + "?charset=utf-8").to("log:?level=ERROR&showBody=true$showException=true")
                .routeId("ModelValidationHarvestersRawRoute").log(LoggingLevel.INFO, "ModelValidationHarvestersRawRoute")
                .split(body().tokenize("\n"))
                .process(new Base64DecoderProcessor()).to("log:?level=ERROR&showBody=true$showException=true")
                .unmarshal().json(JsonLibrary.Gson, DatasetModel.class).to("log:?level=ERROR&showBody=true$showException=true")
                .bean(ModelValidationBean.class, "checkDatasetModel").to("log:?level=ERROR&showBody=true$showException=true")
                .marshal().json(JsonLibrary.Gson, DatasetModel.class).to("log:?level=ERROR&showBody=true$showException=true")
                .convertBodyTo(String.class, "UTF-8")
                .to("mongodb3:mongoConnection?database=" + mongoDatabase + "&collection=dataset&operation=insert")
                .to("seda:findAll?blockWhenFull=true&waitForTaskToComplete=Always&timeout=0").to("log:?level=ERROR&showException=true").log("Triggering ModelValidationRawKafkaRoute");

        from("seda:findAll")
                .routeId("ModelValidationRawKafkaRoute").log(LoggingLevel.INFO, "ModelValidationRawKafkaRoute")
                .setBody().constant("{\"PubMetadata.hasBeenTransferred\": \"false\"}")
                .to("mongodb3:mongoConnection?database=" + mongoDatabase + "&collection=" + dataType + "&operation=findAll")
                .split(body())
                .process(new PrepareMessageProcessor()).to("log:?level=ERROR&showBody=true$showException=true")
                .to("kafka:spot_deduplicate_raw_" + dataType + "?brokers=" + kafkaBroker + "&enableIdempotence=true&maxInFlightRequest=1&retries=1&requestRequiredAcks=all")
                .process(new PrepareMongoUpdateProcessor()).to("log:?level=ERROR&showBody=true$showException=true")
                .to("mongodb3:mongoConnection?database=" + mongoDatabase + "&collection=dataset&operation=update");
    }

}

