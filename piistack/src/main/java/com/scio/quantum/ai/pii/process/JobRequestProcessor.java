package com.scio.quantum.ai.pii.process;

import com.scio.quantum.ai.pii.models.job.Job;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Map;

public class JobRequestProcessor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {

        Map<String,String> jobRequest = exchange.getIn().getBody(Map.class);
        String email = jobRequest.get("email");
        String title = jobRequest.get("title");
        String path = jobRequest.get("path");
        String jobID = jobRequest.get("jobID");
        String mode = jobRequest.get("mode");
        ArrayList<String> customTerms = new ArrayList<>();
        if(jobRequest.containsKey("customTerms")){
            customTerms = loadCustomTerms(jobRequest.get("customTerms"));
        }
        String identifier = "";
        if(jobRequest.containsKey("identifier")){
            identifier = jobRequest.get("identifier");
        }
        Job jb = new Job(jobID,email,title,path,mode,identifier,customTerms);
        exchange.getOut().setBody(jb,Job.class);
    }

    private ArrayList<String> loadCustomTerms(String customTermsPath){
        Path customTerms = Paths.get(customTermsPath);
        if(Files.exists(customTerms)) {
            try (BufferedReader reader = Files.newBufferedReader(customTerms, Charset.forName("UTF-8"))) {
                String currentLine = null;
                ArrayList<String> terms = new ArrayList<>();
                while ((currentLine = reader.readLine()) != null) {//while there is content on the current line
                    terms.add(currentLine.trim());
                }
                return terms;
            } catch (IOException ex) {
                return new ArrayList<>();
            }
        }else{
            return new ArrayList<>();
        }
    }
}
