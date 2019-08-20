package com.scio.quantum.ai.pii.process;

import com.scio.quantum.ai.pii.models.namedentity.NamedEntity;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;

public class HitsTermProcessor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {

        NamedEntity ne = exchange.getIn().getHeader("NamedEntity",NamedEntity.class);
        SearchResponse sr = exchange.getIn().getBody(SearchResponse.class);
        if(sr.getHits().totalHits>0){
            SearchHit[] sh = sr.getHits().getHits();
            String label = (String) sh[0].getSource().get("label");
            exchange.getOut().setHeader("NamedEntity",ne);
            exchange.getOut().setBody(label);
        }else{
            exchange.getOut().setHeader("NamedEntity",ne);
            exchange.getOut().setBody(null);
        }
    }
}
