package com.scio.quantum.harvesters.process;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.commons.codec.binary.Base64;

public class Base64EncoderProcessor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        String stringRaw = exchange.getIn().getBody(String.class);

        byte[] encodedBytes = Base64.encodeBase64(stringRaw.getBytes());
        String stringEncoded = new String(encodedBytes);
        exchange.getOut().setBody(stringEncoded);

    }
}
