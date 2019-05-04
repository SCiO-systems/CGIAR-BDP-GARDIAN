package com.scio.quantum.harvesters.process;

import com.scio.quantum.harvesters.models.external.Center;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.util.ArrayList;

public class PopulateDataverseRootURLsProcessor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        Center dc1 = new Center(
                "https://dataverse.harvard.edu/api/dataverses/AfricaRice/contents",
                "true",
                "CGIAR-AfricaRice-DATASETS",
                "Africa Rice Center (AfricaRice)",
                "AfricaRice",
                "https://dataverse.harvard.edu/api/datasets/export?exporter=dataverse_json&persistentId=");
        Center dc2 = new Center(
                "https://dataverse.harvard.edu/api/dataverses/bioversity/contents",
                "true",
                "CGIAR-Bioversity International-DATASETS",
                "Bioversity International (Bioversity International)",
                "bioversity",
                "https://dataverse.harvard.edu/api/datasets/export?exporter=dataverse_json&persistentId=");
        Center dc3 = new Center(
                "https://dataverse.harvard.edu/api/dataverses/CIAT/contents",
                "true",
                "CGIAR-CIAT-DATASETS",
                "International Center for Tropical Agriculture (CIAT)",
                "CIAT",
                "https://dataverse.harvard.edu/api/datasets/export?exporter=dataverse_json&persistentId=");
        Center dc4 = new Center(
                "https://dataverse.harvard.edu/api/dataverses/cifor/contents",
                "true",
                "CGIAR-CIFOR-DATASETS",
                "Center for International Forestry Research (CIFOR)",
                "cifor",
                "https://dataverse.harvard.edu/api/datasets/export?exporter=dataverse_json&persistentId=");
        Center dc5 = new Center(
                "https://dataverse.harvard.edu/api/dataverses/icraf/contents",
                "true",
                "CGIAR-ICRAF-DATASETS",
                "World Agroforestry Centre (ICRAF)",
                "icraf",
                "https://dataverse.harvard.edu/api/datasets/export?exporter=dataverse_json&persistentId=");
        Center dc6 = new Center(
                "https://dataverse.harvard.edu/api/dataverses/icrisat/contents",
                "true",
                "CGIAR-ICRISAT-DATASETS",
                "International Crops Research Institute for the Semi-Arid Tropics (ICRISAT)",
                "icrisat",
                "https://dataverse.harvard.edu/api/datasets/export?exporter=dataverse_json&persistentId=");
        Center dc7 = new Center(
                "https://dataverse.harvard.edu/api/dataverses/ifpri/contents",
                "true",
                "CGIAR-IFPRI-DATASETS",
                "International Food Policy Research (IFPRI)",
                "ifpri",
                "https://dataverse.harvard.edu/api/datasets/export?exporter=dataverse_json&persistentId=");
        Center dc8 = new Center(
                "https://dataverse.harvard.edu/api/dataverses/RiceResearch/contents",
                "true",
                "CGIAR-IRRI-DATASETS",
                "International Rice Research (IRRI)",
                "RiceResearch",
                "https://dataverse.harvard.edu/api/datasets/export?exporter=dataverse_json&persistentId=");
        Center dc9 = new Center(
                "https://dataverse.harvard.edu/api/dataverses/worldfish/contents",
                "true",
                "CGIAR-WorldFish-DATASETS",
                "WorldFish (WorldFish)",
                "worldfish",
                "https://dataverse.harvard.edu/api/datasets/export?exporter=dataverse_json&persistentId=");
        Center dc10 = new Center(
                "https://dataverse.harvard.edu/api/dataverses/edata/contents",
                "true",
                "CGIAR-CIMMYT-DATASETS",
                "International Maize and Wheat Improvement Center (CIMMYT)",
                "edata",
                "https://dataverse.harvard.edu/api/datasets/export?exporter=dataverse_json&persistentId=");

        Center dc11 = new Center(
                "https://dataverse.harvard.edu/api/dataverses/cacprogram/contents",
                "true",
                "CGIAR-ICARDA-DATASETS",
                "International Center for Agricultural Research in the Dry Areas (ICARDA)",
                "cacprogram",
                "https://dataverse.harvard.edu/api/datasets/export?exporter=dataverse_json&persistentId=");


        ArrayList<Center> dcList = new ArrayList();
        /*dcList.add(dc1);
        dcList.add(dc2);
        dcList.add(dc3);
        dcList.add(dc4);
        dcList.add(dc5);
        dcList.add(dc6);
        dcList.add(dc7);
        dcList.add(dc8);
        dcList.add(dc9);
        dcList.add(dc10);*/
        dcList.add(dc11);
        exchange.getOut().setBody(dcList);

    }
}
