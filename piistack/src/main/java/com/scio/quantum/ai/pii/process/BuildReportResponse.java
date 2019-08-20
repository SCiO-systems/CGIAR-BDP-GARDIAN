package com.scio.quantum.ai.pii.process;

import com.google.gson.JsonObject;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

public class BuildReportResponse implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        JsonObject jo = exchange.getIn().getBody(JsonObject.class);

        JsonObject rjo = new JsonObject();
        rjo.addProperty("status","202");
        rjo.add("report",jo);

        Map<String,Object> headers = new HashMap<>();
        headers.put(Exchange.CONTENT_TYPE, "application/json");
        headers.put(Exchange.HTTP_RESPONSE_CODE, String.valueOf(HttpStatus.SC_OK));
        exchange.getOut().setBody(rjo);
        exchange.getOut().setHeaders(headers);


    }
}
