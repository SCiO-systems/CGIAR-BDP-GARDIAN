package com.scio.quantum.deduplicator.process;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.scio.quantum.deduplicator.models.dataset.DatasetModel;
import com.scio.quantum.deduplicator.models.publication.ContentProvider;
import com.scio.quantum.deduplicator.models.publication.DOI;
import com.scio.quantum.deduplicator.models.publication.PublicationModel;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.ehcache.Cache;
import org.ehcache.CacheManager;

import java.util.*;

import static com.mongodb.client.model.Filters.eq;

//TODO: Add Dataset Model

@SuppressWarnings("Duplicates")
public class FindDuplicateProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {

        String resourceType = exchange.getProperty("resourceType",String.class);
        String refinedDB = exchange.getProperty("refinedDB",String.class);
        MongoClient mc = (MongoClient)exchange.getContext().getRegistry().lookupByName("mongoConnection");


        Document document = (Document)exchange.getIn().getBody();
        document.remove("_id");

        CacheManager cacheManager = (CacheManager)exchange.getContext().getRegistry().lookupByName("cacheManager");
        Cache<String, HashMap> cache = cacheManager.getCache("hashMapCache", String.class, HashMap.class);
        HashMap<String,ArrayList<String>> hm = cache.get("hashmap");

        if(hm == null){
            System.out.println("NULL");
            hm = new HashMap<String,ArrayList<String>>();
        }
        if(resourceType.equalsIgnoreCase("publication")){
            Document updatedDocument = deduplicatePublication(document,mc,hm,refinedDB);
            exchange.getOut().setBody(updatedDocument);
            exchange.getOut().setHeader("DOCUMENT",updatedDocument);
        }else if(resourceType.equalsIgnoreCase("dataset")){
            System.out.println("DEDUPLICATE DATASET");
            Document updatedDocument = deduplicateDataset(document,mc,hm,refinedDB);
            exchange.getOut().setBody(updatedDocument);
            exchange.getOut().setHeader("DOCUMENT",updatedDocument);
        }
    }


    private Document deduplicateDataset(Document rawDocument, MongoClient mc,HashMap<String,ArrayList<String>> hm,String refinedDB){
        MongoCollection moco = mc.getDatabase(refinedDB).getCollection("dataset");
        Gson gson = new Gson();
        DatasetModel dtRaw = gson.fromJson(rawDocument.toJson(), DatasetModel.class);
        Document savedDocument = null;
        List<com.scio.quantum.deduplicator.models.dataset.DOI> doiList = dtRaw.getDatasetMetadata().getDoi();
        Iterator<com.scio.quantum.deduplicator.models.dataset.DOI> doiIt = doiList.iterator();
        String title = dtRaw.getDatasetMetadata().getTitle().trim();
        boolean foundDOIDuplicate = false;
        ArrayList<DatasetModel> datasetModels = new ArrayList<>();
        if(doiIt.hasNext()){
            String link = doiIt.next().getLink();
            if((!link.isEmpty())&&(link.length()>3)) {
                String hashLink = link.toLowerCase();
                if (hm.containsKey(hashLink)) {
                    String id = "";
                    ArrayList<String> ids = hm.get(hashLink);
                    if(ids.size() == 1){
                        id = ids.get(0);
                    }
                    System.out.println("ID: "+id);
                    Object tempObject = moco.find(eq("_id", new ObjectId(id))).first();
                    if(tempObject != null){
                        savedDocument = (Document)tempObject;
                        foundDOIDuplicate = true;
                    }
                }
            }
        }
        if((!title.isEmpty())&&(foundDOIDuplicate == false)) {
            String hashTitle = title.toLowerCase();
            if (hm.containsKey(hashTitle)) {
                ArrayList<String> ids = hm.get(hashTitle);
                if(ids.size() == 1){
                    String id = ids.get(0);
                    Object tempObject = moco.find(eq("_id", new ObjectId(id))).first();
                    if(tempObject != null){
                        savedDocument = (Document)tempObject;
                    }
                }else{
                    for(int i=0;i<ids.size();i++){
                        String id = ids.get(i);
                        Object tempObject = moco.find(eq("_id", new ObjectId(id))).first();
                        if(tempObject != null){
                            DatasetModel dm = gson.fromJson(((Document)tempObject).toJson(), DatasetModel.class);
                            dm.getDatasetMetadata().setQuantumId(id);
                            datasetModels.add(dm);
                        }
                    }
                }
            }
        }
        if(datasetModels.size()<1){
            if (savedDocument == null){
                DatasetModel dm = gson.fromJson(rawDocument.toJson(), DatasetModel.class);
                dm.getDatasetMetadata().setStatus("new");
                String titleHash = DigestUtils.sha256Hex(dm.getDatasetMetadata().getTitleHash());
                String doiHash = "";
                if(dm.getDatasetMetadata().getDoi().size()>0){
                    doiHash = DigestUtils.sha256Hex(dm.getDatasetMetadata().getDoi().get(0).getLink());
                }

                dm.getDatasetMetadata().setTitleHash(titleHash);
                dm.getDatasetMetadata().setDoiHash(doiHash);
                dm.getDatasetMetadata().getTopics().removeIf(Objects::isNull);
                dm.getDatasetMetadata().getGeographic().removeIf(Objects::isNull);
                String json = gson.toJson(dm,DatasetModel.class);
                JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
                Document newDocument = Document.parse(jsonObject.toString());
                return newDocument;
            }else{
                DatasetModel dm = gson.fromJson(savedDocument.toJson(), DatasetModel.class);
                dm = update(dtRaw,dm);
                if(dm.getDatasetMetadata().getStatus().equalsIgnoreCase("new")){
                    String titleHash = DigestUtils.sha256Hex(dm.getDatasetMetadata().getTitle());
                    String doiHash = "";
                    if(dm.getDatasetMetadata().getDoi().size()>0){
                        doiHash = DigestUtils.sha256Hex(dm.getDatasetMetadata().getDoi().get(0).getLink());
                    }
                    dm.getDatasetMetadata().setTitleHash(titleHash);
                    dm.getDatasetMetadata().setDoiHash(doiHash);
                    dm.getDatasetMetadata().getTopics().removeIf(Objects::isNull);
                    dm.getDatasetMetadata().getGeographic().removeIf(Objects::isNull);
                    String json = gson.toJson(dm,DatasetModel.class);
                    JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
                    Document newDocument = Document.parse(jsonObject.toString());
                    return newDocument;
                }else{
                    ObjectId id = (ObjectId)savedDocument.get("_id");
                    savedDocument.remove("_id");
                    String json = gson.toJson(dm,DatasetModel.class);
                    JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
                    Document updatedDocument = Document.parse(jsonObject.toString());
                    updatedDocument.put("_id",id);
                    return updatedDocument;
                }
            }
        }else{
            DatasetModel dm = update(dtRaw,datasetModels);
            if(dm.getDatasetMetadata().getStatus().equalsIgnoreCase("new")){
                String titleHash = DigestUtils.sha256Hex(dm.getDatasetMetadata().getTitle());
                String doiHash = "";
                if(dm.getDatasetMetadata().getDoi().size()>0){
                    doiHash = DigestUtils.sha256Hex(dm.getDatasetMetadata().getDoi().get(0).getLink());
                }
                dm.getDatasetMetadata().setTitleHash(titleHash);
                dm.getDatasetMetadata().setDoiHash(doiHash);
                dm.getDatasetMetadata().getTopics().removeIf(Objects::isNull);
                dm.getDatasetMetadata().getGeographic().removeIf(Objects::isNull);
                String json = gson.toJson(dm,DatasetModel.class);
                JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
                Document newDocument = Document.parse(jsonObject.toString());
                return newDocument;
            }else{
                String json = gson.toJson(dm,DatasetModel.class);
                JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
                Document updatedDocument = Document.parse(jsonObject.toString());
                ObjectId quantumId = new ObjectId(dm.getDatasetMetadata().getQuantumId());
                updatedDocument.put("_id",quantumId);
                return updatedDocument;
            }
        }
    }

    private Document deduplicatePublication(Document rawDocument, MongoClient mc,HashMap<String,ArrayList<String>> hm,String refinedDB){
        MongoCollection moco = mc.getDatabase(refinedDB).getCollection("publication");
        Gson gson = new Gson();
        PublicationModel pmRaw = gson.fromJson(rawDocument.toJson(), PublicationModel.class);
        Document savedDocument = null;
        List<DOI> doiList = pmRaw.getPubMetadata().getDoi();
        Iterator<DOI> doiIt = doiList.iterator();
        String title = pmRaw.getPubMetadata().getTitle().trim();
        boolean foundDOIDuplicate = false;
        ArrayList<PublicationModel> publicationModels = new ArrayList<>();
        if(doiIt.hasNext()){
            String link = doiIt.next().getLink();
            if((!link.isEmpty())&&(link.length()>3)) {
                String hashLink = link.toLowerCase();
                if (hm.containsKey(hashLink)) {
                    String id = "";
                    ArrayList<String> ids = hm.get(hashLink);
                    if(ids.size() == 1){
                        id = ids.get(0);
                    }
                    System.out.println("ID: "+id);
                    Object tempObject = moco.find(eq("_id", new ObjectId(id))).first();
                    if(tempObject != null){
                        savedDocument = (Document)tempObject;
                        foundDOIDuplicate = true;
                    }
                }
            }
        }

        if((!title.isEmpty())&&(foundDOIDuplicate == false)) {
            String hashTitle = title.toLowerCase();
            if (hm.containsKey(hashTitle)) {
                ArrayList<String> ids = hm.get(hashTitle);
                if(ids.size() == 1){
                    String id = ids.get(0);
                    Object tempObject = moco.find(eq("_id", new ObjectId(id))).first();
                    if(tempObject != null){
                        savedDocument = (Document)tempObject;
                    }
                }else{
                    for(int i=0;i<ids.size();i++){
                        String id = ids.get(i);
                        Object tempObject = moco.find(eq("_id", new ObjectId(id))).first();
                        if(tempObject != null){
                            PublicationModel pm = gson.fromJson(((Document)tempObject).toJson(), PublicationModel.class);
                            pm.getPubMetadata().setQuantumId(id);
                            publicationModels.add(pm);
                        }
                    }
                }
            }
        }
        if(publicationModels.size()<1){
            if (savedDocument == null){
                PublicationModel pm = gson.fromJson(rawDocument.toJson(), PublicationModel.class);
                pm.getPubMetadata().setStatus("new");
                String titleHash = DigestUtils.sha256Hex(pm.getPubMetadata().getTitle());
                String doiHash = DigestUtils.sha256Hex(pm.getPubMetadata().getDoi().get(0).getLink());
                pm.getPubMetadata().setTitleHash(titleHash);
                pm.getPubMetadata().setDoiHash(doiHash);
                pm.getPubMetadata().getTopics().removeIf(Objects::isNull);
                pm.getPubMetadata().getGeographic().removeIf(Objects::isNull);
                String json = gson.toJson(pm,PublicationModel.class);
                JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
                Document newDocument = Document.parse(jsonObject.toString());
                return newDocument;
            }else{
                PublicationModel pm = gson.fromJson(savedDocument.toJson(), PublicationModel.class);
                pm = update(pmRaw,pm);
                if(pm.getPubMetadata().getStatus().equalsIgnoreCase("new")){
                    String titleHash = DigestUtils.sha256Hex(pm.getPubMetadata().getTitle());
                    String doiHash = DigestUtils.sha256Hex(pm.getPubMetadata().getDoi().get(0).getLink());
                    pm.getPubMetadata().setTitleHash(titleHash);
                    pm.getPubMetadata().setDoiHash(doiHash);
                    pm.getPubMetadata().getTopics().removeIf(Objects::isNull);
                    pm.getPubMetadata().getGeographic().removeIf(Objects::isNull);
                    String json = gson.toJson(pm,PublicationModel.class);
                    JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
                    Document newDocument = Document.parse(jsonObject.toString());
                    return newDocument;
                }else{
                    ObjectId id = (ObjectId)savedDocument.get("_id");
                    savedDocument.remove("_id");
                    String json = gson.toJson(pm,PublicationModel.class);
                    JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
                    Document updatedDocument = Document.parse(jsonObject.toString());
                    updatedDocument.put("_id",id);
                    return updatedDocument;
                }
            }
        }else{
            PublicationModel pm = update(pmRaw,publicationModels);
            if(pm.getPubMetadata().getStatus().equalsIgnoreCase("new")){
                String titleHash = DigestUtils.sha256Hex(pm.getPubMetadata().getTitle());
                String doiHash = DigestUtils.sha256Hex(pm.getPubMetadata().getDoi().get(0).getLink());
                pm.getPubMetadata().setTitleHash(titleHash);
                pm.getPubMetadata().setDoiHash(doiHash);
                pm.getPubMetadata().getTopics().removeIf(Objects::isNull);
                pm.getPubMetadata().getGeographic().removeIf(Objects::isNull);
                String json = gson.toJson(pm,PublicationModel.class);
                JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
                Document newDocument = Document.parse(jsonObject.toString());
                return newDocument;
            }else{
                String json = gson.toJson(pm,PublicationModel.class);
                JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
                Document updatedDocument = Document.parse(jsonObject.toString());
                ObjectId quantumId = new ObjectId(pm.getPubMetadata().getQuantumId());
                updatedDocument.put("_id",quantumId);
                return updatedDocument;
            }
        }
    }

    private DatasetModel update(DatasetModel dtRaw, ArrayList<DatasetModel> dt){
        com.scio.quantum.deduplicator.models.dataset.ContentProvider cpRaw =  dtRaw.getDatasetMetadata().getContentProvider().get(0);
        String newProviderID = dtRaw.getDatasetMetadata().getContentProvider().get(0).getContentProviderID();
        String newProviderURL = dtRaw.getDatasetMetadata().getContentProvider().get(0).getProviderLink().get(0).getLink();
        Iterator<DatasetModel> itDatasetModel = dt.iterator();
        boolean sameProvider = false;
        while(itDatasetModel.hasNext()){
            DatasetModel dtTemp = itDatasetModel.next();
            Iterator<com.scio.quantum.deduplicator.models.dataset.ContentProvider> contentProvider = dtTemp.getDatasetMetadata().getContentProvider().iterator();
            while(contentProvider.hasNext()){
                com.scio.quantum.deduplicator.models.dataset.ContentProvider cpTemp = contentProvider.next();
                String providerID = cpTemp.getContentProviderID();
                String providerURL = cpTemp.getProviderLink().get(0).getLink();
                if(newProviderID.equalsIgnoreCase(providerID)){
                    if(newProviderURL.equalsIgnoreCase(providerURL)){
                        dtTemp.getDatasetMetadata().setStatus("update");
                        return dtTemp;
                    }else{
                        sameProvider = true;
                    }
                }
            }
            if(sameProvider == false){
                List<com.scio.quantum.deduplicator.models.dataset.ContentProvider> cps = dtTemp.getDatasetMetadata().getContentProvider();
                cps.add(cpRaw);
                dtTemp.getDatasetMetadata().setContentProvider(cps);
                dtTemp.getDatasetMetadata().setStatus("update");
                return dtTemp;
            }
        }
        dtRaw.getDatasetMetadata().setStatus("new");
        dtRaw.getDatasetMetadata().getTopics().removeIf(Objects::isNull);
        dtRaw.getDatasetMetadata().getGeographic().removeIf(Objects::isNull);
        return dtRaw;
    }

    private PublicationModel update(PublicationModel pmRaw, ArrayList<PublicationModel> pm){
        ContentProvider cpRaw =  pmRaw.getPubMetadata().getContentProvider().get(0);
        String newProviderID = pmRaw.getPubMetadata().getContentProvider().get(0).getContentProviderID();
        String newProviderURL = pmRaw.getPubMetadata().getContentProvider().get(0).getProviderLink().get(0).getLink();
        Iterator<PublicationModel> itPublicationModel = pm.iterator();
        boolean sameProvider = false;
        while(itPublicationModel.hasNext()){
            PublicationModel pmTemp = itPublicationModel.next();
            Iterator<ContentProvider> contentProvider = pmTemp.getPubMetadata().getContentProvider().iterator();
            while(contentProvider.hasNext()){
                ContentProvider cpTemp = contentProvider.next();
                String providerID = cpTemp.getContentProviderID();
                String providerURL = cpTemp.getProviderLink().get(0).getLink();
                if(newProviderID.equalsIgnoreCase(providerID)){
                    if(newProviderURL.equalsIgnoreCase(providerURL)){
                        pmTemp.getPubMetadata().setStatus("update");
                        return pmTemp;
                    }else{
                        sameProvider = true;
                    }
                }
            }
            if(sameProvider == false){
                List<ContentProvider> cps = pmTemp.getPubMetadata().getContentProvider();
                cps.add(cpRaw);
                pmTemp.getPubMetadata().setContentProvider(cps);
                pmTemp.getPubMetadata().setStatus("update");
                return pmTemp;
            }
        }
        pmRaw.getPubMetadata().setStatus("new");
        pmRaw.getPubMetadata().getTopics().removeIf(Objects::isNull);
        pmRaw.getPubMetadata().getGeographic().removeIf(Objects::isNull);
        return pmRaw;
    }

    private DatasetModel update(DatasetModel dtRaw,DatasetModel dt){
        String newProviderID = dtRaw.getDatasetMetadata().getContentProvider().get(0).getContentProviderID();
        String newProviderURL = dtRaw.getDatasetMetadata().getContentProvider().get(0).getProviderLink().get(0).getLink();
        boolean addProvider = true;
        for(int i=0;i<dt.getDatasetMetadata().getContentProvider().size();i++){
            com.scio.quantum.deduplicator.models.dataset.ContentProvider cp = dt.getDatasetMetadata().getContentProvider().get(i);
            String existingProviderID = cp.getContentProviderID();
            String existingProviderURL = cp.getProviderLink().get(0).getLink();
            if(existingProviderID.equalsIgnoreCase(newProviderID)){
                if(existingProviderURL.equalsIgnoreCase(newProviderURL)){
                    addProvider = false;
                    break;
                }else{
                    dtRaw.getDatasetMetadata().setStatus("new");
                    dtRaw.getDatasetMetadata().getTopics().removeIf(Objects::isNull);
                    dtRaw.getDatasetMetadata().getGeographic().removeIf(Objects::isNull);
                    return dtRaw;
                }
            }
        }
        if(addProvider == true){
            com.scio.quantum.deduplicator.models.dataset.ContentProvider cp = dtRaw.getDatasetMetadata().getContentProvider().get(0);
            List<com.scio.quantum.deduplicator.models.dataset.ContentProvider> listCp = dt.getDatasetMetadata().getContentProvider();
            listCp.add(cp);
            dt.getDatasetMetadata().setContentProvider(listCp);
        }

        String issuedYearPm = dt.getDatasetMetadata().getIssuedYear();
        if(issuedYearPm.isEmpty()){
            String issuedYear = dtRaw.getDatasetMetadata().getIssuedYear();
            dt.getDatasetMetadata().setIssuedYear(issuedYear);
        }
        Map<String,String> topics = new HashMap();
        List<String> dtRawTopics = dtRaw.getDatasetMetadata().getTopics();
        List<String> dtTopics = dt.getDatasetMetadata().getTopics();
        Iterator<String> itRawTopics = dtRawTopics.iterator();
        Iterator<String> itTopics = dtTopics.iterator();
        while(itRawTopics.hasNext()){
            String topic = itRawTopics.next().toLowerCase();
            topics.put(topic,"string");
        }
        while(itTopics.hasNext()){
            String topic = itTopics.next().toLowerCase();
            topics.put(topic,"string");
        }

        List updatedTopics = new ArrayList<>(topics.keySet());
        dt.getDatasetMetadata().setTopics(updatedTopics);
        Map<String,String> geography = new HashMap();
        List<String> pmRawGeography = dtRaw.getDatasetMetadata().getGeographic();
        List<String> pmGeography = dt.getDatasetMetadata().getGeographic();
        Iterator<String> itRawGeography = pmRawGeography.iterator();
        Iterator<String> itGeography = pmGeography.iterator();
        while(itRawGeography.hasNext()){
            String geo = itRawGeography.next().toLowerCase();
            geography.put(geo,"string");
        }
        while(itGeography.hasNext()){
            String geo = itGeography.next().toLowerCase();
            geography.put(geo,"string");
        }
        List updatedGeography = new ArrayList<>(geography.keySet());
        dt.getDatasetMetadata().setGeographic(updatedGeography);
        if(dt.getDatasetMetadata().getSummary().isEmpty()){
            String summary = dtRaw.getDatasetMetadata().getSummary();
            dt.getDatasetMetadata().setSummary(summary);
        }
        dt.getDatasetMetadata().setStatus("update");
        return dt;

    }

    private PublicationModel update(PublicationModel pmRaw,PublicationModel pm){
        String newProviderID = pmRaw.getPubMetadata().getContentProvider().get(0).getContentProviderID();
        String newProviderURL = pmRaw.getPubMetadata().getContentProvider().get(0).getProviderLink().get(0).getLink();
        boolean addProvider = true;
        //boolean addLink = false;
        //int indexContentProvider = -1;
        for(int i=0;i<pm.getPubMetadata().getContentProvider().size();i++){
            ContentProvider cp = pm.getPubMetadata().getContentProvider().get(i);
            String existingProviderID = cp.getContentProviderID();
            String existingProviderURL = cp.getProviderLink().get(0).getLink();
            if(existingProviderID.equalsIgnoreCase(newProviderID)){
                if(existingProviderURL.equalsIgnoreCase(newProviderURL)){
                    addProvider = false;
                    break;
                }else{
                    pmRaw.getPubMetadata().setStatus("new");
                    pmRaw.getPubMetadata().getTopics().removeIf(Objects::isNull);
                    pmRaw.getPubMetadata().getGeographic().removeIf(Objects::isNull);
                    return pmRaw;
                }
            }
        }
        if(addProvider == true){
           ContentProvider cp = pmRaw.getPubMetadata().getContentProvider().get(0);
           List<ContentProvider> listCp = pm.getPubMetadata().getContentProvider();
           listCp.add(cp);
           pm.getPubMetadata().setContentProvider(listCp);
        }
        /*if(addLink == true){
            ContentProvider cp = pmRaw.getPubMetadata().getContentProvider().get(0);
            List<ContentProvider> listCp = pm.getPubMetadata().getContentProvider();
            ContentProvider savedCp = listCp.get(indexContentProvider);
            List<ProviderLink> pls = savedCp.getProviderLink();
            ProviderLink pl = cp.getProviderLink().get(0);
            pls.add(pl);
            savedCp.setProviderLink(pls);
            listCp.remove(indexContentProvider);
            listCp.add(savedCp);
            pm.getPubMetadata().setContentProvider(listCp);
        }*/
        String issuedYearPm = pm.getPubMetadata().getIssuedYear();
        if(issuedYearPm.isEmpty()){
            String issuedYear = pmRaw.getPubMetadata().getIssuedYear();
            pm.getPubMetadata().setIssuedYear(issuedYear);
        }
        Map<String,String> topics = new HashMap();
        List<String> pmRawTopics = pmRaw.getPubMetadata().getTopics();
        List<String> pmTopics = pm.getPubMetadata().getTopics();
        Iterator<String> itRawTopics = pmRawTopics.iterator();
        Iterator<String> itTopics = pmTopics.iterator();
        while(itRawTopics.hasNext()){
            String topic = itRawTopics.next().toLowerCase();
            topics.put(topic,"string");
        }
        while(itTopics.hasNext()){
            String topic = itTopics.next().toLowerCase();
            topics.put(topic,"string");
        }
        List updatedTopics = new ArrayList<>(topics.keySet());
        pm.getPubMetadata().setTopics(updatedTopics);
        Map<String,String> geography = new HashMap();
        List<String> pmRawGeography = pmRaw.getPubMetadata().getGeographic();
        List<String> pmGeography = pm.getPubMetadata().getGeographic();
        Iterator<String> itRawGeography = pmRawGeography.iterator();
        Iterator<String> itGeography = pmGeography.iterator();
        while(itRawGeography.hasNext()){
            String geo = itRawGeography.next().toLowerCase();
            geography.put(geo,"string");
        }
        while(itGeography.hasNext()){
            String geo = itGeography.next().toLowerCase();
            geography.put(geo,"string");
        }
        List updatedGeography = new ArrayList<>(geography.keySet());
        pm.getPubMetadata().setGeographic(updatedGeography);
        boolean isOpenAccess = pmRaw.getPubMetadata().isOpenAccess();
        if(isOpenAccess == true){
            pm.getPubMetadata().setOpenAccess(isOpenAccess);
        }
        String docLink = pm.getPubMetadata().getDocLink();
        if(docLink.isEmpty()){
            pm.getPubMetadata().setDocLink(pmRaw.getPubMetadata().getDocLink());
        }
        if(pm.getPubMetadata().getSummary().isEmpty()){
            String summary = pmRaw.getPubMetadata().getSummary();
            pm.getPubMetadata().setSummary(summary);
        }
        boolean rebuildCitation = false;
        if(pm.getPubMetadata().getNum().isEmpty()){
            if(!pmRaw.getPubMetadata().getNum().isEmpty()){
                pm.getPubMetadata().setNum(pmRaw.getPubMetadata().getNum());
                rebuildCitation = true;
            }
        }
        if(pm.getPubMetadata().getPages().isEmpty()){
            if(!pmRaw.getPubMetadata().getPages().isEmpty()){
                pm.getPubMetadata().setPages(pmRaw.getPubMetadata().getPages());
                rebuildCitation = true;
            }
        }
        if(pm.getPubMetadata().getSeries().isEmpty()){
            if(!pmRaw.getPubMetadata().getSeries().isEmpty()){
                pm.getPubMetadata().setSeries(pmRaw.getPubMetadata().getSeries());
                rebuildCitation = true;
            }
        }
        if(pm.getPubMetadata().getVolume().isEmpty()){
            if(!pmRaw.getPubMetadata().getVolume().isEmpty()){
                pm.getPubMetadata().setVolume(pmRaw.getPubMetadata().getVolume());
                rebuildCitation = true;
            }
        }
        if(pm.getPubMetadata().getPublisher().isEmpty()){
            if(!pmRaw.getPubMetadata().getPublisher().isEmpty()){
                pm.getPubMetadata().setPublisher(pmRaw.getPubMetadata().getPublisher());
                rebuildCitation = true;
            }
        }
        if(rebuildCitation == true){
            pm.getPubMetadata().setCitation(buildCitation(pm));
        }
        pm.getPubMetadata().setStatus("update");
        return pm;
    }

    private String buildCitation(PublicationModel pm){
        String citation = "";
        String authors = pm.getPubMetadata().getAuthors();
        int occurrences = StringUtils.countMatches(authors,',');
        if(occurrences>3){
            authors = authors.substring(authors.indexOf(","))+" et al., ";
            citation += authors;
        }else{
            citation += authors+", ";
        }
        String title = pm.getPubMetadata().getTitle();
        citation +="\'"+title+"\'";
        String series = pm.getPubMetadata().getSeries();
        if(!series.isEmpty()){
            citation += ", "+series;
        }
        String volume = pm.getPubMetadata().getVolume();
        if(!volume.isEmpty()){
            citation += ", "+volume;
        }
        String num = pm.getPubMetadata().getNum();
        if(!num.isEmpty()){
            citation += ", "+num;
        }
        String pages = pm.getPubMetadata().getPages();
        if(StringUtils.isNumeric(pages)){
            citation += ", p."+pages;
        }else{
            citation += ", pp."+pages;
        }
        String publisher = pm.getPubMetadata().getPublisher();
        if(!publisher.isEmpty()){
            citation += ", "+publisher;
        }
        String pubYear = pm.getPubMetadata().getPubYear();
        if(!pubYear.isEmpty()){
            citation += ", "+pubYear;
        }
        return citation;
    }



}
