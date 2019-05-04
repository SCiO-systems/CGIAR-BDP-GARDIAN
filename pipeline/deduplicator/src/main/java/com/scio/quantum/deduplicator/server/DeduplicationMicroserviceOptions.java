package com.scio.quantum.deduplicator.server;

import org.apache.commons.cli.Options;

public class DeduplicationMicroserviceOptions {

    public DeduplicationMicroserviceOptions() {

    }

    public Options createOptions(){

        Options options = new Options();

        options.addOption("kafka",true,"Kafka Broker");
        options.addOption("mongo",true,"Mongo Location");
        options.addOption("resource",true,"Resource Type");

        return options;
    }


}
