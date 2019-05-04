package com.scio.quantum.harvesters.process;

import com.scio.quantum.harvesters.models.external.Center;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.text.SimpleDateFormat;
import java.util.Date;

public class InitializeProperties implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String date = simpleDateFormat.format(new Date());
        String filePool = exchange.getProperty("FILE_POOL",String.class);

        Center dc = exchange.getIn().getBody(Center.class);
        exchange.setProperty("ownSpace",dc.getOwnSpace());
        exchange.setProperty("contentProviderName",dc.getCenterName());
        exchange.setProperty("contentProviderID",dc.getCenterId());
        exchange.setProperty("exporterURL",dc.getExporterURL());
        exchange.setProperty("ROOT_URL",dc.getRootUrl().trim());
        exchange.setProperty("DATAVERSE_ID",dc.getDataverseID());

        String errorPath = filePool+System.getProperty("file.separator")+"ERRORS_CGIAR_01_"+dc.getCenterName()+"_DT_DV_"+date+".txt";
        exchange.setProperty("ERROR_FILE",errorPath);
        exchange.setProperty("DATE",date);
        exchange.getOut().setBody(dc);
    }
}
