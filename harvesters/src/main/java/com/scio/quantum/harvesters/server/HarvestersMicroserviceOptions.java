package com.scio.quantum.harvesters.server;

import org.apache.commons.cli.Options;

public class HarvestersMicroserviceOptions {

    public Options createOptions(){

        Options options = new Options();

        options.addOption("filepool",true,"Metadata Pool");
        options.addOption("resource",true,"Resource Type");

        return options;
    }
}
