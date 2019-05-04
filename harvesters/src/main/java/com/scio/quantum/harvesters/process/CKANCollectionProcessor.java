package com.scio.quantum.harvesters.process;

import com.scio.quantum.harvesters.exceptions.AlreadyProcessedException;
import com.scio.quantum.harvesters.exceptions.NoDatePresent;
import com.scio.quantum.harvesters.exceptions.NoTitlePresent;
import com.scio.quantum.harvesters.models.dataset.*;
import com.scio.quantum.harvesters.models.external.ckan.resources.iita.Resource;
import com.scio.quantum.harvesters.models.external.ckan.resources.iita.Tag;
import com.scio.quantum.harvesters.utilities.RegexCollection;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.util.*;


public class CKANCollectionProcessor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        Object obscureDataset = exchange.getIn().getBody();
        if(obscureDataset instanceof com.scio.quantum.harvesters.models.external.ckan.resources.iita.Result_){
            com.scio.quantum.harvesters.models.external.ckan.resources.iita.Result_ dataset =
                    (com.scio.quantum.harvesters.models.external.ckan.resources.iita.Result_)obscureDataset;
            System.out.println("BF PROC ID:"+dataset.getIdentifier());
            DatasetModel dm = processDataset(dataset,exchange);
            exchange.getOut().setBody(dm);
            String persistenceURL = dm.getDatasetMetadata().getContentProvider().get(0).getProviderLink().get(0).getLink();
            exchange.setProperty("internalId",persistenceURL.trim());
        }else if (obscureDataset instanceof com.scio.quantum.harvesters.models.external.ckan.resources.ilri.Result){
            com.scio.quantum.harvesters.models.external.ckan.resources.ilri.Result dataset =
                    (com.scio.quantum.harvesters.models.external.ckan.resources.ilri.Result)obscureDataset;
            DatasetModel dm = processDataset(dataset,exchange);
            exchange.getOut().setBody(dm);
            String persistenceURL = dm.getDatasetMetadata().getContentProvider().get(0).getProviderLink().get(0).getLink();
            exchange.setProperty("internalId",persistenceURL.trim());

        }


    }
    private DatasetModel processDataset(com.scio.quantum.harvesters.models.external.ckan.resources.ilri.Result dataset, Exchange exchange) throws IOException, NoDatePresent {
        String contentProviderName = exchange.getProperty("contentProviderName",String.class);
        String contentProviderID = exchange.getProperty("contentProviderID",String.class);
        String index = exchange.getProperty("INDEX",String.class);
        String persistenceURL = dataset.getUrl();
        String series = "ILRI Dataportal";
        try{
            checkIfHasBeenProcessed(index,persistenceURL);
        }catch(AlreadyProcessedException ex) {
            exchange.setException(ex);
        }
        String pubYear = dataset.getMetadataCreated();
        pubYear = pubYear.substring(0, 4);
        if(!StringUtils.isNumeric(pubYear)){
            pubYear = pubYear.substring(pubYear.length() - 4);
            if(!StringUtils.isNumeric(pubYear)){
                throw new NoDatePresent("Error in Metadata: "+persistenceURL);
            }
        }

        String title = dataset.getTitle();
        try{
            checkTitle(title,persistenceURL);
        }catch(NoTitlePresent ex){
            exchange.setException(ex);
        }

        String issuedYear = "";

        String summary = dataset.getILRIPrjabstract();
        String license = dataset.getLicenseId();
        String termsOfUse = dataset.getLicenseUrl();
        String accessibility = "open";

        List<String> geographic = dataset.getILRIActycountries();
        ArrayList<String> geoTerms = new ArrayList<>();
        geoTerms.addAll(geographic);

        ArrayList<String> topics = new ArrayList<>();
        List<com.scio.quantum.harvesters.models.external.ckan.resources.ilri.Tag> tagList = dataset.getTags();
        if(tagList != null){
            Iterator<com.scio.quantum.harvesters.models.external.ckan.resources.ilri.Tag> tagIterator = tagList.iterator();
            while(tagIterator.hasNext()){
                com.scio.quantum.harvesters.models.external.ckan.resources.ilri.Tag tag = tagIterator.next();
                String topic = tag.getName();
                topics.add(topic);
            }
        }


        String authors = dataset.getILRIActystaff();
        ArrayList<String> authorsList = sanitizeAuthors(authors);
        String citation = "";

        if (authorsList.size() > 3) {
            citation = authorsList.get(0)+" etc al.,'" + title +"', " +series+", "+pubYear;
        }else{
            citation = String.join(", ", authorsList)+",'" + title +"', " +series+", "+pubYear;
        }

        List<com.scio.quantum.harvesters.models.external.ckan.resources.ilri.Resource> resourceList = dataset.getResources();
        Iterator<com.scio.quantum.harvesters.models.external.ckan.resources.ilri.Resource> resourceIterator = resourceList.iterator();
        ArrayList<File> files = new ArrayList<>();
        while(resourceIterator.hasNext()){
            com.scio.quantum.harvesters.models.external.ckan.resources.ilri.Resource resource = resourceIterator.next();
            String fileId = resource.getId();
            String fileURL = resource.getUrl();
            String name = dataset.getName();
            String label = resource.getName();
            String downloadLink ="http://data.ilri.org/portal/dataset/"+ name +"/resource/"+fileId+"/request";
            String fileAccessibility = "restricted";
            String contentType = "";
            File file = new File();
            file.setLabel(label);
            file.setContentType(contentType);
            file.setAccessibility(fileAccessibility);
            file.setDownloadLink(downloadLink);
            file.setURL(fileURL);
            file.setFilename(label);
            files.add(file);
        }

        List<DOI> dois = new ArrayList<>();
        List<HDL> hdls = new ArrayList<>();
        ProviderLink pl = new ProviderLink();
        pl.setActive("true");
        pl.setLink(persistenceURL);
        List<ProviderLink> pls = new ArrayList<>();
        pls.add(pl);
        List<ContentProvider> cps = new ArrayList<>();
        ContentProvider cp = new ContentProvider();
        cp.setContentProviderName(contentProviderName);
        cp.setContentProviderID(contentProviderID);
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
        dmm.setSeries(series);
        dmm.setSummary(summary);
        dmm.setTitle(title);
        dmm.setLanguage("EN");
        dmm.setGeographic(geoTerms);
        dmm.setFiles(files);
        dmm.setTopics(topics);
        dm.setDatasetMetadata(dmm);
        return  dm;

    }

    private DatasetModel processDataset(com.scio.quantum.harvesters.models.external.ckan.resources.iita.Result_ dataset, Exchange exchange) throws IOException, NoDatePresent {
        String index = exchange.getProperty("INDEX",String.class);
        String persistenceURL = dataset.getIdentifier();
        String identifierType = dataset.getIdentifierType();
        String contentProviderName = exchange.getProperty("contentProviderName",String.class);
        String contentProviderID = exchange.getProperty("contentProviderID",String.class);
        String series = "";
        if(contentProviderID.equalsIgnoreCase("CGIAR-IITA-DATASETS")){
            series = "IITA Dataportal";
        }
        try{
            checkIfHasBeenProcessed(index,persistenceURL);
        }catch(AlreadyProcessedException ex) {
            exchange.setException(ex);
        }
        String title = dataset.getTitle();
        try{
            checkTitle(title,persistenceURL);
        }catch(NoTitlePresent ex){
            exchange.setException(ex);
        }
        String pubYear = dataset.getMetadataCreated();
        pubYear = pubYear.substring(0, 4);
        if(!StringUtils.isNumeric(pubYear)){
            pubYear = pubYear.substring(pubYear.length() - 4);
            if(!StringUtils.isNumeric(pubYear)){
                throw new NoDatePresent("Error in Metadata: "+persistenceURL);
            }
        }
        String issuedYear = dataset.getCreationDate();
        issuedYear = issuedYear.substring(0, 4);
        if(!StringUtils.isNumeric(issuedYear)){
            issuedYear = "";
        }
        String language = dataset.getLanguage();
        String license = "";
        if(dataset.getLicenseTitle() != null){
            license = (String) dataset.getLicenseTitle();
        }
        String termsOfUse = dataset.getRestriction();
        String summary = dataset.getNotes();
        String accessibility = "open";

        ArrayList<String> geoTerms =  new ArrayList<>();
        String country = dataset.getCoverageCountry();
        if(!country.equalsIgnoreCase("Not Applicable")&&!country.isEmpty()){
            geoTerms.add(country);
        }
        ArrayList<String> topics = new ArrayList<>();
        List<Tag> tagList = dataset.getTags();
        Iterator<Tag> tagIt = tagList.iterator();
        while(tagIt.hasNext()){
            Tag tg = tagIt.next();
            topics.add(tg.getName());
        }

        String authors = dataset.getCreator();
        String citation = "";
        ArrayList<String> authorsList = sanitizeAuthors(authors);

        if (authorsList.size() > 3) {
            citation = authorsList.get(0)+" etc al.,'" + title +"', " +series+", "+pubYear;
        }else{
            citation = String.join(", ", authorsList)+",'" + title +"', " +series+", "+pubYear;
        }

        List<Resource> resourceList = dataset.getResources();
        Iterator<Resource> resourceIterator = resourceList.iterator();
        ArrayList<File> files = new ArrayList();
        while(resourceIterator.hasNext()){
            Resource resource = resourceIterator.next();
            String label = resource.getName();
            String filename = label;
            String contentType = resource.getFormat();
            String fileURL = resource.getUrl();
            String downloadLink = "";
            String fileAccessibility = "restricted";
            boolean isOpen = false;

            if(fileURL.contains("dataverse")){
                Map<String, List<String>> urlParameters = getURLParameters(new URL(fileURL));
                List<String> fileIdList = urlParameters.get("fileId");
                if(fileIdList.size()>0){
                    String fileId = fileIdList.get(0);
                    downloadLink = "https://dataverse.harvard.edu/api/access/datafile/"+fileId;
                    RegexCollection rc = new RegexCollection();
                    boolean isURL = rc.checkURLFormat(downloadLink);
                    if(isURL == true) {
                        isOpen = resolveFileAccessibility(downloadLink);
                    }
                }
            }else{
                String fileId = resource.getId();
                String packageName = dataset.getName();
                downloadLink = fileURL;
                RegexCollection rc = new RegexCollection();
                boolean isURL = rc.checkURLFormat(downloadLink);
                if(isURL == true) {
                    isOpen = resolveFileAccessibility(downloadLink);
                }
                fileURL = "http://data.iita.org/dataset/"+packageName+"/resource/"+fileId;
            }
            if(isOpen == true){
                fileAccessibility = "open";
            }else{
                accessibility = "restricted";
            }
            File file = new File();
            file.setLabel(label);
            file.setContentType(contentType);
            file.setAccessibility(fileAccessibility);
            file.setDownloadLink(downloadLink);
            file.setURL(fileURL);
            file.setFilename(filename);
            files.add(file);
        }
        List<DOI> dois = new ArrayList<>();
        List<HDL> hdls = new ArrayList<>();
        if(identifierType.equalsIgnoreCase("DOI")){
            DOI doi = new DOI();
            doi.setActive("true");
            doi.setLink(persistenceURL);
            dois.add(doi);
        }else if(identifierType.equalsIgnoreCase("HDL")){
            HDL hdl = new HDL();
            hdl.setActive("true");
            hdl.setLink(persistenceURL);
            hdls.add(hdl);
        }

        ProviderLink pl = new ProviderLink();
        pl.setActive("true");
        pl.setLink(persistenceURL);
        List<ProviderLink> pls = new ArrayList<>();
        pls.add(pl);
        List<ContentProvider> cps = new ArrayList<>();
        ContentProvider cp = new ContentProvider();
        cp.setContentProviderName(contentProviderName);
        cp.setContentProviderID(contentProviderID);
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
        dmm.setSeries(series);
        dmm.setSummary(summary);
        dmm.setTitle(title);
        dmm.setLanguage(language);
        dmm.setGeographic(geoTerms);
        dmm.setFiles(files);
        dmm.setTopics(topics);
        dm.setDatasetMetadata(dmm);

        return  dm;
    }

    private ArrayList<String> sanitizeAuthors(String authors){
        ArrayList<String> authorsList = new ArrayList<>();
        if(authors.contains(";")){
            authors.replaceAll(","," ");
            StringTokenizer st = new StringTokenizer(authors,";");
            while(st.hasMoreTokens()){
                String author = st.nextToken();
                authorsList.add(author.trim());
            }
        }else{
            StringTokenizer st = new StringTokenizer(authors,",");
            while(st.hasMoreTokens()){
                String author = st.nextToken();
                authorsList.add(author.trim());
            }
        }
        return authorsList;
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

    private  Map<String, List<String>> getURLParameters(URL url) throws UnsupportedEncodingException {
        final Map<String, List<String>> queryPairs = new LinkedHashMap<>();
        final String[] pairs = url.getQuery().split("&");
        for (String pair : pairs) {
            final int idx = pair.indexOf("=");
            final String key = idx > 0 ? URLDecoder.decode(pair.substring(0, idx), "UTF-8") : pair;
            if (!queryPairs.containsKey(key)) {
                queryPairs.put(key, new LinkedList<>());
            }
            final String value = idx > 0 && pair.length() > idx + 1 ? URLDecoder.decode(pair.substring(idx + 1), "UTF-8") : null;
            queryPairs.get(key).add(value);
        }
        return queryPairs;
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
                if(line.equalsIgnoreCase(id.trim())){
                    throw new AlreadyProcessedException("ID Document "+id);
                }
            }
            bufferedReader.close();
        }
    }
}
