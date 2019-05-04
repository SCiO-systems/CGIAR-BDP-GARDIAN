package com.scio.quantum.extractor.process;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.scio.quantum.extractor.models.publication.ContentProvider;
import com.scio.quantum.extractor.models.publication.ExtractedMetadata;
import com.scio.quantum.extractor.models.publication.FAIR;
import com.scio.quantum.extractor.models.publication.PublicationModel;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CalculateFairProcessor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        ArrayList<ExtractedMetadata> emList = exchange.getIn().getBody(ArrayList.class);
        Object obj = exchange.getProperty("OBJECT");
        if(obj instanceof PublicationModel){
            PublicationModel pm = (PublicationModel) obj;
            pm.getPubMetadata().setExtractedMetadata(emList);

            double findability = calculateFindability(pm);
            double accessibility = calculateAccesibility(pm);
            double interoperability = calculateInteroperability(pm);
            double reusability = calculateReusability(findability,accessibility,interoperability);
            FAIR fair = new FAIR(
                    Double.toString(findability),
                    Double.toString(accessibility),
                    Double.toString(interoperability),
                    Double.toString(reusability));
            pm.getPubMetadata().setFair(fair);
            String objectId = pm.getPubMetadata().getQuantumId();
            ObjectId id = new ObjectId(objectId);
            Gson gson = new Gson();
            String json = gson.toJson(pm,PublicationModel.class);
            JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
            Document updatedDocument = Document.parse(jsonObject.toString());
            updatedDocument.put("_id",id);
            exchange.getIn().setBody(updatedDocument);
        }
    }

    private double calculateFindability(Object model) {

        //Indexed to GARDIAN
        double findability = 0.5;

        List<ContentProvider> pl = null;
        String title = "";
        String authors = "";
        String publicationDate = "";

        String summary = "";
        List<String> topics = new ArrayList<>();
        List<String> geographic = new ArrayList<>();
        List<ExtractedMetadata> emList = new ArrayList<>();

        boolean hasDoi = false;
        boolean hasHdl = false;

        if(model instanceof PublicationModel){
            PublicationModel md =  (PublicationModel)model;
            pl = md.getPubMetadata().getContentProvider();
            summary = md.getPubMetadata().getSummary();
            topics = md.getPubMetadata().getTopics();
            geographic = md.getPubMetadata().getGeographic();
            emList = md.getPubMetadata().getExtractedMetadata();

            if(md.getPubMetadata().getDoi().size()>0){
                hasDoi = true;
            }
        }

        if(!title.isEmpty()){
            findability += 0.25 ;
        }
        if(!authors.isEmpty()){
            findability += 0.25;
        }
        if(!publicationDate.isEmpty()){
            findability += 0.25;
        }
        findability += checkURL(pl);

        if(!summary.isEmpty()){
            findability += 0.50;
        }
        findability += Math.min(0.5, topics.size()*0.125);


        if(geographic.size()>0){
            findability += 1;
        }else{
            Iterator<ExtractedMetadata> itEm = emList.iterator();
            while(itEm.hasNext()){
                ExtractedMetadata em = itEm.next();
                if(em.getVocabulary().equalsIgnoreCase("geonames")){
                    if (em.getData().size()>0){
                        findability += 1;
                    }
                    break;
                }
            }
        }

        Iterator<ExtractedMetadata> itEm = emList.iterator();
        while(itEm.hasNext()){
            ExtractedMetadata em = itEm.next();
            if(em.getVocabulary().equalsIgnoreCase("agrovoc")){
                findability += Math.min(1, topics.size()*0.25);
                break;
            }
        }

        if(hasDoi ==  true){
            findability += 0.25;
        }
        findability += checkHDL(pl);



        return findability;
    }

    private double calculateAccesibility(Object model){

        double accesibility = 1.5;
        if(model instanceof PublicationModel){
            PublicationModel md =  (PublicationModel)model;
            boolean isOpenAccess = md.getPubMetadata().isOpenAccess();
            if(isOpenAccess == true){
                accesibility += 2.5;
            }else{
                accesibility += 1.0;
            }

            if(!md.getPubMetadata().getDocLink().isEmpty()){
                accesibility += 1.0;
            }
        }
        return accesibility;
    }

    private double calculateInteroperability(Object model){
        double interoperability = 1.5;
        if(model instanceof PublicationModel){
            interoperability += 1.5;
            PublicationModel md =  (PublicationModel)model;
            List<ExtractedMetadata> emList = md.getPubMetadata().getExtractedMetadata();
            Iterator<ExtractedMetadata> itList = emList.iterator();
            while(itList.hasNext()){
                ExtractedMetadata em = itList.next();
                if(!em.getVocabulary().equalsIgnoreCase("license")){
                    if(em.getVocabulary().equalsIgnoreCase("agrovoc")){
                        if(em.getData().size()>0){
                            interoperability += 0.5;
                        }
                    }else if(em.getVocabulary().equalsIgnoreCase("geonames")){
                        if(em.getData().size()>0){
                            interoperability += 0.25;
                        }
                    }else{
                        if(em.getData().size()>0){
                            interoperability += 0.25;
                        }
                    }
                }
            }
        }
        return interoperability;
    }

    private double calculateReusability(double findability,double accessibility,double interoperability){
        double reusability = (findability + accessibility + interoperability) / 3.0;
        return reusability;
    }


    private double checkURL(List<ContentProvider> pl){
        Iterator<ContentProvider> plIterator = pl.iterator();

        while(plIterator.hasNext()){
            ContentProvider cp = plIterator.next();
            if(cp.getProviderLink().size()>0){
                return 0.25;
            }
        }
        return 0;
    }

    private double checkHDL(List<ContentProvider> pl){
        Iterator<ContentProvider> plIterator = pl.iterator();

        while(plIterator.hasNext()){
            ContentProvider cp = plIterator.next();
            if(cp.getHDL().size()>0){
                return 0.25;
            }
        }
        return 0;
    }
}
