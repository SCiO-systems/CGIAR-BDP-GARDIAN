package com.scio.quantum.harvesters.process;

import com.google.gson.internal.LinkedTreeMap;
import com.scio.quantum.harvesters.exceptions.AlreadyProcessedException;
import com.scio.quantum.harvesters.exceptions.NoTitlePresent;
import com.scio.quantum.harvesters.models.dataset.*;
import com.scio.quantum.harvesters.models.external.dataverse.resource.*;
import com.scio.quantum.harvesters.models.external.dataverse.resource.File;
import com.scio.quantum.harvesters.utilities.RegexCollection;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class DataverseCollectionProcessor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        Dataverse dataverse = exchange.getIn().getBody(Dataverse.class);
        String index = exchange.getProperty("INDEX",String.class);
        String persistenceURL = dataverse.getPersistentUrl();
        try{
            checkIfHasBeenProcessed(index,persistenceURL);
        }catch(AlreadyProcessedException ex) {
            exchange.setException(ex);
        }

        String identifier = dataverse.getIdentifier();
        String authority = dataverse.getAuthority();
        String protocol = dataverse.getProtocol();
        String persistenceId = protocol + ":" + authority + "/" + identifier;
        String pubYear = "";

        if(dataverse.getPublicationDate() == null){
            pubYear = dataverse.getDatasetVersion().getCreateTime();

        }else{
            pubYear = dataverse.getPublicationDate();
        }
        pubYear = pubYear.substring(0, 4);
        if(!StringUtils.isNumeric(pubYear)){
            pubYear = dataverse.getDatasetVersion().getCreateTime();
            pubYear = pubYear.substring(0, 4);
        }

        String issuedYear = dataverse.getDatasetVersion().getProductionDate();
        issuedYear = issuedYear.substring(0, 4);
        if(!StringUtils.isNumeric(issuedYear)){
            issuedYear = dataverse.getDatasetVersion().getCreateTime();
            issuedYear = issuedYear.substring(0, 4);
        }

        String license = dataverse.getDatasetVersion().getLicense();
        String termsOfUse = dataverse.getDatasetVersion().getTermsOfUse();
        String accessibility = "open";
        String authors = "";


        List<Object> fields = dataverse.getDatasetVersion().getMetadataBlocks().getCitation().getFields();
        Iterator fieldsIt = fields.iterator();
        List<Object> geoFields = dataverse.getDatasetVersion().getMetadataBlocks().getGeospatial().getFields();
        Iterator geoFieldsIt = geoFields.iterator();

        ArrayList<String> geoTerms = new ArrayList<>();
        while(geoFieldsIt.hasNext()){
            Object obscureField = geoFieldsIt.next();
            if(obscureField instanceof Field){
                String value = ((Field) obscureField).getValue();
                geoTerms.add(value);
            }else if (obscureField instanceof Field_){
                List<Object> objects = ((Field_) obscureField).getValue();
                Iterator objectsIt = objects.iterator();
                while(objectsIt.hasNext()){
                    Object obscureValue = objectsIt.next();
                    if(obscureValue instanceof String){
                        geoTerms.add((String)obscureValue);
                    }else if(obscureValue instanceof Value){
                        String geoTerm = "";
                        Value value = (Value)obscureValue;
                        if( value.getCountry() != null ){
                            geoTerm = value.getCountry().getValue();
                        }else if( value.getCountry() != null){
                            geoTerm = value.getOtherGeographicCoverage().getValue();
                        }
                        if(!geoTerm.isEmpty()){
                            geoTerms.add(geoTerm);
                        }
                    }
                }
            }
        }

        String title = "";
        String series = "";
        String summary = "";
        ArrayList<String> topics = new ArrayList<>();
        while(fieldsIt.hasNext()){
            Object obscureField = fieldsIt.next();
            if(obscureField instanceof Field){
                Field field = (Field) obscureField;
                if(field.getTypeName().equalsIgnoreCase("title")){
                    title = field.getValue();
                }else if(field.getTypeName().equalsIgnoreCase("series")){
                    series = field.getValue();
                }
            }else if(obscureField instanceof Field_){
                Field_ field = (Field_) obscureField;
                if(field.getTypeName().equalsIgnoreCase("dsDescription")){
                    List<Object> value = field.getValue();
                    Iterator valueIt = value.iterator();
                    while(valueIt.hasNext()){
                        Object valueType = valueIt.next();
                        if(valueType instanceof LinkedTreeMap){
                            LinkedTreeMap<String,LinkedTreeMap> summaryMap = (LinkedTreeMap)((LinkedTreeMap) valueType).get("dsDescriptionValue");
                            summary = summary + summaryMap.get("value");
                        }
                    }
                }else if(field.getTypeName().equalsIgnoreCase("author")){
                    String type = "author";
                    List<Object>  value = field.getValue();
                    ArrayList<String> authorsList = valueTypeConversion(value,type);
                    authors =  String.join(", ", authorsList);
                }else if(field.getTypeName().equalsIgnoreCase("keyword")){
                    List<Object>  value = field.getValue();
                    String type = "keyword";
                    topics.addAll(valueTypeConversion(value,type));
                }else if(field.getTypeName().equalsIgnoreCase("topicClassification")){
                    List<Object>  value = field.getValue();
                    String type = "topicClassification";
                    topics.addAll(valueTypeConversion(value,type));
                }
            }
        }

        try{
            checkTitle(title,persistenceURL);
        }catch(NoTitlePresent ex){
            exchange.setException(ex);
        }

        String citation = "";
        int occurrences = StringUtils.countMatches(authors,',');
        if(occurrences<4) {
            if(!dataverse.getDatasetVersion().getCitation().isEmpty()){
                citation = dataverse.getDatasetVersion().getCitation();
                int citationUpperLimit = citation.indexOf(", UNF:");
                if(citationUpperLimit>0){
                    citation = citation.substring(0,citationUpperLimit);
                }
            }else{
                citation = buildCitation(authors,title,persistenceId,series,pubYear);
            }

        }else{
            citation =  buildCitation(authors,title,persistenceId,series,pubYear);
        }

        List<File> files = dataverse.getDatasetVersion().getFiles();
        Iterator<File> fileIt = files.iterator();
        ArrayList<com.scio.quantum.harvesters.models.dataset.File> modelFiles = new ArrayList();
        while(fileIt.hasNext()){
            File file = fileIt.next();
            String label = file.getLabel();
            String contentType = file.getDataFile().getContentType();
            String id = Integer.toString(file.getDataFile().getId());
            String storageIdentifier = file.getDataFile().getStorageIdentifier();
            String filename = file.getDataFile().getFilename();
            String fileAccessibility = "restricted";
            String fileURL = "";
            RegexCollection rc = new RegexCollection();
            boolean isURL = rc.checkURLFormat(storageIdentifier);
            boolean isOpen = false;
            String downloadLink = "";
            if(isURL == true){
                isOpen = resolveFileAccessibility(storageIdentifier);
                fileURL = storageIdentifier;
            }else{
                downloadLink = "https://dataverse.harvard.edu/api/access/datafile/"+id;
                isOpen = resolveFileAccessibility(downloadLink);
                fileURL = downloadLink;
            }
            if(isOpen == true){
                fileAccessibility = "open";
            }else{
                accessibility = "restricted";
            }

            com.scio.quantum.harvesters.models.dataset.File modelFile = new com.scio.quantum.harvesters.models.dataset.File();
            modelFile.setLabel(label);
            modelFile.setContentType(contentType);
            modelFile.setAccessibility(fileAccessibility);
            modelFile.setDownloadLink(downloadLink);
            modelFile.setURL(fileURL);
            modelFile.setFilename(filename);
            modelFiles.add(modelFile);

        }

        List<DOI> dois = new ArrayList<>();
        List<HDL> hdls = new ArrayList<>();
        if(protocol.equalsIgnoreCase("doi")){
            DOI doi = new DOI();
            doi.setActive("true");
            doi.setLink(authority + "/" + identifier);
            dois.add(doi);
        }else{
            HDL hdl = new HDL();
            hdl.setActive("true");
            hdl.setLink(authority + "/" + identifier);
            hdls.add(hdl);
        }

        ProviderLink pl = new ProviderLink();
        pl.setActive("true");
        pl.setLink(persistenceURL);

        List<ProviderLink> pls = new ArrayList<>();
        pls.add(pl);


        List<ContentProvider> cps = new ArrayList<>();
        ContentProvider cp = new ContentProvider();
        cp.setContentProviderName(exchange.getProperty("contentProviderName",String.class));
        cp.setContentProviderID(exchange.getProperty("contentProviderID",String.class));
        cp.setOwnSpace(exchange.getProperty("ownSpace",String.class));
        cp.setHDL(hdls);
        cp.setProviderLink(pls);
        cps.add(cp);

        DatasetModel dm = new DatasetModel();
        DatasetMetadata dmm = new DatasetMetadata();
        dmm.setDOI(dois);
        dmm.setContentProvider(cps);
        dmm.setAccessibility(accessibility);
        dmm.setAuthors(authors);
        dmm.setCategory("dataset");
        dmm.setCitation(citation);
        dmm.setIssuedYear(issuedYear);
        dmm.setLicense(license);
        dmm.setTermsOfUse(termsOfUse);
        dmm.setPubYear(pubYear);
        if(series.isEmpty()){
            dmm.setSeries("Harvard Dataverse");
        }else{
            dmm.setSeries(series);
        }
        dmm.setSummary(summary);
        dmm.setTitle(title);
        dmm.setLanguage("EN");
        dmm.setGeographic(geoTerms);
        dmm.setFiles(modelFiles);
        dmm.setTopics(topics);
        dm.setDatasetMetadata(dmm);
        exchange.getOut().setBody(dm);
        exchange.setProperty("internalId",persistenceURL);

    }

    private void checkTitle(String title,String providerLink) throws NoTitlePresent {
        if((title.isEmpty())||title.length()<3){
            throw new NoTitlePresent("Error in Metadata: "+providerLink);
        }
    }


    private void checkIfHasBeenProcessed(String index,String id) throws AlreadyProcessedException, IOException {
        java.io.File f = new java.io.File(index);
        if ( f.exists() && !f.isDirectory() ) {
            FileReader fileReader = new FileReader(index);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = "";
            while((line = bufferedReader.readLine()) != null) {
                if(line.trim().equalsIgnoreCase(id.trim())){
                    throw new AlreadyProcessedException("ID Document "+id);
                }
            }
            bufferedReader.close();
        }
    }

    private ArrayList valueTypeConversion(List<Object> value,String type){
        ArrayList<String> actualValueList = new ArrayList<>();
        Iterator valueIt = value.iterator();
        while(valueIt.hasNext()){
            Object valueType = valueIt.next();
            if(valueType instanceof LinkedTreeMap){
                LinkedTreeMap<String,LinkedTreeMap> valueMap = ((LinkedTreeMap) valueType);
                if(type.equalsIgnoreCase("author")){
                    LinkedTreeMap<String,String> authorMap =valueMap.get("authorName");
                    String result = authorMap.get("value");
                    actualValueList.add(result);
                }else if(type.equalsIgnoreCase("keyword")){
                    LinkedTreeMap<String,String> keywordMap =valueMap.get("keywordValue");
                    String result = keywordMap.get("value");
                    actualValueList.add(result);
                }else if(type.equalsIgnoreCase("topicClassification")){
                    LinkedTreeMap<String,String> topicClassValueMap =valueMap.get("topicClassValue");
                    String result = topicClassValueMap.get("value");
                    actualValueList.add(result);
                }
            }
        }
        actualValueList.removeIf(Objects::isNull);
        return actualValueList;
    }

    private boolean resolveFileAccessibility(String url){
        URL connection = null;
        try {
            connection = new URL(url);
            HttpURLConnection http = (HttpURLConnection) connection.openConnection();
            String responseMessage = http.getResponseMessage();
            if(responseMessage.equalsIgnoreCase("Forbidden")){
                return false;
            }else{
                return true;
            }
        } catch (MalformedURLException e) {
            //e.printStackTrace();
            return false;
        } catch (ConnectException ce){
            return false;
        } catch (IOException e) {
            //e.printStackTrace();
            return false;
        }
    }

    private String buildCitation(String authors, String title, String id, String series, String pubYear){
        String citation = "";
        int occurrences = StringUtils.countMatches(authors,',');
        if(occurrences<4){
            citation += authors+", ";
        }else{
            authors = authors.substring(0,authors.indexOf(","))+" et al.";
            citation += authors;
        }
        citation +=", \'"+title+"\'";
        citation +=", \'"+id+"\'";
        citation +=", \'"+series+"\'";
        citation +=", \'"+pubYear+"\'";

        return citation;
    }
}