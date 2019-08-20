package com.scio.quantum.ai.pii.process;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.http.HttpStatus;

import javax.json.Json;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class BuildJobsListResponse implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        JsonArray ja = exchange.getIn().getBody(JsonArray.class);

        if(ja != null){
            Iterator<JsonElement> jsonArrayIterator = ja.iterator();

            JsonObject jo = new JsonObject();
            jo.addProperty("status","202");
            JsonArray jobs = new JsonArray();
            while(jsonArrayIterator.hasNext()){

                JsonObject je = jsonArrayIterator.next().getAsJsonObject();
                jobs.add(je.get("Job"));

            }
            jo.add("jobs",jobs);
            exchange.getOut().setBody(jo);
        }else{

            JsonObject jo = new JsonObject();
            jo.addProperty("status","202");
            JsonArray jobs = new JsonArray();


            JsonObject je = new JsonObject();
            je.addProperty("status","202");
            je.addProperty("Title","No Running Job");
            je.addProperty("Path","No Running Job");
            je.addProperty("JobID","No Running Job");
            je.addProperty("email","No Running Job");
            jobs.add(je);
            jo.add("jobs",jobs);
            exchange.getOut().setBody(jo);
        }


        Map<String,Object> headers = new HashMap<>();
        headers.put(Exchange.CONTENT_TYPE, "application/json");
        headers.put(Exchange.HTTP_RESPONSE_CODE, String.valueOf(HttpStatus.SC_OK));

        exchange.getOut().setHeaders(headers);
    }
}
