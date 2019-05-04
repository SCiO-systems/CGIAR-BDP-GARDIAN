package com.scio.quantum.harvesters.route.publications;

import com.scio.quantum.harvesters.exceptions.*;
import com.scio.quantum.harvesters.models.publication.PublicationModel;
import com.scio.quantum.harvesters.process.*;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;

import java.text.SimpleDateFormat;
import java.util.Date;

public class IFPRIRouter extends RouteBuilder {
    private String filePool;
    private String dataType;

    public IFPRIRouter(String filePool, String dataType) {
        super();
        this.filePool = filePool;
        this.dataType = dataType;
    }

    @Override
    public void configure() throws Exception {
        try {
            if(dataType.equalsIgnoreCase("publication")){
                publicationRoute();
            }else if(dataType.equalsIgnoreCase("dataset")){
                datasetRoute();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void publicationRoute(){
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

        ////ERRORS_CGIAR_02_IFPRI_OAI_2019-04-02.txt

        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String date = simpleDateFormat.format(new Date());
        String errorPath = this.filePool+System.getProperty("file.separator")+"ERRORS_CGIAR_02_IFPRI_OAI_"+date+".txt";

        /*from("timer://startOAI")
                .routeId("IFPRI Collection p15738coll5")
                .setProperty("SET",constant("p15738coll5"))
                .setProperty("ERROR_FILE",constant(errorPath))
                .setProperty("INDEX",constant(this.filePool+System.getProperty("file.separator")+"p15738coll5.idx"))
                .setProperty("RESUMPTION",constant("FIRST"))
                .loopDoWhile(simple("${exchangeProperty.RESUMPTION} != 'LAST'"))
                    .choice()
                        .when(simple("${exchangeProperty.RESUMPTION} == 'FIRST'"))
                            .enrich("http://cdm15738.contentdm.oclc.org/oai/oai.php/?verb=ListRecords&set=p15738coll5&metadataPrefix=oai_dc")
                            .process(new ResumptionTokenProcessor())
                            .split(body().tokenizeXML("metadata","metadata"))
                            .process(new IFPRICollectionProcessor())
                            .setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
                            .setHeader(Exchange.HTTP_METHOD,constant("GET"))
                            .pollEnrich().simple("http://api.crossref.org/works/${header.doi}").aggregationStrategy(new DOIAggregator())
                            .marshal().json(JsonLibrary.Gson, PublicationModel.class)
                            .process(new Base64EncoderProcessor())
                            .setHeader(Exchange.FILE_NAME, constant("PUBS_CGIAR_IFPRI_p15738coll5_"+date+".json"))
                            .transform(body().append(System.getProperty("line.separator")))
                            .to("file://" + filePool + "?charset=utf-8&fileExist=append")
                            .process(new InternalIdProcessor())
                            .setHeader(Exchange.FILE_NAME, constant("p15738coll5.idx"))
                            .transform(body().append(System.getProperty("line.separator")))
                            .to("file://" + filePool + "?charset=utf-8&fileExist=append")
                            .end()
                        .endChoice()
                    .otherwise()
                            .enrich()
                            .simple("http://cdm15738.contentdm.oclc.org/oai/oai.php/?verb=ListRecords&resumptionToken=${exchangeProperty.RESUMPTION}")
                            .process(new ResumptionTokenProcessor())
                            .split(body().tokenizeXML("metadata","metadata"))
                            .process(new IFPRICollectionProcessor())
                            .setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
                            .setHeader(Exchange.HTTP_METHOD,constant("GET"))
                            .pollEnrich().simple("http://api.crossref.org/works/${header.doi}").aggregationStrategy(new DOIAggregator())
                            .marshal().json(JsonLibrary.Gson, PublicationModel.class)
                            .process(new Base64EncoderProcessor())
                            .setHeader(Exchange.FILE_NAME, constant("PUBS_CGIAR_IFPRI_p15738coll5_"+date+".json"))
                            .transform(body().append(System.getProperty("line.separator")))
                            .to("file://" + filePool + "?charset=utf-8&fileExist=append")
                            .process(new InternalIdProcessor())
                            .setHeader(Exchange.FILE_NAME, constant("p15738coll5.idx"))
                            .transform(body().append(System.getProperty("line.separator")))
                            .to("file://" + filePool + "?charset=utf-8&fileExist=append")
                        .end()
                .end();*/

        from("timer://startOAI")
                .routeId("IFPRI Collection p15738coll2")
                .setProperty("SET",constant("p15738coll2"))
                .setProperty("ERROR_FILE",constant(errorPath))
                .setProperty("INDEX",constant(this.filePool+System.getProperty("file.separator")+"p15738coll2.idx"))
                .setProperty("RESUMPTION",constant("FIRST"))
                .loopDoWhile(simple("${exchangeProperty.RESUMPTION} != 'LAST'"))
                .choice()
                .when(simple("${exchangeProperty.RESUMPTION} == 'FIRST'"))
                .enrich("http://cdm15738.contentdm.oclc.org/oai/oai.php/?verb=ListRecords&set=p15738coll2&metadataPrefix=oai_dc")
                .process(new ResumptionTokenProcessor())
                .split(body().tokenizeXML("metadata","metadata"))
                .process(new IFPRICollectionProcessor())
                .setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
                .setHeader(Exchange.HTTP_METHOD,constant("GET"))
                .pollEnrich().simple("http://api.crossref.org/works/${header.doi}").aggregationStrategy(new DOIAggregator())
                .marshal().json(JsonLibrary.Gson, PublicationModel.class)
                .process(new Base64EncoderProcessor())
                .setHeader(Exchange.FILE_NAME, constant("PUBS_CGIAR_IFPRI_p15738coll2_"+date+".json"))
                .transform(body().append(System.getProperty("line.separator")))
                .to("file://" + filePool + "?charset=utf-8&fileExist=append")
                .process(new InternalIdProcessor())
                .setHeader(Exchange.FILE_NAME, constant("p15738coll2.idx"))
                .transform(body().append(System.getProperty("line.separator")))
                .to("file://" + filePool + "?charset=utf-8&fileExist=append")
                .end()
                .endChoice()
                .otherwise()
                .enrich()
                .simple("http://cdm15738.contentdm.oclc.org/oai/oai.php/?verb=ListRecords&resumptionToken=${exchangeProperty.RESUMPTION}")
                .process(new ResumptionTokenProcessor())
                .split(body().tokenizeXML("metadata","metadata"))
                .process(new IFPRICollectionProcessor())
                .setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
                .setHeader(Exchange.HTTP_METHOD,constant("GET"))
                .pollEnrich().simple("http://api.crossref.org/works/${header.doi}").aggregationStrategy(new DOIAggregator())
                .marshal().json(JsonLibrary.Gson, PublicationModel.class)
                .process(new Base64EncoderProcessor())
                .setHeader(Exchange.FILE_NAME, constant("PUBS_CGIAR_IFPRI_p15738coll2_"+date+".json"))
                .transform(body().append(System.getProperty("line.separator")))
                .to("file://" + filePool + "?charset=utf-8&fileExist=append")
                .process(new InternalIdProcessor())
                .setHeader(Exchange.FILE_NAME, constant("p15738coll2.idx"))
                .transform(body().append(System.getProperty("line.separator")))
                .to("file://" + filePool + "?charset=utf-8&fileExist=append")
                .end()
                .end();
    }

    private void datasetRoute(){

    }
}
