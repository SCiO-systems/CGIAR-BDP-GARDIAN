package com.scio.quantum.harvesters.process;

import com.google.api.services.sheets.v4.model.ValueRange;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.util.List;

public class GoogleSheetListProcessor implements Processor {

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
        ValueRange data = exchange.getIn().getBody(ValueRange.class);
        List<List<Object>> rows = data.getValues();
        exchange.getOut().setBody(rows.subList(2,rows.size()));

        /*ValueRange data = exchange.getIn().getBody(ValueRange.class);
        List<List<Object>> rows = data.getValues();
        Iterator<List<Object>> itRows = rows.iterator();
        itRows.next();itRows.next();*/
        /*while(itRows.hasNext()){
            List<Object> row = itRows.next();

            String pubYear = (String)row.get(this.yearField);
            String docKey = (String)row.get(this.docKeyField);
            String authors = (String)row.get(this.authorsField);
            String title = (String)

            System.out.println(row.get(0));
        }*/

        //System.out.println("LETS SEE: "+data.getValues().get(4).get(0));




        /*JsonParser jp = new JsonParser();
        JsonObject json = jp.parse(data).getAsJsonObject();
        System.out.println("data "+json.toString());*/
    }
}
