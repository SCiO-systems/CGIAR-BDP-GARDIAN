package com.scio.quantum.ai.pii.process;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.bson.Document;


public class DocumentToJsonProcessor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        Document document = exchange.getIn().getBody(Document.class);
        document.remove("_id");

        JsonParser jsonParser = new JsonParser();
        String json = document.toJson();
        JsonElement je = jsonParser.parse(json);

        exchange.getOut().setBody(je);
    }
}
