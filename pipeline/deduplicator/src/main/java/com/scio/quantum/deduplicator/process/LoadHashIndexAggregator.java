package com.scio.quantum.deduplicator.process;

import com.google.gson.Gson;
import com.scio.quantum.deduplicator.models.dataset.DatasetModel;
import com.scio.quantum.deduplicator.models.publication.PublicationModel;
import org.apache.camel.Exchange;
import org.apache.camel.processor.aggregate.AggregationStrategy;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.HashMap;

public class LoadHashIndexAggregator implements AggregationStrategy {

    @Override
    public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
        String resource = newExchange.getProperty("DATATYPE",String.class);

        if(oldExchange == null){
            HashMap<String, ArrayList<String>> hashMap = new HashMap<String, ArrayList<String>>();
            Document document = newExchange.getIn().getBody(Document.class);
            ObjectId objectId = document.getObjectId("_id");
            document.remove("_id");
            if(resource.equalsIgnoreCase("publication")){
                Gson gson = new Gson();
                PublicationModel pm = gson.fromJson(document.toJson(), PublicationModel.class);
                ArrayList<String> objectIds = new ArrayList<String>();
                objectIds.add(objectId.toString());
                hashMap.put(pm.getPubMetadata().getTitle().toLowerCase(),objectIds);
                hashMap.put(pm.getPubMetadata().getDoi().get(0).getLink().toLowerCase(),objectIds);
                newExchange.getOut().setBody(hashMap);
                newExchange.getIn().setBody(hashMap);
            }else if(resource.equalsIgnoreCase("dataset")){
                Gson gson = new Gson();
                DatasetModel dm = gson.fromJson(document.toJson(), DatasetModel.class);
                ArrayList<String> objectIds = new ArrayList<String>();
                objectIds.add(objectId.toString());
                hashMap.put(dm.getDatasetMetadata().getTitle().toLowerCase(),objectIds);
                if(dm.getDatasetMetadata().getDoi().size()>0){
                    hashMap.put(dm.getDatasetMetadata().getDoi().get(0).getLink().toLowerCase(),objectIds);
                }
                newExchange.getOut().setBody(hashMap);
                newExchange.getIn().setBody(hashMap);
            }

            return newExchange;
        }else{
            HashMap<String, ArrayList<String>> hashMap = oldExchange.getIn().getBody(HashMap.class);
            Document document = newExchange.getIn().getBody(Document.class);
            ObjectId objectId = document.getObjectId("_id");
            document.remove("_id");
            if(resource.equalsIgnoreCase("publication")){
                Gson gson = new Gson();
                PublicationModel pm = gson.fromJson(document.toJson(), PublicationModel.class);
                String titleHash = pm.getPubMetadata().getTitle().toLowerCase();
                String doiHash = pm.getPubMetadata().getDoi().get(0).getLink().toLowerCase();
                if(hashMap.containsKey(titleHash)){
                    ArrayList<String> objectIds = hashMap.get(titleHash);
                    objectIds.add(objectId.toString());
                    hashMap.put(titleHash,objectIds);
                }else{
                    ArrayList<String> objectIds = new ArrayList<String>();
                    objectIds.add(objectId.toString());
                    hashMap.put(titleHash,objectIds);
                }
                if((!doiHash.isEmpty())&&(doiHash.length()>3)){
                    if(hashMap.containsKey(doiHash)) {
                        ArrayList<String> objectIds = hashMap.get(doiHash);
                        objectIds.add(objectId.toString());
                        hashMap.put(doiHash, objectIds);
                    }else{
                        ArrayList<String> objectIds = new ArrayList<String>();
                        objectIds.add(objectId.toString());
                        hashMap.put(doiHash,objectIds);
                    }
                }
            }else if(resource.equalsIgnoreCase("dataset")){
                Gson gson = new Gson();
                DatasetModel dm = gson.fromJson(document.toJson(), DatasetModel.class);
                String titleHash = dm.getDatasetMetadata().getTitle().toLowerCase();
                String doiHash = "";
                if(dm.getDatasetMetadata().getDoi().size()>0){
                    doiHash = dm.getDatasetMetadata().getDoi().get(0).getLink().toLowerCase();
                }
                if(hashMap.containsKey(titleHash)){
                    ArrayList<String> objectIds = hashMap.get(titleHash);
                    objectIds.add(objectId.toString());
                    hashMap.put(titleHash,objectIds);
                }else{
                    ArrayList<String> objectIds = new ArrayList<String>();
                    objectIds.add(objectId.toString());
                    hashMap.put(titleHash,objectIds);
                }
                if((!doiHash.isEmpty())&&(doiHash.length()>3)){
                    if(hashMap.containsKey(doiHash)) {
                        ArrayList<String> objectIds = hashMap.get(doiHash);
                        objectIds.add(objectId.toString());
                        hashMap.put(doiHash, objectIds);
                    }else{
                        ArrayList<String> objectIds = new ArrayList<String>();
                        objectIds.add(objectId.toString());
                        hashMap.put(doiHash,objectIds);
                    }
                }
            }

            newExchange.getOut().setBody(hashMap);
            newExchange.getIn().setBody(hashMap);
            return newExchange;
        }
    }
}
