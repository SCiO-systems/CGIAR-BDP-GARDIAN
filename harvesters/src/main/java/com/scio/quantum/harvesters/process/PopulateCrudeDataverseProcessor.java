package com.scio.quantum.harvesters.process;

import com.scio.quantum.harvesters.models.external.Center;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.util.ArrayList;

public class PopulateCrudeDataverseProcessor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        Center dc1 = new Center(
                "https://data.cimmyt.org/dataverse/root?q=&types=datasets&sort=dateSort&order=asc&page=",
                "true",
                "CGIAR-CIMMYT-DATASETS",
                "International Maize and Wheat Improvement Center (CIMMYT)",
                "CIMMYT",
                "https://data.cimmyt.org/api/datasets/export?exporter=dataverse_json&persistentId=");

        Center dc2 = new Center(
                "https://dataverse.harvard.edu/dataverse/cifor_harvested?q=&types=dataverses%3Adatasets&sort=dateSort&order=desc&page=",
                "true",
                "CGIAR-CIFOR-DATASETS",
                "Center for International Forestry Research (CIFOR)",
                "CIFOR",
                "https://dataverse.harvard.edu/api/datasets/export?exporter=dataverse_json&persistentId=");

        Center dc3 = new Center(
                "https://data.cipotato.org/dataverse/dvn;jsessionid=a409f10a432f55354937df420ccc?q=&types=datasets&sort=dateSort&order=desc&page=",
                "true",
                "CGIAR-CIP-DATASETS",
                "International Potato Center (CIP)",
                "CIP",
                "https://data.cipotato.org/api/datasets/export?exporter=dataverse_json&persistentId=");

        Center dc4 = new Center(
                "http://dataverse.icrisat.org/dataverse/icrisat;jsessionid=8af1dffab37dd4d8ed89ce97014b?q=&types=datasets&sort=dateSort&order=desc&page=",
                "true",
                "CGIAR-ICRISAT-DATASETS",
                "International Crops Research Institute for the Semi-Arid Tropics (ICRISAT)",
                "ICRISAT",
                "http://dataverse.icrisat.org/api/datasets/export?exporter=dataverse_json&persistentId=");



        ArrayList<Center> dcList = new ArrayList();
        //dcList.add(dc1);
        //dcList.add(dc2);
        //dcList.add(dc3);
        dcList.add(dc4);
        exchange.getOut().setBody(dcList);
    }
}
