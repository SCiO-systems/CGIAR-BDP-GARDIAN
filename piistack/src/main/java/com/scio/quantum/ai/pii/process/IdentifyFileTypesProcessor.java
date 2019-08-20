package com.scio.quantum.ai.pii.process;

import com.scio.quantum.ai.pii.models.file.CandidateFile;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class IdentifyFileTypesProcessor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        String pathName = exchange.getIn().getBody(String.class);
        Path path = new File(pathName).toPath();

        boolean isDirectory = Files.isDirectory(path);
        List<File> files = new ArrayList<>();

        if(isDirectory){
            Stream<Path> walk = Files.walk(path);
            files = walk.filter(Files::isRegularFile)
                    .map(x -> new File(x.toString())).collect(Collectors.toList());
        }else if(!isDirectory){
            files.add(new File(pathName));
        }


        Iterator<File> iteratorFiles = files.iterator();
        ArrayList<CandidateFile> cfs = new ArrayList<>();
        while(iteratorFiles.hasNext()){

            File file = iteratorFiles.next();

            CandidateFile cf = identifyMIMEType(file);
            if(cf != null){
                cfs.add(cf);
            }

        }
        exchange.getOut().setBody(cfs,ArrayList.class);
    }

    private CandidateFile identifyMIMEType(File file) throws IOException, TikaException, SAXException {
        Tika tika = new Tika();

        String mediaType = tika.detect(file);

        if( mediaType.equalsIgnoreCase("text/plain")||
            mediaType.equalsIgnoreCase("text/csv")||
            mediaType.equalsIgnoreCase("application/vnd.ms-excel")||
            mediaType.equalsIgnoreCase("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")||
            mediaType.equalsIgnoreCase("application/pdf")||
            mediaType.equalsIgnoreCase("application/vnd.openxmlformats-officedocument.wordprocessingml.document")||
            mediaType.equalsIgnoreCase("application/msword")){

            String filename = file.getName();
            String content = extractContent(file);

            return new CandidateFile(file.getPath(),mediaType,content,filename);

        }else{
            return null;
        }
    }

    private String extractContent(File file) throws IOException, TikaException, SAXException {
        InputStream targetStream = new FileInputStream(file);
        Parser parser = new AutoDetectParser();
        ContentHandler handler = new BodyContentHandler(-1);
        Metadata metadata = new Metadata();
        ParseContext context = new ParseContext();
        parser.parse(targetStream, handler, metadata, context);

        return handler.toString();
    }
}
