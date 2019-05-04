package com.scio.quantum.extractor.process;

import com.scio.quantum.extractor.models.publication.PublicationModel;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class PrepareSummaryProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        String dataType = exchange.getProperty("DATATYPE",String.class);
        if(dataType.equalsIgnoreCase("publication")){
            PublicationModel pm = exchange.getIn().getBody(PublicationModel.class);
            String summary = pm.getPubMetadata().getTitle()+" "+pm.getPubMetadata().getSummary();
            String query = " {   \"doc\" : {  \"dummy_text\" : \""+summary+"\"   }" +
                    ",\"term_statistics\" : true" +
                    ",\"field_statistics\" : false" +
                    " } \n ";
            exchange.getOut().setBody(query);
            exchange.setProperty("OBJECT",pm);
        }
    }
}
