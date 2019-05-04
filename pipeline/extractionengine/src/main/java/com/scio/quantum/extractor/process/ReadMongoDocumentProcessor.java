package com.scio.quantum.extractor.process;

import com.google.gson.Gson;
import com.scio.quantum.extractor.models.publication.PublicationModel;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.kafka.KafkaConstants;
import org.bson.Document;

public class ReadMongoDocumentProcessor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        if (exchange.getIn() != null) {
            String resourceType = exchange.getProperty("DATATYPE",String.class);
            Document document = exchange.getIn().getBody(Document.class);
            if(resourceType.equalsIgnoreCase("publication")){
                Gson gson = new Gson();
                PublicationModel pm = gson.fromJson(document.toJson(), PublicationModel.class);
                exchange.getOut().setHeader(KafkaConstants.PARTITION_KEY, 0);
                exchange.getOut().setHeader(KafkaConstants.KEY, "id");
                exchange.getOut().setBody(pm);
            }
        }
    }
}