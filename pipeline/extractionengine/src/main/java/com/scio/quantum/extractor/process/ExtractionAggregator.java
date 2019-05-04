package com.scio.quantum.extractor.process;

import com.scio.quantum.extractor.models.publication.ExtractedMetadata;
import org.apache.camel.Exchange;
import org.apache.camel.processor.aggregate.AggregationStrategy;

import java.util.ArrayList;
import java.util.List;

public class ExtractionAggregator implements AggregationStrategy {
    @Override
    public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
        if (oldExchange == null) {
            List<ExtractedMetadata> emList = new ArrayList<>();
            ExtractedMetadata em = newExchange.getIn().getBody(ExtractedMetadata.class);
            System.out.println("AGG 1: "+newExchange.getIn().getBody());
            System.out.println("AGG 2: "+newExchange.getOut().getBody());
            emList.add(em);
            newExchange.getOut().setBody(emList);
            return newExchange;
        } else {
            List<ExtractedMetadata> emList = oldExchange.getIn().getBody(ArrayList.class);
            ExtractedMetadata em = newExchange.getIn().getBody(ExtractedMetadata.class);
            emList.add(em);
            newExchange.getOut().setBody(emList);
            return newExchange;
        }
    }
}
