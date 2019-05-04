package com.scio.quantum.deduplicator.process;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.util.ArrayList;
import java.util.HashMap;

public class InitializeHashMapProcessor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        HashMap<String, ArrayList<String>> hashMap = new HashMap<String, ArrayList<String>>();
        exchange.getOut().setBody(hashMap,HashMap.class);
    }
}
