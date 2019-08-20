package com.scio.quantum.ai.pii.process;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.kafka.KafkaConstants;

public class ProduceJobProcessor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        exchange.getOut().setHeader(KafkaConstants.PARTITION_KEY, 0);
        exchange.getOut().setHeader(KafkaConstants.KEY, "id");
        exchange.getOut().setBody(exchange.getIn().getBody(String.class));
    }
}
