package com.scio.quantum.extractor.exceptions;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;


public class ExceptionHandler implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        Exception exception = (Exception) exchange.getProperty(Exchange.EXCEPTION_CAUGHT);
        exchange.getOut().setBody(exception.getMessage());
    }
}
