package com.scio.quantum.ai.pii.aggregations;

import com.scio.quantum.ai.pii.models.file.CandidateFile;
import org.apache.camel.Exchange;
import org.apache.camel.processor.aggregate.AggregationStrategy;

import java.util.ArrayList;
import java.util.List;

public class OntologyAggregator implements AggregationStrategy {
    @Override
    public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
        if (oldExchange == null) {
            List<CandidateFile> candidateFiles = new ArrayList<>();
            CandidateFile cf = newExchange.getIn().getBody(CandidateFile.class);
            if(cf.getNamedEntities().size()>0){
                candidateFiles.add(cf);
            }
            newExchange.getIn().setBody(candidateFiles);
            return newExchange;
        }else {
            List<CandidateFile> candidateFiles = oldExchange.getIn().getBody(ArrayList.class);
            CandidateFile cf = newExchange.getIn().getBody(CandidateFile.class);
            if(cf.getNamedEntities().size()>0){
                candidateFiles.add(cf);
            }
            newExchange.getIn().setBody(candidateFiles);
            return newExchange;
        }
    }
}
