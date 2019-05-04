package com.scio.quantum.harvesters.process;

import com.scio.quantum.harvesters.models.external.Center;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.util.ArrayList;

public class PopulateDataverseSearchCentersProcessor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        Center dc1 = new Center(
                "https://dataverse.harvard.edu/api/search?q=CIP&key=0f0c69e4-e69a-4096-8169-18e49163f6e1&per_page=1000&type=dataset",
                "false",
                "CGIAR-CIP-DATASETS",
                "International Potato Center (CIP)",
                "https://dataverse.harvard.edu/api/search?q=CIP&key=0f0c69e4-e69a-4096-8169-18e49163f6e1&per_page=1000&type=dataset",
                "https://dataverse.harvard.edu/api/datasets/export?exporter=dataverse_json&persistentId=");

        Center dc2 = new Center(
                "https://dataverse.harvard.edu/api/search?q=IITA&key=0f0c69e4-e69a-4096-8169-18e49163f6e1&per_page=1000&type=dataset",
                "false",
                "CGIAR-IITA-DATASETS",
                "International Institute of Tropical Agriculture (IITA)",
                "https://dataverse.harvard.edu/api/search?q=IITA&key=0f0c69e4-e69a-4096-8169-18e49163f6e1&per_page=1000&type=dataset",
                "https://dataverse.harvard.edu/api/datasets/export?exporter=dataverse_json&persistentId=");

        Center dc3 = new Center(
                "https://dataverse.harvard.edu/api/search?q=IWMI&key=0f0c69e4-e69a-4096-8169-18e49163f6e1&per_page=1000&type=dataset",
                "false",
                "CGIAR-IWMI-DATASETS",
                "International Water Management Institute (IWMI)",
                "https://dataverse.harvard.edu/api/search?q=IWMI&key=0f0c69e4-e69a-4096-8169-18e49163f6e1&per_page=1000&type=dataset",
                "https://dataverse.harvard.edu/api/datasets/export?exporter=dataverse_json&persistentId=");

        ArrayList<Center> dcList = new ArrayList();
        dcList.add(dc1);
        dcList.add(dc2);
        dcList.add(dc3);
        exchange.getOut().setBody(dcList);
    }
}
