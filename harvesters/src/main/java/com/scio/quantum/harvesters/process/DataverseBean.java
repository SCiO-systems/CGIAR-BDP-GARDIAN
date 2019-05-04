package com.scio.quantum.harvesters.process;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.scio.quantum.harvesters.models.external.dataverse.result.DataverseSearchResult;
import com.scio.quantum.harvesters.models.external.dataverse.result.Item;
import org.apache.camel.Exchange;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataverseBean {


    public ArrayList<String> buildCrudeHarvestList(Exchange exchange) throws IOException {
        String rootURL = exchange.getProperty("ROOT_URL",String.class);
        Document document = Jsoup.connect(rootURL+"1").get();
        ArrayList<String> harvestList = new ArrayList<>();
        double pages = calculatePages(document);
        for(int i=0;i<pages;i++){
            int page = i+1;
            Document documentPage = Jsoup.connect(rootURL+page).get();
            harvestList.addAll(extractPersistentIds(documentPage));
        }
        return harvestList;
    }

    private ArrayList<String> extractPersistentIds(Document document){
        Elements items = document.select(".card-title-icon-block");
        Iterator<Element> itItems = items.iterator();
        ArrayList<String> idList = new ArrayList<>();
        while(itItems.hasNext()){
            Element element = itItems.next();
            Elements link = element.select("a");
            String href = link.attr("href");
            idList.add(href.substring(href.indexOf("=")+1));
        }
        return idList;
    }

    private double calculatePages(Document document){
        Elements results = document.select("#resultsCountPaginatorBlock .results-count");
        Pattern p = Pattern.compile("\\s+([0-9]+)");
        Matcher m = p.matcher(results.text());
        int k = 0;
        double pages = 0;
        while(m.find()) {
            if(k == 1){
                double items = Double.parseDouble(m.group());
                pages = items/10.0;
            }
            k++;
        }
        return pages;
    }

    public ArrayList<String> buildSimpleHarvestList(Exchange exchange){
        DataverseSearchResult dsr = exchange.getIn().getBody(DataverseSearchResult.class);
        List<Item> items = dsr.getData().getItems();
        Iterator<Item> itItems = items.iterator();
        ArrayList<String> harvestList = new ArrayList<>();
        while(itItems.hasNext()){
            Item item = itItems.next();
            harvestList.add(item.getGlobalId());
        }
        return harvestList;
    }

    public ArrayList<String> buildHarvestList(Exchange exchange) throws IOException {
        String rootURL = exchange.getProperty("DATAVERSE_ID",String.class);
        ArrayList<String> harvestList = collectAllDatasets(rootURL);
        return harvestList;

    }
    private ArrayList<String> collectAllDatasets(String dataverseID) throws IOException {
        ArrayList<String> allIDs = new ArrayList<>();
        final String userAgent = "Mozilla/5.0";
        String url = "https://dataverse.harvard.edu/api/dataverses/"+dataverseID+"/contents";
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", userAgent);
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        Gson gson = new Gson();
        LinkedTreeMap<String,ArrayList> dataverseContent = gson.fromJson(response.toString(),LinkedTreeMap.class);
        ArrayList data = dataverseContent.get("data");
        Iterator<LinkedTreeMap> itData = data.iterator();
        while(itData.hasNext()){
            LinkedTreeMap<String,Object> linkedTreeMap = itData.next();
            String type = linkedTreeMap.get("type").toString();
            if(type.equalsIgnoreCase("dataverse")){
                Double temp = (Double)linkedTreeMap.get("id");
                String id =  Integer.toString(temp.intValue());
                allIDs.addAll(collectAllDatasets(id));
            }else{
                String protocol = linkedTreeMap.get("protocol").toString();
                String identifier = linkedTreeMap.get("identifier").toString();
                String authority = linkedTreeMap.get("authority").toString();
                String id = protocol + ":" + authority + "/" + identifier;
                allIDs.add(id);
            }
        }
        return allIDs;
    }

}
