package com.scio.quantum.extractor.process;

import com.scio.quantum.extractor.models.vocabulary.AgrovocTerm;
import com.scio.quantum.extractor.models.vocabulary.GeonamesTerm;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;

public class HitsTermProcessor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        SearchResponse sr = exchange.getIn().getBody(SearchResponse.class);
        if(sr.getHits().totalHits>0){
            String vocabulary = exchange.getProperty("INDEX",String.class);
            SearchHit[] sh = sr.getHits().getHits();
            if(vocabulary.equalsIgnoreCase("agrovoc")){
                String uri = (String) sh[0].getSource().get("URI");
                String agrovocId = (String) sh[0].getSource().get("agrovoc_id");
                String label = (String) sh[0].getSource().get("label");
                String frequency = (String) exchange.getProperty("TERM_FREQUENCY");
                AgrovocTerm at = new AgrovocTerm(uri,agrovocId,label,frequency);
                exchange.getOut().setBody(at);
            }else if(vocabulary.equalsIgnoreCase("geonames")){
                String country = (String) sh[0].getSource().get("Country");
                String capital = (String) sh[0].getSource().get("Capital");
                String population = (String) sh[0].getSource().get("Population");
                String continent = (String) sh[0].getSource().get("Continent");
                String geonamesId = (String) sh[0].getSource().get("geonameid");
                String frequency = (String) exchange.getProperty("TERM_FREQUENCY");
                GeonamesTerm gt = new GeonamesTerm(capital,population,continent,geonamesId,country,frequency);
                exchange.getOut().setBody(gt);
            }else if(vocabulary.equalsIgnoreCase("license")){

            }
        }else{
            exchange.getOut().setBody(null);
        }
    }
}
