package com.scio.quantum.deduplicator.process;

import com.mongodb.client.result.UpdateResult;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.bson.types.ObjectId;

public class ReadMongoIdProcessor implements Processor {


    @Override
    public void process(Exchange exchange) throws Exception {
        if (exchange.getIn() != null) {
            UpdateResult result = (UpdateResult) exchange.getIn().getBody();
            if(result.getUpsertedId() != null){
                ObjectId mongoId = result.getUpsertedId().asObjectId().getValue();
                exchange.getIn().setBody(mongoId);
            }else{
                ObjectId mongoId = new ObjectId();
                exchange.getIn().setBody(mongoId);
            }
        }
    }
}
