package com.scio.quantum.harvesters.process;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class InternalIdProcessor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        exchange.getOut().setBody(exchange.getProperty("internalId"));
    }
}
