package com.scio.quantum.deduplicator.route;

import com.scio.quantum.deduplicator.exceptions.ExceptionHandler;
import com.scio.quantum.deduplicator.models.publication.PublicationModel;
import com.scio.quantum.deduplicator.process.*;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.ehcache.EhcacheConstants;
import org.apache.camel.model.dataformat.JsonLibrary;

public class DeduplicatorRouter extends RouteBuilder {

    private String mongoDatabase = "raw";
    private String mongoDatabaseRefined = "refined";
    private String kafkaBroker;
    private String dataType;

    public DeduplicatorRouter(String kafkaBroker, String dataType) {
        super();
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

    private void datasetRoute() {
        errorHandler(deadLetterChannel(
                "log:?level=ERROR&showAll=true&showCaughtException=true"));

        onException(Exception.class)
                .handled(true)
                .process(new ExceptionHandler())
                .to("log:?level=ERROR&showAll=true&showCaughtException=true");

        from("timer:loadHash?repeatCount=1")
                .routeId("LoadHashIndex")
                .startupOrder(1)
                .setProperty("DATATYPE",constant(dataType)).log("LOAD")
                .to("mongodb3:mongoConnection?database=" + mongoDatabaseRefined + "&collection=" + dataType + "&operation=findAll")
                .choice()
                .when(header("CamelMongoDbResultPageSize").isGreaterThan(0))
                .split(body())
                .aggregate(constant(true),new LoadHashIndexAggregator()).completionSize( simple( "${exchangeProperty.CamelSplitSize}" ) )
                .log("Force Aggregator")
                .setHeader("CamelEhcacheAction", constant(EhcacheConstants.ACTION_PUT))
                .setHeader("CamelEhcacheKey", constant("hashmap"))
                .to("ehcache://hashMapCache?cacheManager=#cacheManager&key=hashmap&keyType=java.lang.String&valueType=java.util.HashMap")
                .process(new InitiateDeduplicationProcessor())
                .end()
                .endChoice()
                .when(header("CamelMongoDbResultPageSize").isEqualTo(0))
                .process(new InitializeHashMapProcessor())
                .log("Empty Mongo")
                .setHeader("CamelEhcacheAction", constant(EhcacheConstants.ACTION_PUT))
                .setHeader("CamelEhcacheKey", constant("hashmap"))
                .to("ehcache://hashMapCache?cacheManager=#cacheManager&key=hashmap&keyType=java.lang.String&valueType=java.util.HashMap")
                .process(new InitiateDeduplicationProcessor())
                .end();

        from("kafka:"+this.kafkaBroker+"?topic=deduplicate_raw_"+this.dataType+"&autoOffsetReset=earliest" +
                "&autoCommitEnable=false&allowManualCommit=true&groupId=deduplicator&consumersCount=1&maxPollRecords=5")
                .routeId("DatasetDeduplicatorConsumeRawTopic").log(LoggingLevel.INFO, "DatasetDeduplicatorConsumeRawTopic")
                .autoStartup(false)
                .process(new KafkaManualCommitProcessor())
                .to("seda:findById?blockWhenFull=true&waitForTaskToComplete=Always&timeout=0");

        from("seda:findById")
                .routeId("DatasetDeduplicatorConsumeRawDatabase").log(LoggingLevel.INFO, "DatasetDeduplicatorConsumeRawDatabase")
                .process(new ReadKafkaMessageProcessor())
                .to("mongodb3:mongoConnection?database=" + mongoDatabase + "&collection=" + dataType + "&operation=findById")
                .to("direct:deduplicate");

        from("direct:deduplicate")
                .routeId("DatasetDeduplicatorBeginsDeduplication")
                .process(exchange -> {
                    exchange.setProperty("resourceType",this.dataType);
                    exchange.setProperty("refinedDB",this.mongoDatabaseRefined);

                })
                .process(new FindDuplicateProcessor())
                .to("mongodb3:mongoConnection?database=" + mongoDatabaseRefined + "&collection=" + dataType + "&operation=save")
                .process(new UpdateHashIndexProcessor())
                .to("seda:forwardToProcessing");

        from("seda:forwardToProcessing")
                .routeId("DatasetDeduplicatorKafkaRoute")
                .process(new ReadMongoIdProcessor())
                .to("mongodb3:mongoConnection?database=" + mongoDatabaseRefined + "&collection=" + dataType + "&operation=findById")
                .process(new ReadMongoDocumentProcessor())
                .marshal().json(JsonLibrary.Gson, PublicationModel.class)
                .multicast()
                .to(
                        "kafka:production_download_" + dataType + "?brokers=" + kafkaBroker + "&enableIdempotence=true&maxInFlightRequest=1&retries=1&requestRequiredAcks=all",
                        "kafka:production_extraction_bus_" + dataType + "?brokers=" + kafkaBroker + "&enableIdempotence=true&maxInFlightRequest=1&retries=1&requestRequiredAcks=all"
                );


    }

    private void publicationRoute() {

        errorHandler(deadLetterChannel(
                "log:?level=ERROR&showAll=true&showCaughtException=true"));

        onException(Exception.class)
                .handled(true)
                .process(new ExceptionHandler())
                .to("log:?level=ERROR&showAll=true&showCaughtException=true");

        //from("quartz2:loadHashIndex?cron=0+0+*+?+*+*+*")
        from("timer:loadHash?repeatCount=1")
                .routeId("LoadHashIndex")
                .startupOrder(1)
                .setProperty("DATATYPE",constant(dataType)).log("LOAD")
                .to("mongodb3:mongoConnection?database=" + mongoDatabaseRefined + "&collection=" + dataType + "&operation=findAll")
                .choice()
                    .when(header("CamelMongoDbResultPageSize").isGreaterThan(0))
                        .split(body())
                        .aggregate(constant(true),new LoadHashIndexAggregator()).completionSize( simple( "${exchangeProperty.CamelSplitSize}" ) )
                        .log("Force Aggregator")
                        .setHeader("CamelEhcacheAction", constant(EhcacheConstants.ACTION_PUT))
                        .setHeader("CamelEhcacheKey", constant("hashmap"))
                        .to("ehcache://hashMapCache?cacheManager=#cacheManager&key=hashmap&keyType=java.lang.String&valueType=java.util.HashMap")
                        .process(new InitiateDeduplicationProcessor())
                        .end()
                        .endChoice()
                    .when(header("CamelMongoDbResultPageSize").isEqualTo(0))
                        .process(new InitializeHashMapProcessor())
                        .log("Empty Mongo")
                        .setHeader("CamelEhcacheAction", constant(EhcacheConstants.ACTION_PUT))
                        .setHeader("CamelEhcacheKey", constant("hashmap"))
                        .to("ehcache://hashMapCache?cacheManager=#cacheManager&key=hashmap&keyType=java.lang.String&valueType=java.util.HashMap")
                        .process(new InitiateDeduplicationProcessor())
                        .end();


        from("kafka:"+this.kafkaBroker+"?topic=production_deduplicate_bus_"+this.dataType+"&autoOffsetReset=earliest" +
                "&autoCommitEnable=false&allowManualCommit=true&groupId=deduplicator&consumersCount=1&maxPollRecords=5")
                .routeId("DeduplicatorConsumeRawTopic").log(LoggingLevel.INFO, "DeduplicatorConsumeRawTopic")
                .autoStartup(false)
                .process(new KafkaManualCommitProcessor())
                .to("seda:findById?blockWhenFull=true&waitForTaskToComplete=Always&timeout=0");


        from("seda:findById")
                .routeId("DeduplicatorConsumeRawDatabase").log(LoggingLevel.INFO, "DeduplicatorConsumeRawDatabase")
                .process(new ReadKafkaMessageProcessor())
                .to("mongodb3:mongoConnection?database=" + mongoDatabase + "&collection=" + dataType + "&operation=findById")
                .to("direct:deduplicate");

        from("direct:deduplicate")
                .routeId("DeduplicatorBeginsDeduplication")
                .process(exchange -> {
                    exchange.setProperty("resourceType",this.dataType);
                    exchange.setProperty("refinedDB",this.mongoDatabaseRefined);

                })
                .process(new FindDuplicateProcessor())
                .to("mongodb3:mongoConnection?database=" + mongoDatabaseRefined + "&collection=" + dataType + "&operation=save")
                .process(new UpdateHashIndexProcessor())
                .to("seda:forwardToProcessing");

        from("seda:forwardToProcessing")
                .routeId("DeduplicatorKafkaRoute")
                .process(new ReadMongoIdProcessor())
                .to("mongodb3:mongoConnection?database=" + mongoDatabaseRefined + "&collection=" + dataType + "&operation=findById")
                .process(new ReadMongoDocumentProcessor())
                .marshal().json(JsonLibrary.Gson, PublicationModel.class)
                .multicast()
                .to(
                    "kafka:production_download_" + dataType + "?brokers=" + kafkaBroker + "&enableIdempotence=true&maxInFlightRequest=1&retries=1&requestRequiredAcks=all",
                    "kafka:production_extraction_bus_" + dataType + "?brokers=" + kafkaBroker + "&enableIdempotence=true&maxInFlightRequest=1&retries=1&requestRequiredAcks=all"
                );
    }


}
