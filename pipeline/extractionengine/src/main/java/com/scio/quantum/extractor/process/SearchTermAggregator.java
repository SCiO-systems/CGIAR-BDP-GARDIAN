package com.scio.quantum.extractor.process;

import com.scio.quantum.extractor.models.vocabulary.Term;
import org.apache.camel.Exchange;
import org.apache.camel.processor.aggregate.AggregationStrategy;

import java.util.ArrayList;

public class SearchTermAggregator implements AggregationStrategy {
    @Override
    public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {

        ArrayList<Term> tm = oldExchange.getIn().getBody(ArrayList.class);



        return null;
    }
}
