package com.scio.quantum.deduplicator.process;

import com.google.gson.Gson;
import com.scio.quantum.deduplicator.models.QuantumResourceHash;
import com.scio.quantum.deduplicator.models.publication.PublicationModel;
import org.apache.camel.Exchange;
import org.bson.Document;


public class ExtractResourceHashBean {

    public static void extractHash(Exchange exchange){
        String dataType = exchange.getProperty("DATATYPE",String.class);
        Gson gson = new Gson();
        Document document = exchange.getIn().getBody(Document.class);
        document.remove("_id");
        if(dataType.equalsIgnoreCase("publication")){
            PublicationModel pm = gson.fromJson(document.toJson(), PublicationModel.class);
            String title = pm.getPubMetadata().getTitle();
            String titleHash = pm.getPubMetadata().getTitleHash();
            String doi = pm.getPubMetadata().getDoi().get(0).getLink();
            String doiHash = pm.getPubMetadata().getDoiHash();
            QuantumResourceHash sh = new QuantumResourceHash(title,titleHash,doi,doiHash);
            exchange.getOut().setBody(sh);
        }

    }
}
