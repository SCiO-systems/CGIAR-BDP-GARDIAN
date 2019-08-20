package com.scio.quantum.ai.pii.process;

import com.scio.quantum.ai.pii.models.namedentity.NamedEntity;
import org.apache.camel.Exchange;

import java.util.ArrayList;

public class NamedEntityResultBean {

    public ArrayList<NamedEntity> returnResult(Exchange exchange){
        ArrayList<NamedEntity> ne = exchange.getIn().getBody(ArrayList.class);
        return ne;
    }

}
