package com.scio.quantum.ai.pii.aggregations;

import com.scio.quantum.ai.pii.models.namedentity.NamedEntity;
import org.apache.camel.Exchange;
import org.apache.camel.processor.aggregate.AggregationStrategy;

import java.util.ArrayList;

public class FoundTermsAggregator implements AggregationStrategy {

    @Override
    public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
        if(oldExchange == null){
            ArrayList<NamedEntity> namedEntities = new ArrayList();
            NamedEntity ne = newExchange.getIn().getHeader("NamedEntity",NamedEntity.class);
            String label = newExchange.getIn().getBody(String.class);
            setRemovalState(ne, namedEntities, label);
            newExchange.getOut().setBody(namedEntities);
            return newExchange;
        }else{
            ArrayList<NamedEntity> namedEntities = oldExchange.getIn().getBody(ArrayList.class);
            NamedEntity ne = newExchange.getIn().getHeader("NamedEntity",NamedEntity.class);
            String label = newExchange.getIn().getBody(String.class);
            setRemovalState(ne, namedEntities, label);
            newExchange.getOut().setBody(namedEntities);
            return newExchange;
        }
    }

    private void setRemovalState(NamedEntity ne, ArrayList<NamedEntity> namedEntities, String label) {
        if(label != null){
            ne.setRemove(true);
            namedEntities.add(ne);
        }else{
            ne.setRemove(false);
            namedEntities.add(ne);
        }
    }
}
