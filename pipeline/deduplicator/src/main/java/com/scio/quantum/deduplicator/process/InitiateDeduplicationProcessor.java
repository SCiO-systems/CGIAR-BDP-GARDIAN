package com.scio.quantum.deduplicator.process;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class InitiateDeduplicationProcessor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        String resource = exchange.getProperty("DATATYPE",String.class);
        boolean isHashMapReady = exchange.getIn().getHeader("CamelEhcacheActionSucceeded",Boolean.class);
        if(isHashMapReady == true){
            if(resource.equalsIgnoreCase("publication")){
                exchange.getContext().startRoute("DeduplicatorConsumeRawTopic");
            }else if(resource.equalsIgnoreCase("dataset")){
                exchange.getContext().startRoute("DatasetDeduplicatorConsumeRawTopic");
            }
        }
    }
}
