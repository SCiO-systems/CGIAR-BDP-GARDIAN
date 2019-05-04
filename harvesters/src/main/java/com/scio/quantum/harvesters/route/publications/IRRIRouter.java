package com.scio.quantum.harvesters.route.publications;

import com.scio.quantum.harvesters.exceptions.*;
import com.scio.quantum.harvesters.models.publication.PublicationModel;
import com.scio.quantum.harvesters.process.*;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;

import java.text.SimpleDateFormat;
import java.util.Date;

public class IRRIRouter extends RouteBuilder {

    private String filePool;
    private String dataType;

    public IRRIRouter(String filePool, String dataType) {
        super();
        this.filePool = filePool;
        this.dataType = dataType;
    }

    @Override
    public void configure() throws Exception {

        errorHandler(deadLetterChannel(
                "log:?level=ERROR&showAll=true&showCaughtException=true"));

        onException(AlreadyProcessedException.class)
                .handled(true)
                .process(new ExceptionHandler())
                .to("log:?level=ERROR&showAll=true&showCaughtException=true");

        onException(DOINotAvailable.class)
                .continued(true)
                .process(new ExceptionHandler())
                .to("log:?level=ERROR&showAll=true&showCaughtException=true");

        onException(NoTitlePresent.class)
                .handled(true)
                .process(new ExceptionHandler())
                .to("log:?level=ERROR&showAll=true&showCaughtException=true");

        onException(NoDatePresent.class)
                .handled(true)
                .process(new ExceptionHandler())
                .to("log:?level=ERROR&showAll=true&showCaughtException=true");

        onException(NoSoundexMatch.class)
                .handled(true)
                .process(new ExceptionHandler())
                .to("log:?level=ERROR&showAll=true&showCaughtException=true");

        String clientId = "629954483252-o7mn1nc0hamld8p3l3c8qn1kcu60dqtv.apps.googleusercontent.com";
        String clientSecret = "A1HnRo6kWVR7KQthzXNYJnEq";
        String accessToken = "ya29.GlvlBnXgLObcll3JxOiBBVVIZeI5DFDav4bqAdO2V_lmqsGBK0KSU_RTOaAxCCWqH5n2hKGoPENKqbwCb9wM_enJBB8Wk0Efkj8T1pMvGaKViRB570tSOAmHCANK";
        String refreshToken = "1/a1YYsAIGtcVPJDbwiV05iN-UDk3dOorXPq1aq4rtqOw";
        String spreadSheetID = "1ZxNbTo4F4nEMFENsRR5732ONkdzoULobHhLIUG7iRKE";
        String applicationName = "camel-google-sheets/1.0";

        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String date = simpleDateFormat.format(new Date());
        String errorPath = this.filePool+System.getProperty("file.separator")+"ERRORS_CGIAR_11_IRRI_GS_"+date+".txt";

        from("google-sheets-stream://spreadsheets?accessToken="+
                accessToken+"&refreshToken="+refreshToken+
                "&clientId="+clientId+"&clientSecret="+clientSecret+
                "&spreadsheetId="+spreadSheetID+"&applicationName="+applicationName+
                "&includeGridData=true&maxResults=2&range=Sheet1!A1:Q11779")
                .setProperty("ERROR_FILE",constant(errorPath))
                .setProperty("INDEX",constant(this.filePool+System.getProperty("file.separator")+"irri.idx"))
                .process(new GoogleSheetListProcessor())
                .split(body())
                .process(new IRRICollectionProcessor())
                .setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
                .setHeader(Exchange.HTTP_METHOD,constant("GET"))
                .pollEnrich().simple("http://api.crossref.org/works/${header.doi}").aggregationStrategy(new DOIAggregator())
                .marshal().json(JsonLibrary.Gson, PublicationModel.class)
                .process(new Base64EncoderProcessor())
                .setHeader(Exchange.FILE_NAME, constant("PUBS_CGIAR_IRRI_"+date+".json"))
                .transform(body().append(System.getProperty("line.separator")))
                .to("file://" + filePool + "?charset=utf-8&fileExist=append")
                .process(new InternalIdProcessor())
                .setHeader(Exchange.FILE_NAME, constant("irri.idx"))
                .transform(body().append(System.getProperty("line.separator")))
                .to("file://" + filePool + "?charset=utf-8&fileExist=append")
                .to("mock:result");
    }


    public String getFilePool() {
        return filePool;
    }

    public void setFilePool(String filePool) {
        this.filePool = filePool;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }
}
