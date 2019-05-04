package com.scio.quantum.deduplicator.exceptions;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.scio.quantum.deduplicator.models.dataset.DatasetModel;
import com.scio.quantum.deduplicator.models.publication.PublicationModel;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;


public class ExceptionHandler implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        Exception exception = (Exception) exchange.getProperty(Exchange.EXCEPTION_CAUGHT);

        Object json = exchange.getIn().getBody();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();


        if(json instanceof PublicationModel){
            PublicationModel pm = (PublicationModel) json;
            String jsonSrc = gson.toJson(pm);

            JsonParser parser = new JsonParser();
            JsonObject jsonWithException = parser.parse(jsonSrc).getAsJsonObject();
            jsonWithException.addProperty("Exception",exception.getMessage());

            exchange.getOut().setBody(jsonWithException.toString());

        }else if(json instanceof DatasetModel){
            DatasetModel dm = (DatasetModel) json;
            String jsonSrc = gson.toJson(dm);

            JsonParser parser = new JsonParser();
            JsonObject jsonWithException = parser.parse(jsonSrc).getAsJsonObject();
            jsonWithException.addProperty("Exception",exception.getMessage());

            exchange.getOut().setBody(jsonWithException.toString());
        }else{
            exchange.getOut().setBody(exception.getMessage());
        }

    }
}
