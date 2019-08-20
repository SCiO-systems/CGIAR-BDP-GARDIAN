package com.scio.quantum.ai.pii.process;


import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.scio.quantum.ai.pii.models.job.Job;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class RemoveCompletedJobProcessor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {

        Job jb = exchange.getProperty("JOB",Job.class);
        String jobID = jb.getJobID();
        DBObject removeJob = new BasicDBObject("jobID", jobID);
        exchange.getOut().setBody(removeJob);

    }
}
