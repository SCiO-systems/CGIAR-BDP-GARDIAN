package com.scio.quantum.harvesters.process;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;

public class ResumptionTokenProcessor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        String resource = exchange.getIn().getBody(String.class);
        exchange.getOut().setBody(resource);

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(new InputSource(new StringReader(resource)));

        doc.getDocumentElement().normalize();
        NodeList resumptionToken = doc.getElementsByTagName("resumptionToken");
        for(int i=0;i<resumptionToken.getLength();i++){
            String identifier = resumptionToken.item(i).getTextContent().trim();
            if((identifier.isEmpty())||(identifier.length()<2)){
                exchange.setProperty("RESUMPTION","LAST");
            }else{
                exchange.setProperty("RESUMPTION",identifier);
            }
        }



    }
}
