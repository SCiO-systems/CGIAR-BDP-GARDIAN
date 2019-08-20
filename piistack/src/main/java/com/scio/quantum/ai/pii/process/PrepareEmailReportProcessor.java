package com.scio.quantum.ai.pii.process;

import com.scio.quantum.ai.pii.models.job.Job;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;


public class PrepareEmailReportProcessor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {

        Job jb = exchange.getProperty("JOB",Job.class);
        String uiip = exchange.getProperty("UIIP",String.class);

        String jobID = jb.getJobID();
        String title = jb.getTitle();
        String email = jb.getEmail();

        exchange.getOut().setHeader("to",email);
        exchange.getOut().setHeader("from","info@scio.systems");
        exchange.getOut().setHeader("subject","PII report");

        String body = "Please find the PII Report for "+title+" with " +
                "JobID: "+jobID+" to the following link: "+uiip+":9000/"+jobID;
        exchange.getOut().setBody(body,String.class);
    }
}
