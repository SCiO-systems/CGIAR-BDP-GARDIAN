package com.scio.quantum.harvesters.process;

import com.scio.quantum.harvesters.exceptions.AlreadyProcessedException;
import com.scio.quantum.harvesters.models.publication.*;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

//TODO: Deduplicate Functions

public class IRRICollectionProcessor implements Processor {

    final int yearField = 0;
    final int linkField = 1;
    final int articleField = 2;
    final int docKeyField = 3;
    final int authorsField = 4;
    final int titleField = 5;
    final int sourceField = 6;
    final int openAccessField = 7;
    final int pubTypeField = 8;
    final int doiField = 9;
    final int publisherField = 10;

    @Override
    public void process(Exchange exchange) throws Exception {
        List<Object> row = exchange.getIn().getBody(List.class);

        String pubDate = ((String)row.get(this.yearField)).trim();
        String docKey = ((String)row.get(this.docKeyField)).trim();
        String authors = ((String)row.get(this.authorsField)).trim();
        String title = ((String)row.get(this.titleField)).trim();
        String source = ((String)row.get(this.sourceField)).trim();
        String openAccess = ((String)row.get(this.openAccessField)).trim();
        String pubType = ((String)row.get(this.pubTypeField)).trim();

        String providerLink = "https://docs.google.com/a/irri.org/file/d/" + docKey;
        String index = exchange.getProperty("INDEX",String.class);

        String doi = "";
        if(row.size() > 9){
            doi = ((String)row.get(this.doiField)).trim();
        }
        try{
            checkIfHasBeenProcessed(index,docKey);
        }catch(AlreadyProcessedException ex) {
            exchange.setException(ex);
        }
        boolean isOpen = false;
        String docLink = "";
        if(openAccess.equalsIgnoreCase("Y")){
            isOpen = true;
            docLink = "https://docs.google.com/a/irri.org/file/d/" + docKey;
        }
        title = refineTitle(title);
        String citation = buildCitation(authors,title,source);
        String category = resolvePublicationCategory(pubType);
        String issueDate = "";
        if(pubDate.length() != 4){
            pubDate = "";
        }else{
            issueDate = pubDate;
        }
        ProviderLink pl = new ProviderLink(providerLink,true);
        ArrayList<ProviderLink> providerLinks = new ArrayList<>();
        providerLinks.add(pl);
        ArrayList<HDL> hdls = new ArrayList<>();
        ContentProvider cp = new ContentProvider("IRRI-PUBS","International Rice Research Institute (IRRI)",providerLinks,hdls);
        ArrayList<ContentProvider> cps = new ArrayList<>();
        cps.add(cp);

        DOI doiInstance = new DOI(doi,true);
        ArrayList<DOI> dois = new ArrayList<>();
        dois.add(doiInstance);

        PubMetadata pmd = new PubMetadata();
        pmd.setTitle(title);
        pmd.setContentProvider(cps);
        pmd.setAuthors(authors);
        pmd.setDoi(dois);
        pmd.setSummary("");
        pmd.setCategory(category);
        pmd.setDocLink(docLink);
        pmd.setFeaturedImgURL("");
        pmd.setPubYear(pubDate);
        pmd.setIssuedYear(issueDate);
        pmd.setIsOpenAccess(isOpen);
        pmd.setLanguage("EN");
        pmd.setSeries(source);
        pmd.setVolume("");
        pmd.setNum("");
        pmd.setPages("");
        pmd.setCitation(citation);

        ArrayList<String> empty = new ArrayList<>();
        pmd.setTopics(empty);
        pmd.setGeographic(empty);

        PublicationModel pm = new PublicationModel();
        pm.setPubMetadata(pmd);

        exchange.getOut().setBody(pm);
        exchange.getOut().setHeader("doi",doi);
        exchange.setProperty("internalId",docKey);
    }

    private String buildCitation(String authors,String title,String source){
        String citation = "";
        StringTokenizer st = new StringTokenizer(authors, ",");
        String firstAuthor = st.nextToken();
        if(st.countTokens()> 3){
            citation = firstAuthor + " et al.";
        }else{
            citation = firstAuthor;
        }
        citation = (citation+", '"+title+"', "+source).trim();
        return citation;
    }

    private String refineTitle(String title){
        title = title.replaceAll("’", "'");
        title = title.replaceAll("“", "'");
        title = title.replaceAll("”", "'");
        title = title.trim();
        return title;
    }

    private String resolvePublicationCategory(String pubType){
        String publicationCategory = "Other";
        if (pubType.equalsIgnoreCase("journal")) {
            publicationCategory = "Scientific Publication";
        } else if (pubType.equalsIgnoreCase("book")) {
            publicationCategory = "Book / Monograph";
        } else if (pubType.equalsIgnoreCase("working paper")) {
            publicationCategory = "Working Paper";
        } else if (pubType.equalsIgnoreCase("report")) {
            publicationCategory = "Report / Factsheet";
        } else if (pubType.equalsIgnoreCase("proceedings")) {
            publicationCategory = "Scientific Publication";
        } else {
            publicationCategory = "Other";
        }
        return publicationCategory;
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


}
