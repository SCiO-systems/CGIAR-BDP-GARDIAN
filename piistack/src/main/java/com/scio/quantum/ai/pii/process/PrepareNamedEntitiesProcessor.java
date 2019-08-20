package com.scio.quantum.ai.pii.process;

import com.scio.quantum.ai.pii.models.file.CandidateFile;
import com.scio.quantum.ai.pii.models.namedentity.NamedEntity;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.util.ArrayList;

public class PrepareNamedEntitiesProcessor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        CandidateFile cf = exchange.getIn().getBody(CandidateFile.class);
        ArrayList<NamedEntity> namedEntityList = cf.getNamedEntities();
        exchange.getOut().setBody(namedEntityList,ArrayList.class);
    }
}
