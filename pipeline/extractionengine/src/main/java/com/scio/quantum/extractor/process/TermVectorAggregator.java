package com.scio.quantum.extractor.process;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.scio.quantum.extractor.models.vocabulary.Term;
import org.apache.camel.Exchange;
import org.apache.camel.processor.aggregate.AggregationStrategy;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

public class TermVectorAggregator implements AggregationStrategy {
    @Override
    public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {

        String elasticResponse = newExchange.getIn().getBody(String.class);
        JsonParser parser = new JsonParser();
        JsonElement messageObject = parser.parse(elasticResponse);
        JsonObject es = messageObject.getAsJsonObject();
        JsonObject termVectors = es.get("term_vectors").getAsJsonObject();
        JsonObject dummyText = termVectors.get("dummy_text").getAsJsonObject();
        JsonObject terms = dummyText.getAsJsonObject("terms");

        Set<Map.Entry<String, JsonElement>> entries = terms.entrySet();
        ArrayList<Term> extractedTerms = new ArrayList<>();
        for (Map.Entry<String, JsonElement> entry: entries) {
            String term = entry.getKey();
            boolean isNumber = NumberUtils.isCreatable(term);
            if((term.length()>3)&&(!isNumber)){
                JsonObject termStatistics = entry.getValue().getAsJsonObject();
                String termFrequency = termStatistics.get("term_freq").getAsString();
                Term tr = new Term(term,termFrequency);
                extractedTerms.add(tr);
            }
        }
        newExchange.getOut().setBody(extractedTerms);
        return newExchange;
    }
}
