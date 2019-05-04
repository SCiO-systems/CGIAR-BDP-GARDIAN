package com.scio.quantum.deduplicator.process;

import com.google.gson.Gson;
import com.scio.quantum.deduplicator.models.publication.PublicationModel;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.kafka.KafkaConstants;
import org.bson.Document;
import org.bson.types.ObjectId;

public class ReadMongoDocumentProcessor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {

        if (exchange.getIn().getBody() != null) {
            String resourceType = exchange.getProperty("resourceType",String.class);
            Document document = exchange.getIn().getBody(Document.class);
            ObjectId objectId = document.getObjectId("_id");
            document.remove("_id");

            if(resourceType.equalsIgnoreCase("publication")){
                Gson gson = new Gson();
                PublicationModel pm = gson.fromJson(document.toJson(), PublicationModel.class);
                pm.getPubMetadata().setQuantumId(objectId.toString());

                exchange.getOut().setHeader(KafkaConstants.PARTITION_KEY, 0);
                exchange.getOut().setHeader(KafkaConstants.KEY, "id");
                exchange.getOut().setBody(pm);
            }else{
                exchange.getOut().setHeader(KafkaConstants.PARTITION_KEY, 0);
                exchange.getOut().setHeader(KafkaConstants.KEY, "id");
                exchange.getOut().setBody("ignore");

            }
        }

    }
}
