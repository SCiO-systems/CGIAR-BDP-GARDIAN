package com.scio.quantum.harvesters.exceptions;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.http.common.HttpOperationFailedException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;


public class ExceptionHandler implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        Exception exception = (Exception) exchange.getProperty(Exchange.EXCEPTION_CAUGHT,Exception.class);
        if(((exception instanceof DOINotAvailable)
                ||(exception instanceof NoTitlePresent)
                ||(exception instanceof NoDatePresent)
                ||(exception instanceof NoSoundexMatch)

        )){
            String msg = exception.getMessage();
            writeError(exchange,msg);
        } else if(exception instanceof HttpOperationFailedException){
            String msg = "Error: Resource not accessible: "+((HttpOperationFailedException)exception).getUri();
            writeError(exchange,msg);
        } else if(exception instanceof AlreadyProcessedException){
            System.out.println(exception.getMessage());
        } else {
            exception.printStackTrace();
        }
    }

    private void writeError(Exchange exchange,String msg) throws FileNotFoundException {
        String errorPath = exchange.getProperty("ERROR_FILE",String.class);
        File f = new File(errorPath);
        PrintWriter out = null;
        if ( f.exists() && !f.isDirectory() ) {
            out = new PrintWriter(new FileOutputStream(new File(errorPath), true));
        }
        else {
            out = new PrintWriter(errorPath);
        }
        out.append(msg+System.getProperty("line.separator"));
        out.close();
    }
}
