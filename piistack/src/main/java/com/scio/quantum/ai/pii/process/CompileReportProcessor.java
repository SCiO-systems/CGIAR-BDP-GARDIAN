package com.scio.quantum.ai.pii.process;

import com.scio.quantum.ai.pii.models.file.CandidateFile;
import com.scio.quantum.ai.pii.models.job.Job;
import com.scio.quantum.ai.pii.models.report.SimpleReport;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.util.ArrayList;

public class CompileReportProcessor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        Job jb = exchange.getProperty("JOB",Job.class);
        ArrayList<CandidateFile> candidateFiles = exchange.getIn().getBody(ArrayList.class);
        SimpleReport sr = new SimpleReport();
        sr.setJob(jb);
        sr.setDetection(candidateFiles);
        exchange.getOut().setBody(sr,SimpleReport.class);
    }
}
