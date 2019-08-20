package com.scio.quantum.ai.pii.process;

import com.scio.quantum.ai.pii.models.job.Job;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class SetPathProcessor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        Job job = exchange.getIn().getBody(Job.class);
        String path = job.getPath();
        String mode = job.getMode();

        exchange.setProperty("JOB",job);
        exchange.setProperty("MODE",mode);
        exchange.getOut().setBody(path,String.class);

    }
}
