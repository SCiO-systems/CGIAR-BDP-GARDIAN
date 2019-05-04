package com.scio.quantum.extractor.process;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.scio.quantum.extractor.models.vocabulary.AgrovocTerm;
import com.scio.quantum.extractor.models.vocabulary.GeonamesTerm;
import com.scio.quantum.extractor.models.vocabulary.Term;
import com.scio.quantum.extractor.models.publication.ExtractedMetadata;
import com.scio.quantum.extractor.models.publication.PublicationModel;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FoundTermProcessor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        ArrayList<Term> tm = exchange.getIn().getBody(ArrayList.class);
        ExtractedMetadata em = null;
        if(tm.size()>0){
            tm.removeIf(Objects::isNull);
            Term term = tm.get(0);
            if(term instanceof AgrovocTerm){
                em = new ExtractedMetadata("agrovoc",true,tm);
            }else if(term instanceof GeonamesTerm){
                em = new ExtractedMetadata("geonames",true,tm);
            }
        }
        if(em == null){
            tm = new ArrayList();
            String index = exchange.getProperty("INDEX",String.class);
            if(index.equalsIgnoreCase("agrovoc")){
                em = new ExtractedMetadata("agrovoc",true,tm);
            }else if(index.equalsIgnoreCase("geonames")){
                em = new ExtractedMetadata("geonames",true,tm);
            }
        }
        System.out.println("EM: "+em);
        exchange.getOut().setBody(em);
        exchange.getIn().setBody(em);
    }
}
