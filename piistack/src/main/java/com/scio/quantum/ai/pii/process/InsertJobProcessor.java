package com.scio.quantum.ai.pii.process;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.bson.Document;

import java.util.Map;

public class InsertJobProcessor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        Map mp = exchange.getProperty("map",Map.class);
        Gson gson = new GsonBuilder().create();
        String json = gson.toJson(mp);
        Document document = Document.parse(json);
        exchange.getIn().setBody(document);
    }
}
