package com.scio.quantum.harvesters.process;

import com.scio.quantum.harvesters.exceptions.AlreadyProcessedException;
import com.scio.quantum.harvesters.exceptions.NoTitlePresent;
import com.scio.quantum.harvesters.models.publication.*;
import com.scio.quantum.harvesters.utilities.RegexCollection;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;

public class IFPRICollectionProcessor implements Processor {

    //TODO: ADD Citation Builder

    @Override
    public void process(Exchange exchange) throws Exception {

        String resource = exchange.getIn().getBody(String.class);
        String set = exchange.getProperty("SET",String.class);
        String index = exchange.getProperty("INDEX",String.class);

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(new InputSource(new StringReader(resource)));

        doc.getDocumentElement().normalize();

        String doi = "";
        NodeList identifiers = doc.getElementsByTagName("dc:identifier");
        if(identifiers.getLength()>0){
            doi = resourceDoi(identifiers);
        }

        String featuredImage = "";
        String internalID = identifiers.item(identifiers.getLength()-1).getTextContent();

        if(set.equalsIgnoreCase("p15738coll2")){
            String imageID = identifiers.item(identifiers.getLength()-2).getTextContent();
            featuredImage = "http://www.ifpri.org/sites/default/files/styles/pub_thumb/public/p15738coll2-"+imageID+".jpg";
        }

        try{
            checkIfHasBeenProcessed(index,internalID);
        }catch(AlreadyProcessedException ex) {
            exchange.setException(ex);
        }

        ArrayList<ProviderLink> providerLinks = new ArrayList<>();
        identifiers = doc.getElementsByTagName("dc:identifier");
        if(identifiers.getLength()>0){
            ArrayList<String> links = resourceProviderLinks(identifiers);
            Iterator<String> itLinks = links.iterator();
            while(itLinks.hasNext()){
                String link = itLinks.next();
                ProviderLink pl = new ProviderLink(link,true);
                providerLinks.add(pl);
            }
        }

        String title = "";
        NodeList titles = doc.getElementsByTagName("dc:title");
        if(titles.getLength()>0){
            title = resourceTitle(titles);
        }

        try{
            checkTitle(title,providerLinks.get(0).getLink());
        }catch(NoTitlePresent ex){
            exchange.setException(ex);
        }


        boolean isOpen = false;
        NodeList rights = doc.getElementsByTagName("dc:rights");

        if(rights.getLength()>0){
            isOpen = resourceRights(rights);
        }

        String language = "";
        NodeList languages = doc.getElementsByTagName("dc:language");

        if(languages.getLength()>0){
            language = resourceLanguage(languages);
        }

        String category = "";
        NodeList categories = doc.getElementsByTagName("dc:type");

        if(categories.getLength()>0){
            category = resourceCategory(categories);
            category = transformCategory(category);

        }

        String summary = "";
        NodeList descriptions = doc.getElementsByTagName("dc:description");

        if(descriptions.getLength()>0){
            summary = resourceSummary(descriptions);
        }

        String series = "";
        String volume = "";
        String num = "";
        String pages = "";

        if(set.equalsIgnoreCase("p15738coll5")){
            NodeList source = doc.getElementsByTagName("dc:source");
            series = resourceSeries(source);
            volume = resourceVolume(source);
            num = resourceNum(source);
            pages = resourcePages(source);
        }

        String pubDate = "";
        String issueDate = "";

        NodeList dates = doc.getElementsByTagName("dc:date");
        pubDate = resourceDate(dates);
        issueDate = pubDate;

        ArrayList<String> authors;

        NodeList creators = doc.getElementsByTagName("dc:creator");
        authors = resourceAuthors(creators);

        ArrayList<String> topics = new ArrayList<>();
        ArrayList<String> geographic = new ArrayList<>();
        NodeList subject = doc.getElementsByTagName("dc:subject");
        if(subject.getLength() == 1){
            topics = resourceSubjects(subject.item(0));
        }else if(subject.getLength() == 2){
            geographic = resourceSubjects(subject.item(0));
            topics = resourceSubjects(subject.item(1));

        }
        ArrayList<HDL> hdls = new ArrayList<>();
        ContentProvider cp = new ContentProvider("IFPRI-PUBS","International Food Policy Research Institute (IFPRI)",providerLinks,hdls);
        ArrayList<ContentProvider> cps = new ArrayList<>();
        cps.add(cp);

        DOI doiInstance = new DOI(doi,true);
        ArrayList<DOI> dois = new ArrayList<>();
        dois.add(doiInstance);

        PubMetadata pmd = new PubMetadata();

        pmd.setTitle(title);
        pmd.setContentProvider(cps);
        String unifiedAuthors = String.join(",", authors);
        pmd.setAuthors(unifiedAuthors);
        pmd.setDoi(dois);
        pmd.setSummary(summary);
        pmd.setCategory(category);
        pmd.setDocLink("");
        pmd.setFeaturedImgURL(featuredImage);
        pmd.setPubYear(pubDate);
        pmd.setIssuedYear(issueDate);
        pmd.setIsOpenAccess(isOpen);
        pmd.setLanguage(language);
        pmd.setSeries(series);
        pmd.setVolume(volume);
        pmd.setNum(num);
        pmd.setPages(pages);

        pmd.setTopics(topics);
        pmd.setGeographic(geographic);

        PublicationModel pm = new PublicationModel();
        pm.setPubMetadata(pmd);

        exchange.getOut().setBody(pm);
        exchange.getOut().setHeader("doi",doi);
        exchange.setProperty("internalId",internalID);


    }

    private void checkIfHasBeenProcessed(String index,String id) throws AlreadyProcessedException, IOException {
        File f = new File(index);
        if ( f.exists() && !f.isDirectory() ) {
            FileReader fileReader = new FileReader(index);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = "";
            while((line = bufferedReader.readLine()) != null) {
                if(line.equalsIgnoreCase(id)){
                    throw new AlreadyProcessedException("ID Document "+id);
                }
            }
            bufferedReader.close();
        }
    }

    private void checkTitle(String title,String providerLink) throws NoTitlePresent {
        if((title.isEmpty())||title.length()<3){
            throw new NoTitlePresent("Error in Metadata: "+providerLink);
        }
    }

    private String resourceTitle(NodeList titles){
        String title = "";
        for(int i=0;i<titles.getLength();i++){
            title +=" "+titles.item(i).getTextContent();
        }
        title = title.replaceAll("’", "'");
        title = title.replaceAll("“", "'");
        title = title.replaceAll("”", "'");
        title = title.trim();
        return title;
    }

    private String resourceDoi(NodeList identifiers){
        String doi = "";
        for(int i=0;i<identifiers.getLength();i++){
            String identifier = identifiers.item(i).getTextContent().trim();
            RegexCollection re = new RegexCollection();
            String simpleDoi = re.simpleDOI(identifier);
            if(!simpleDoi.isEmpty()){
                doi = simpleDoi;
                break;
            }
        }
        return doi;
    }

    private ArrayList<String> resourceProviderLinks(NodeList identifiers){
        ArrayList<String> providerLinks = new ArrayList<String>();
        for(int i=0;i<identifiers.getLength();i++){
            String identifier = identifiers.item(i).getTextContent().trim();
            if(identifier.contains("p15738coll5")||identifier.contains("p15738coll2")||identifier.contains("http://www.ifpri.org/")){
                providerLinks.add(identifier);
            }
        }
        return providerLinks;
    }

    private boolean resourceRights(NodeList rights){
        boolean isOpen = false;
        for(int i=0;i<rights.getLength();i++){
            String right = rights.item(i).getTextContent().trim();
            if(right.equalsIgnoreCase("restricted")){
                isOpen = false;
            }else if(right.equalsIgnoreCase("open")){
                isOpen = true;
            }
        }
        return isOpen;
    }

    private String resourceLanguage(NodeList languages){
        String language = "";
        for(int i=0;i<languages.getLength();i++){
            language = languages.item(i).getTextContent().trim();
            break;
        }
        return language;
    }

    private String resourceCategory(NodeList categories){
        String category = "";
        for(int i=0;i<categories.getLength();i++){
            category = categories.item(i).getTextContent().trim();
            break;
        }
        return category;
    }

    private String transformCategory(String category){

        if (category.equalsIgnoreCase("Discussion paper")
                || category.equalsIgnoreCase("discussion paper")
                || category.equalsIgnoreCase("brief")
                || category.equalsIgnoreCase("project paper")
                || category.equalsIgnoreCase("conference Paper")
                || category.equalsIgnoreCase("essay")
                || category.equalsIgnoreCase("conference proceeding")
                || category.equalsIgnoreCase("data paper")  ) {

            category = "Scientific Publication";

        } else if (category.equalsIgnoreCase("book")
                || category.equalsIgnoreCase("book chapter") ) {

            category = "Book / Monograph";

        } else if (category.equalsIgnoreCase("report")
                || category.equalsIgnoreCase("factsheet")
                || category.equalsIgnoreCase("booklet")
                || category.equalsIgnoreCase("training materials")
                || category.equalsIgnoreCase("infographic")) {

            category = "Report / Factsheet";

        } else if (category.equalsIgnoreCase("working paper")) {
            category = "Working Paper";
        } else {
            category = "Other";
        }

        return category;

    }

    private String resourceSummary(NodeList descriptions){
        String summary = "";
        for(int i=0;i<descriptions.getLength();i++){
            String description = descriptions.item(i).getTextContent().trim();
            if(description.length()>10){
                summary = description;
            }
        }
        summary = summary.replaceAll("’", "'");
        summary = summary.replaceAll("“", "'");
        summary = summary.replaceAll("”", "'");
        summary = summary.trim();
        return summary;
    }

    private String resourceSeries(NodeList source){
        String series = "";
        for(int i=0;i<source.getLength();i++){
           series = source.item(i).getTextContent().trim();
           if(series.contains("(")){
               series = series.substring(0,series.indexOf("("));
           }
        }
        return series;
    }

    private String resourceVolume(NodeList source){
        String volume = "";
        for(int i=0;i<source.getLength();i++){
            RegexCollection re = new RegexCollection();
            volume = re.volumeOfJournal(source.item(i).getTextContent().trim());
        }
        return volume;
    }

    private String resourceNum(NodeList source){
        String num = "";
        for(int i=0;i<source.getLength();i++){
            RegexCollection re = new RegexCollection();
            num = re.numOfJournal(source.item(i).getTextContent().trim());
        }
        return num;
    }

    private String resourcePages(NodeList source){
        String pages = "";
        for(int i=0;i<source.getLength();i++){
            String cleanSource = source.item(i).getTextContent().trim();
            int startIndex = cleanSource.indexOf(":");
            pages = cleanSource.substring(startIndex+1).trim();
        }
        return pages;
    }

    private String resourceDate(NodeList dates){
        String date = "";
        for(int i=0;i<dates.getLength();i++){
            date = dates.item(i).getTextContent().trim();
            if(date.length() == 4){
                break;
            }
        }
        return date;
    }

    private ArrayList<String> resourceAuthors(NodeList creators){
        ArrayList<String> authors = new ArrayList<String>();
        for(int i=0;i<creators.getLength();i++){
            String all = creators.item(i).getTextContent().trim();
            StringTokenizer st = new StringTokenizer(all, ";");
            while (st.hasMoreTokens()) {
                String tempAuth = st.nextToken();
                tempAuth = tempAuth.replaceAll(",", "");
                tempAuth = tempAuth.replaceAll("\"", "'");
                authors.add(tempAuth.trim());
            }
        }
        return authors;
    }

    private ArrayList<String> resourceSubjects(Node subjects){
        ArrayList<String> subjectsList = new ArrayList<>();
        String all = subjects.getTextContent();
        all = all.toLowerCase();
        StringTokenizer st = new StringTokenizer(all,";");
        while(st.hasMoreTokens()){
            String subject = st.nextToken();
            subject = subject.replaceAll(",", "");
            subject = subject.replaceAll("\"", "'");
            subjectsList.add(subject);
        }
        return subjectsList;
    }

}
