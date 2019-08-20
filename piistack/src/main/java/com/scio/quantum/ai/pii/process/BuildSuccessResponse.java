package com.scio.quantum.ai.pii.process;

import com.google.gson.JsonObject;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

public class BuildSuccessResponse implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        Map<String,Object> headers = new HashMap<String,Object>();
        headers.put(Exchange.CONTENT_TYPE, "application/json");
        headers.put(Exchange.HTTP_RESPONSE_CODE, String.valueOf(HttpStatus.SC_OK));
        String jobID = exchange.getProperty("jobID",String.class);
        String message = "{\"status\":\"202\",\"jobID\":\""+jobID+"\",\"message\":\"Job Submitted Successfully\"}";
        exchange.getOut().setBody(message);
        exchange.getOut().setHeaders(headers);
    }
}
