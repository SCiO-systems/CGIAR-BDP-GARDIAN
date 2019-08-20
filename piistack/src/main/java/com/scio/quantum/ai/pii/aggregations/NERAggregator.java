package com.scio.quantum.ai.pii.aggregations;

import com.scio.quantum.ai.pii.models.file.CandidateFile;
import org.apache.camel.Exchange;
import org.apache.camel.processor.aggregate.AggregationStrategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NERAggregator implements AggregationStrategy {
    @Override
    public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
        if(oldExchange == null){
            ArrayList<CandidateFile> candidateFiles = new ArrayList();
            CandidateFile cf = newExchange.getIn().getBody(CandidateFile.class);
            if(cf != null){
                candidateFiles.add(cf);
            }
            newExchange.getOut().setBody(candidateFiles);
            return newExchange;
        }else{
            ArrayList<CandidateFile> candidateFiles = oldExchange.getIn().getBody(ArrayList.class);
            CandidateFile cf = newExchange.getIn().getBody(CandidateFile.class);
            if(cf != null){
                candidateFiles.add(cf);
            }
            newExchange.getOut().setBody(candidateFiles);
            return newExchange;
        }

    }
}
