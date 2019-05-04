package com.scio.quantum.harvesters.process;

import com.scio.quantum.harvesters.models.external.Center;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.util.ArrayList;

public class PopulateCKANSearchCentersProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {

        Center dc1 = new Center(
                "http://data.iita.org/api/3/action/package_list",
                "true",
                "CGIAR-IITA-DATASETS",
                "International Institute of Tropical Agriculture (IITA)",
                "IITA",
                "http://data.iita.org/api/3/action/package_search?rows=250&q=");

        Center dc2 = new Center(
                "http://data.ilri.org/portal/api/3/action/package_list",
                "true",
                "CGIAR-ILRI-DATASETS",
                "International Livestock Research Institute (ILRI)",
                "ILRI",
                "http://data.ilri.org/portal/api/3/action/package_show?id=");

        ArrayList<Center> dcList = new ArrayList();
        dcList.add(dc1);
        //dcList.add(dc2);
        exchange.getOut().setBody(dcList);

    }
}
