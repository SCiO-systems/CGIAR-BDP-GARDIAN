package com.scio.quantum.ai.pii.process;

import com.scio.quantum.ai.pii.models.file.CandidateFile;
import com.scio.quantum.ai.pii.models.namedentity.NamedEntity;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.util.*;
import java.util.stream.Collectors;

public class DeduplicateNamedEntitiesProcessor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {

        ArrayList<NamedEntity> neList = exchange.getIn().getBody(ArrayList.class);
        HashMap<Integer,NamedEntity> hashNamedEntities = new HashMap<>();
        Iterator<NamedEntity> namedEntityIterator = neList.iterator();

        while(namedEntityIterator.hasNext()){
            NamedEntity ne = namedEntityIterator.next();
            int neID = ne.hashCode();
            NamedEntity storedNE = hashNamedEntities.get(neID);
            if(storedNE == null){
                hashNamedEntities.put(neID,ne);
            }else{
                if(ne.isRemove() == true){
                    hashNamedEntities.replace(neID,ne);
                }
            }
        }

        ArrayList<NamedEntity> namedEntities = new ArrayList<>();
        for (Map.Entry<Integer, NamedEntity> entry : hashNamedEntities.entrySet()) {
            if(entry.getValue().isRemove() == false){
                namedEntities.add(entry.getValue());
            }
        }
        List<NamedEntity> deduplicatedNamedEntities = namedEntities.stream().distinct().collect(Collectors.toList());

        CandidateFile cf = exchange.getProperty("CandidateFile",CandidateFile.class);
        cf.setContent("");
        cf.setNamedEntities((ArrayList<NamedEntity>) deduplicatedNamedEntities);
        exchange.getOut().setBody(cf,CandidateFile.class);
    }
}
