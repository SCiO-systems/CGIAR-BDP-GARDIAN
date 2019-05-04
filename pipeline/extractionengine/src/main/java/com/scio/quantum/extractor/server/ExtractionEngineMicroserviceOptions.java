package com.scio.quantum.extractor.server;

import org.apache.commons.cli.Options;

public class ExtractionEngineMicroserviceOptions {

    public ExtractionEngineMicroserviceOptions() {

    }

    public Options createOptions(){

        Options options = new Options();

        options.addOption("kafka",true,"Kafka Broker");
        options.addOption("mongo",true,"Mongo Location");
        options.addOption("resource",true,"Resource Type");
        options.addOption("vocabulary",true,"Vocabulary Extraction");
        options.addOption("termvector",true,"Term Vector Builder");
        options.addOption("elasticsearch",true,"Elasticsearch Cluster Name & IP");


        return options;
    }


}
