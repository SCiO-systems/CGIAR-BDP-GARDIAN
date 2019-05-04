package com.scio.quantum.deduplicator.process;

import com.google.gson.Gson;
import com.mongodb.client.result.UpdateResult;
import com.scio.quantum.deduplicator.models.dataset.DatasetModel;
import com.scio.quantum.deduplicator.models.publication.PublicationModel;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.ehcache.Cache;
import org.ehcache.CacheManager;

import java.util.ArrayList;
import java.util.HashMap;

public class UpdateHashIndexProcessor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        if (exchange.getIn() != null) {
            exchange.getOut().setBody(exchange.getIn().getBody());
            String resource = exchange.getProperty("resourceType",String.class);
            UpdateResult result = (UpdateResult) exchange.getIn().getBody();
            if (result.getUpsertedId() != null) {
                ObjectId mongoId = result.getUpsertedId().asObjectId().getValue();
                Document document = exchange.getIn().getHeader("DOCUMENT", Document.class);
                if(resource.equalsIgnoreCase("publication")){
                    Gson gson = new Gson();
                    PublicationModel pm = gson.fromJson(document.toJson(), PublicationModel.class);
                    if (pm.getPubMetadata().getStatus().equalsIgnoreCase("new")) {
                        String titleHash = pm.getPubMetadata().getTitle().toLowerCase();
                        String doiHash = pm.getPubMetadata().getDoi().get(0).getLink().toLowerCase();
                        CacheManager cacheManager = (CacheManager) exchange.getContext().getRegistry().lookupByName("cacheManager");
                        Cache<String, HashMap> cache = cacheManager.getCache("hashMapCache", String.class, HashMap.class);
                        HashMap<String, ArrayList<String>> hm = cache.get("hashmap");
                        if (hm == null) {
                            System.out.println("ERROR");
                        }
                        String id = mongoId.toString();
                        if(hm.containsKey(titleHash)){
                            ArrayList<String> ids = hm.get(titleHash);
                            ids.add(id);
                            hm.put(titleHash,ids);
                        }else{
                            ArrayList<String> ids = new ArrayList<>();
                            ids.add(id);
                            hm.put(titleHash, ids);
                        }
                        if((doiHash.isEmpty())&&(doiHash.length()>3)){
                            if(hm.containsKey(doiHash)) {
                                ArrayList<String> ids = hm.get(doiHash);
                                ids.add(id);
                                hm.put(titleHash,ids);
                            }else{
                                ArrayList<String> ids = new ArrayList<>();
                                ids.add(id);
                                hm.put(doiHash, ids);
                            }
                        }
                        cache.put("hashmap", hm);
                    }

                }else if (resource.equalsIgnoreCase("dataset")){
                    Gson gson = new Gson();
                    DatasetModel dm = gson.fromJson(document.toJson(), DatasetModel.class);
                    if (dm.getDatasetMetadata().getStatus().equalsIgnoreCase("new")) {
                        String titleHash = dm.getDatasetMetadata().getTitle().toLowerCase();
                        String doiHash = "";
                        if(dm.getDatasetMetadata().getDoi().size()>0){
                            doiHash = dm.getDatasetMetadata().getDoi().get(0).getLink().toLowerCase();
                        }

                        CacheManager cacheManager = (CacheManager) exchange.getContext().getRegistry().lookupByName("cacheManager");
                        Cache<String, HashMap> cache = cacheManager.getCache("hashMapCache", String.class, HashMap.class);
                        HashMap<String, ArrayList<String>> hm = cache.get("hashmap");
                        if (hm == null) {
                            System.out.println("ERROR");
                        }
                        String id = mongoId.toString();
                        if(hm.containsKey(titleHash)){
                            ArrayList<String> ids = hm.get(titleHash);
                            ids.add(id);
                            hm.put(titleHash,ids);
                        }else{
                            ArrayList<String> ids = new ArrayList<>();
                            ids.add(id);
                            hm.put(titleHash, ids);
                        }
                        if((doiHash.isEmpty())&&(doiHash.length()>3)){
                            if(hm.containsKey(doiHash)) {
                                ArrayList<String> ids = hm.get(doiHash);
                                ids.add(id);
                                hm.put(titleHash,ids);
                            }else{
                                ArrayList<String> ids = new ArrayList<>();
                                ids.add(id);
                                hm.put(doiHash, ids);
                            }
                        }
                        cache.put("hashmap", hm);
                    }
                }
            }
        }
    }
}
