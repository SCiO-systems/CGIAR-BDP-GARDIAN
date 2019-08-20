package com.scio.quantum.ai.pii.process;

import com.scio.quantum.ai.pii.models.namedentity.NamedEntity;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import static org.elasticsearch.index.query.QueryBuilders.matchQuery;

public class QueryLemmaProcessor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {

        String vocabulary = exchange.getProperty("INDEX",String.class);
        String field = exchange.getProperty("FIELD",String.class);
        SearchSourceBuilder search = new SearchSourceBuilder();
        NamedEntity namedEntity = exchange.getIn().getBody(NamedEntity.class);
        exchange.getOut().setHeader("NamedEntity",namedEntity);

        QueryBuilder qb = query(field,namedEntity.getLemma().toLowerCase());
        search.query(qb);

        SearchRequest request = new SearchRequest();
        request.searchType(SearchType.DFS_QUERY_THEN_FETCH);
        request.source(search);
        request.indices(vocabulary);
        exchange.getOut().setBody(request);
    }

    private QueryBuilder query(String field, String term){
        QueryBuilder qb = matchQuery(field, term);
        return qb;
    }
}
