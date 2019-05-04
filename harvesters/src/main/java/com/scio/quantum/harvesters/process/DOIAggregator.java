package com.scio.quantum.harvesters.process;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.scio.quantum.harvesters.exceptions.DOINotAvailable;
import com.scio.quantum.harvesters.exceptions.NoDatePresent;
import com.scio.quantum.harvesters.exceptions.NoSoundexMatch;
import com.scio.quantum.harvesters.models.publication.PublicationModel;
import org.apache.camel.Exchange;
import org.apache.camel.processor.aggregate.AggregationStrategy;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DOIAggregator implements AggregationStrategy {
    @Override
    public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
        PublicationModel pm = oldExchange.getIn().getBody(PublicationModel.class);
        String internalID = oldExchange.getIn().getHeader("internalId",String.class);
        String doiResolve =  newExchange.getIn().getBody(String.class);
        //System.out.println("DOI Resolve: "+doiResolve);
        String doiHeader = pm.getPubMetadata().getDoi().get(0).getLink().trim();

        if((!doiHeader.isEmpty())&&(doiResolve.equalsIgnoreCase("Resource not found."))){
            String doi = pm.getPubMetadata().getDoi().get(0).getLink();
            String url = pm.getPubMetadata().getContentProvider().get(0).getProviderLink().get(0).getLink();
            String msg ="Warning: DOI ERROR ("+doi+"): "+url;
            pm.getPubMetadata().getDoi().get(0).setLink("");
            newExchange.setException(new DOINotAvailable(msg));
            newExchange.getOut().setBody(pm);
            return newExchange;
        }
        try{
            JsonObject jObject = new JsonParser().parse(doiResolve).getAsJsonObject();
            String pages = retrievePages(jObject);
            String num = retrieveNumber(jObject);
            String volume = retrieveVolume(jObject);
            String year = retrievePublishDate(jObject);
            String title = retrieveTitle(jObject);

            if(!title.isEmpty()){
                if(!pm.getPubMetadata().getTitle().equalsIgnoreCase(title)){
                    if(pm.getPubMetadata().getTitle().length() < title.length()){
                        if(title.contains(pm.getPubMetadata().getTitle())){
                            pm.getPubMetadata().setTitle(title);
                        }
                    }else{
                        boolean soundexMatch = soundexCheckTitle(pm.getPubMetadata().getTitle(),title);
                        if(!soundexMatch){
                            String doi = pm.getPubMetadata().getDoi().get(0).getLink();
                            String url = pm.getPubMetadata().getContentProvider().get(0).getProviderLink().get(0).getLink();
                            String msg ="Warning: DOI METADATA DO NOT MATCH RESOURCE ("+doi+"): "+url;
                            pm.getPubMetadata().getDoi().get(0).setLink("");
                            newExchange.setException(new NoSoundexMatch(msg));
                            newExchange.getOut().setBody(pm);
                            return newExchange;
                        }
                    }
                }
            }

            String summary = retrieveAbstract(jObject);
            ArrayList<String> subjects = retrieveSubject(jObject);

            if(!title.isEmpty()){
                pm.getPubMetadata().setTitle(title);
            }
            if(!summary.isEmpty()){
                pm.getPubMetadata().setSummary(summary);
            }
            if(!pages.isEmpty()){
                pm.getPubMetadata().setPages(pages);
            }
            if(!num.isEmpty()){
                pm.getPubMetadata().setNum(num);
            }
            if(!volume.isEmpty()){
                pm.getPubMetadata().setVolume(volume);
            }
            if(!year.isEmpty()){
                pm.getPubMetadata().setPubYear(year);
            }else{
                try{
                    String url = pm.getPubMetadata().getContentProvider().get(0).getProviderLink().get(0).getLink();
                    checkDate(pm.getPubMetadata().getPubYear(),url);
                }catch(NoDatePresent ex){
                    newExchange.setException(ex);
                    return newExchange;
                }
            }

            if(subjects.size()>0){
                List<String> topics = pm.getPubMetadata().getTopics();
                Iterator<String> iterator = subjects.iterator();
                while(iterator.hasNext()){
                    topics.add(iterator.next());
                }
                pm.getPubMetadata().setTopics(topics);
            }
            newExchange.getOut().setBody(pm);
            return newExchange;
        }catch(Exception ex){
            ex.printStackTrace();
            System.out.println("Exception");
            return oldExchange;
        }
    }

    private boolean soundexCheckTitle(String sourceTitle,String doiTitle){
        String soundexSourceTitle = soundex(sourceTitle);
        String soundexDoiTitle = soundex(doiTitle);
        if(soundexSourceTitle.equalsIgnoreCase(soundexDoiTitle)){
            return true;
        }else{
            return false;
        }
    }
    private String soundex(String s){
        String code, previous, soundex;
        code = s.toUpperCase().charAt(0) + "";
        previous = "7";
        for(int i = 1;i < s.length();i++){
            String current = getCode(s.toUpperCase().charAt(i));
            if(current.length() > 0 && !current.equals(previous)){
                code = code + current;
            }
            previous = current;
        }
        soundex = (code + "0000").substring(0, 4);
        return soundex;
    }

    private String getCode(char c){
        switch(c){
            case 'B': case 'F': case 'P': case 'V':
                return "1";
            case 'C': case 'G': case 'J': case 'K':
            case 'Q': case 'S': case 'X': case 'Z':
                return "2";
            case 'D': case 'T':
                return "3";
            case 'L':
                return "4";
            case 'M': case 'N':
                return "5";
            case 'R':
                return "6";
            default:
                return "";
        }
    }


    private void checkDate(String date,String providerLink) throws NoDatePresent {
        if((date.isEmpty())||date.length()<4){
            throw new NoDatePresent("Error in Metadata: "+providerLink);
        }
    }

    private ArrayList<String> retrieveSubject(JsonObject jObject){
        JsonObject msg = jObject.get("message").getAsJsonObject();
        try{
            JsonArray subject = msg.get("subject").getAsJsonArray();
            ArrayList<String> subjectList = new ArrayList<>();
            Iterator<JsonElement> iterator = subject.iterator();
            while(iterator.hasNext()){
                subjectList.add(iterator.next().getAsString());
            }
            return subjectList;
        }catch(Exception ex){
            ArrayList<String> subjectList = new ArrayList<>();
            return subjectList;
        }
    }

    private String retrieveTitle(JsonObject jObject){
        JsonObject msg = jObject.get("message").getAsJsonObject();
        try{
            JsonArray titles = msg.get("title").getAsJsonArray();
            Type listType = new TypeToken<List<String>>() {}.getType();
            List<String> listTitle = new Gson().fromJson(titles, listType);
            String title = String.join(",", listTitle);
            return title;
        }catch(Exception ex){
            String title = "";
            return title;
        }
    }
    private String retrieveAbstract(JsonObject jObject){
        JsonObject msg = jObject.get("message").getAsJsonObject();
        try{
            String absTract = msg.get("abstract").getAsString();
            return absTract;
        }catch(Exception ex){
            String absTract = "";
            return absTract;
        }
    }
    private String retrievePages(JsonObject jObject) {
        JsonObject msg = jObject.get("message").getAsJsonObject();
        try{
            String pages = msg.get("page").getAsString();
            if (pages.length() > 4) {
                pages = "pp. '" + pages + "'";
            } else {
                pages = "p. '" + pages + "'";
            }
            return pages;
        }catch(Exception ex){
            String pages = "";
            return pages;
        }
    }
    private String retrieveNumber(JsonObject jObject) {
        JsonObject msg = jObject.get("message").getAsJsonObject();
        try{
            String issue = msg.get("issue").getAsString();
            return issue;
        }catch(Exception ex){
            String issue = "";
            return issue;
        }
    }

    private String retrieveVolume(JsonObject jObject) {
        JsonObject msg = jObject.get("message").getAsJsonObject();
        try{
            String volume = msg.get("volume").getAsString();
            volume = "vol. " + volume;
            return volume;
        }catch(Exception ex){
            String volume = "";
            return volume;
        }
    }

    private String retrievePublishDate(JsonObject jObject) {
        String publishedYear = "";
        JsonObject msg = jObject.get("message").getAsJsonObject();
        try{
            JsonObject publishedDate = msg.get("published-online").getAsJsonObject();
            JsonArray dateParts = publishedDate.getAsJsonArray("date-parts");
            Iterator iterator = dateParts.iterator();
            while(iterator.hasNext()){
                JsonArray date = (JsonArray)iterator.next();
                publishedYear = date.get(0).getAsString();
            }
            return publishedYear;
        }catch(Exception ex1){
            try{
                JsonObject publishedDate = msg.get("published-print").getAsJsonObject();
                JsonArray dateParts = publishedDate.getAsJsonArray("date-parts");
                Iterator iterator = dateParts.iterator();
                while(iterator.hasNext()){
                    JsonArray date=(JsonArray)iterator.next();
                    publishedYear = date.get(0).getAsString();
                }
                return publishedYear;
            }catch(Exception ex2){
                return publishedYear;
            }
        }
    }
}
