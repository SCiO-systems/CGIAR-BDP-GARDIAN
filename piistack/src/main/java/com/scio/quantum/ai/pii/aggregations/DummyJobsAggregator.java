package com.scio.quantum.ai.pii.aggregations;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.camel.Exchange;
import org.apache.camel.processor.aggregate.AggregationStrategy;

public class DummyJobsAggregator implements AggregationStrategy {
    @Override
    public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
        if(oldExchange == null){
            JsonArray runningJobsList = new JsonArray();
            JsonElement jo = newExchange.getIn().getBody(JsonElement.class);
            runningJobsList.add(jo);
            newExchange.getOut().setBody(runningJobsList);
            return newExchange;
        }else{
            JsonArray runningJobsList = oldExchange.getIn().getBody(JsonArray.class);
            JsonElement jo = newExchange.getIn().getBody(JsonElement.class);
            runningJobsList.add(jo);
            newExchange.getOut().setBody(runningJobsList);
            return newExchange;
        }
    }
}
