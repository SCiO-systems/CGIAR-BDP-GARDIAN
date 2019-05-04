package com.scio.quantum.extractor.route;


import com.scio.quantum.extractor.exceptions.ExceptionHandler;
import com.scio.quantum.extractor.models.publication.PublicationModel;
import com.scio.quantum.extractor.process.*;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;

public class ExtractionRouter extends RouteBuilder {

    private String mongoDatabaseRefined = "refined";
    private String kafkaBroker;
    private String resourceType;
    private String vocabulary;
    private String termVectorBuilder;
    private String elasticsearchClusterName;
    private String elasticsearchIP;


    public ExtractionRouter(String resourceType,String kafkaBroker,String vocabulary,String termVectorBuilder,String elasticsearch) {
        this.resourceType = resourceType;
        this.kafkaBroker = kafkaBroker;
        this.vocabulary = vocabulary;
        this.termVectorBuilder = termVectorBuilder;
        this.elasticsearchClusterName = elasticsearch.split(":")[0];
        this.elasticsearchIP = elasticsearch.split(":")[1];
    }

    @Override
    public void configure() throws Exception {
        publicationRoute();
    }

    private void publicationRoute() {


        errorHandler(deadLetterChannel(
                "log:?level=ERROR&showAll=true&showCaughtException=true"));

        onException(Exception.class)
                .handled(true)
                .process(new ExceptionHandler())
                .to("log:?level=ERROR&showAll=true&showCaughtException=true");

        from("kafka:"+this.kafkaBroker+"?topic=extraction_bus_"+this.resourceType+"&autoOffsetReset=earliest&groupId="+this.vocabulary+
                "&autoCommitEnable=false&allowManualCommit=true&consumersCount=3&maxPollRecords=5")
            .routeId("Extraction Messages")
            .convertBodyTo(String.class)
            .filter(body().isNotEqualTo("ignore"))
            .process(new KafkaManualCommitProcessor())
            .to("direct:term_extractor");

        from("direct://term_extractor")
                .routeId("Extract Vocabulary")
                .setProperty("DATATYPE",constant(this.resourceType))
                .convertBodyTo(String.class)
                .unmarshal().json(JsonLibrary.Gson, PublicationModel.class)
                .process(new PrepareSummaryProcessor())
                .setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
                .setHeader(Exchange.HTTP_METHOD,constant("POST"))
                .enrich("http://"+this.termVectorBuilder+"/dummy/dummy_record/_termvectors",new TermVectorAggregator())
                .to("direct:extraction");

        from("direct:extraction")
                .multicast(new ExtractionAggregator())
                .parallelProcessing()
                .to("direct:agrovoc","direct:geonames")
                .end()
                //.log("BODY: ${in.body}")
                .process(new CalculateFairProcessor())
                .to("mongodb3:mongoConnection?database=" + mongoDatabaseRefined + "&collection=" + this.resourceType + "&operation=save")
                .process(new ReadMongoDocumentProcessor())
                .marshal().json(JsonLibrary.Gson, PublicationModel.class)
                .to("kafka:index_bus_" + resourceType + "?brokers=" + kafkaBroker + "&enableIdempotence=true&maxInFlightRequest=1&retries=1&requestRequiredAcks=all");

        from("direct:agrovoc")
                .routeId("Extract Agrovoc Terms")
                .setProperty("INDEX",constant("agrovoc"))
                .split(body())
                .process(new QueryTermProcessor())
                .to("elasticsearch5://"+this.elasticsearchClusterName+"?ip="+this.elasticsearchIP+"&operation=SEARCH")
                .process(new HitsTermProcessor())
                .aggregate(constant(true),new FoundTermsAggregator()).completionPredicate( simple( "${exchangeProperty.CamelSplitComplete}" ) )
                .log("Force Aggregator ${exchangeProperty.CamelSplitSize}")
                .process(new FoundTermProcessor()).end();

        from("direct:geonames")
                .routeId("Extract Geonames Terms")
                .setProperty("INDEX",constant("geonames"))
                .split(body())
                .process(new QueryTermProcessor())
                .to("elasticsearch5://"+this.elasticsearchClusterName+"?ip="+this.elasticsearchIP+"&operation=SEARCH")
                .process(new HitsTermProcessor())
                .aggregate(constant(true),new FoundTermsAggregator()).completionPredicate( simple( "${exchangeProperty.CamelSplitComplete}" ) )
                .log("Force Aggregator ${exchangeProperty.CamelSplitSize}")
                .process(new FoundTermProcessor()).end();

        from("direct:licenses")
                .routeId("Extract Licenses Terms")
                .setProperty("INDEX",constant("licenses"))
                .split(body())
                .process(new QueryTermProcessor())
                .to("elasticsearch5://"+this.elasticsearchClusterName+"?ip="+this.elasticsearchIP+"&operation=SEARCH")
                .process(new HitsTermProcessor())
                .aggregate(constant(true),new FoundTermsAggregator()).completionPredicate( simple( "${exchangeProperty.CamelSplitComplete}" ) )
                .log("Force Aggregator ${exchangeProperty.CamelSplitSize}")
                .process(new FoundTermProcessor());
    }

    private void datasetRoute() {

    }
}
