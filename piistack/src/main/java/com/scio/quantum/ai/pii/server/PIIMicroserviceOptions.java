package com.scio.quantum.ai.pii.server;

import org.apache.commons.cli.Options;

public class PIIMicroserviceOptions {

    public PIIMicroserviceOptions() {

    }

    public Options createOptions(){

        Options options = new Options();
        options.addOption("ip",true,"PII Stack IP");
        return options;
    }
}
