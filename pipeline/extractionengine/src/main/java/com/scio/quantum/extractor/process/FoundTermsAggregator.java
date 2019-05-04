package com.scio.quantum.extractor.process;

import com.scio.quantum.extractor.models.vocabulary.Term;
import org.apache.camel.Exchange;
import org.apache.camel.processor.aggregate.AggregationStrategy;

import java.util.ArrayList;

public class FoundTermsAggregator implements AggregationStrategy {
    @Override
    public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
        if(oldExchange == null){
            ArrayList<Term> avList = new ArrayList();
            Term term = newExchange.getIn().getBody(Term.class);
            if(term != null){
                avList.add(term);
            }
            newExchange.getOut().setBody(avList);
            return newExchange;
        }else{
            ArrayList<Term> avList = oldExchange.getIn().getBody(ArrayList.class);
            Term term = newExchange.getIn().getBody(Term.class);
            if(term != null){
                avList.add(term);
            }
            newExchange.getOut().setBody(avList);
            return newExchange;
        }

    }
}
