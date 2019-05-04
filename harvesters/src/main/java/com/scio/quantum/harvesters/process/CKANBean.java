package com.scio.quantum.harvesters.process;

import com.scio.quantum.harvesters.models.external.ckan.resources.iita.CKANIITAPackageResources;
import com.scio.quantum.harvesters.models.external.ckan.resources.ilri.CKANILRIPackageResources;
import com.scio.quantum.harvesters.models.external.ckan.result.CKANSearchResult;
import org.apache.camel.Exchange;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CKANBean {

    public ArrayList<String> buildHarvestList(Exchange exchange) throws IOException {
        CKANSearchResult csr = exchange.getIn().getBody(CKANSearchResult.class);
        List<String> results = csr.getResult();
        ArrayList<String> harvestList = new ArrayList<>();
        harvestList.addAll(results);
        return harvestList;
    }

    public ArrayList buildResultList(Exchange exchange){
        Object ckanPackageResources = exchange.getIn().getBody();
        ArrayList resultList = new ArrayList();
        if(ckanPackageResources instanceof CKANIITAPackageResources){
            CKANIITAPackageResources cpr = (CKANIITAPackageResources)ckanPackageResources;
            com.scio.quantum.harvesters.models.external.ckan.resources.iita.Result result = cpr.getResult();
            List<com.scio.quantum.harvesters.models.external.ckan.resources.iita.Result_> results = result.getResults();
            resultList.addAll(results);
        }else if(ckanPackageResources instanceof CKANILRIPackageResources){
            CKANILRIPackageResources cpr = (CKANILRIPackageResources)ckanPackageResources;
            com.scio.quantum.harvesters.models.external.ckan.resources.ilri.Result result = cpr.getResult();
            resultList.add(result);
        }
        return resultList;
    }

}
