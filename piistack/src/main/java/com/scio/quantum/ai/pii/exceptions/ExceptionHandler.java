package com.scio.quantum.ai.pii.exceptions;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class ExceptionHandler implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        Exception exception = (Exception) exchange.getProperty(Exchange.EXCEPTION_CAUGHT);
        System.out.println("FATAL ERROR");
        exception.printStackTrace();
    }
}
