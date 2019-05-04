package com.scio.quantum.extractor.process;


import com.scio.quantum.extractor.models.vocabulary.Term;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import static org.elasticsearch.index.query.QueryBuilders.*;

public class QueryTermProcessor implements Processor {


    @Override
    public void process(Exchange exchange) throws Exception {

        String vocabulary = exchange.getProperty("INDEX",String.class);
        Term tm = exchange.getIn().getBody(Term.class);
        String term = tm.getTerm();
        SearchSourceBuilder search = new SearchSourceBuilder();
        if(vocabulary.equalsIgnoreCase("agrovoc")){
            QueryBuilder qb = query("label",term,"en");
            search.query(qb);
        }else if(vocabulary.equalsIgnoreCase("geonames")){
            QueryBuilder qb = query("country",term);
            search.query(qb);
        }else if(vocabulary.equalsIgnoreCase("license")){
            QueryBuilder qb = query("term",term);
            search.query(qb);
        }

        SearchRequest request = new SearchRequest();
        request.searchType(SearchType.DFS_QUERY_THEN_FETCH);
        request.source(search);
        request.indices(vocabulary);
        exchange.setProperty("TERM_FREQUENCY",tm.getFrequency());
        exchange.getOut().setBody(request);
    }

    private QueryBuilder query(String field, String term){
        QueryBuilder qb = matchQuery(field+".keyword", term.toLowerCase());
        return qb;
    }
    private QueryBuilder query(String field, String term,String language){
        QueryBuilder qb = boolQuery()
                .must(wildcardQuery("URI", "*"+language+"*"))
                .must(matchQuery(field+".keyword", term));
        return qb;
    }
}
