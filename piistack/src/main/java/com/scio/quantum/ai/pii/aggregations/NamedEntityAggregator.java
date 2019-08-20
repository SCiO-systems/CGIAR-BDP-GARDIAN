package com.scio.quantum.ai.pii.aggregations;

import com.scio.quantum.ai.pii.models.namedentity.NamedEntity;
import org.apache.camel.Exchange;
import org.apache.camel.processor.aggregate.AggregationStrategy;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NamedEntityAggregator implements AggregationStrategy {
    @Override
    public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
        if (oldExchange == null) {
            List<NamedEntity> neList =  new ArrayList<>();
            ArrayList<NamedEntity> ne = newExchange.getIn().getBody(ArrayList.class);
            neList.addAll(ne);
            newExchange.getIn().setBody(neList);
            return newExchange;
        } else {
            List<NamedEntity> neList = oldExchange.getIn().getBody(ArrayList.class);
            ArrayList<NamedEntity> ne = newExchange.getIn().getBody(ArrayList.class);
            neList.addAll(ne);
            newExchange.getIn().setBody(neList);
            return newExchange;
        }
    }
}
