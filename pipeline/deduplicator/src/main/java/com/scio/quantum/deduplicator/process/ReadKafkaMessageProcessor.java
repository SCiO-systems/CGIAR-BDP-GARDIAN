package com.scio.quantum.deduplicator.process;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.bson.types.ObjectId;

public class ReadKafkaMessageProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        if (exchange.getIn() != null) {
            String message = exchange.getIn().getBody(String.class);
            ObjectId mongoId = new ObjectId(message);
            exchange.getIn().setBody(mongoId);
        }

    }
}
