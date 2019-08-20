package com.scio.quantum.ai.pii.process;

import com.google.gson.Gson;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.http.HttpStatus;

import javax.ws.rs.BadRequestException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ValidateRESTRequestProcessor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        String jobRequest = exchange.getIn().getBody(String.class);
        Gson gson = new Gson();
        Map<String,String> map = new HashMap<String,String>();
        map = (Map<String,String>) gson.fromJson(jobRequest, map.getClass());

        if(!map.containsKey("email")){
            exchange.getIn().setBody("{\"status\":\"400\",\"message\":\"No email field is present\"}");
            exchange.getIn().setHeader(Exchange.CONTENT_TYPE, "application/json");
            exchange.getIn().setHeader(Exchange.HTTP_RESPONSE_CODE, HttpStatus.SC_BAD_REQUEST);
            throw new BadRequestException("No email field is present");
        }else{
            String email = map.get("email");
            if(isValidEmailFormat(email)!=true){
                exchange.getIn().setBody("{\"status\":\"400\",\"message\":\"Please check your email format\"}");
                exchange.getIn().setHeader(Exchange.CONTENT_TYPE, "application/json");
                exchange.getIn().setHeader(Exchange.HTTP_RESPONSE_CODE, HttpStatus.SC_BAD_REQUEST);
                throw new BadRequestException("Please check your email format");
            }
        }
        if(!map.containsKey("path")){
            exchange.getIn().setBody("{\"status\":\"400\",\"message\":\"No path field is present\"}");
            exchange.getIn().setHeader(Exchange.CONTENT_TYPE, "application/json");
            exchange.getIn().setHeader(Exchange.HTTP_RESPONSE_CODE, HttpStatus.SC_BAD_REQUEST);
            throw new BadRequestException("No path field is present");
        }

        if(!map.containsKey("title")){
            exchange.getIn().setBody("{\"status\":\"400\",\"message\":\"No path field is present\"}");
            exchange.getIn().setHeader(Exchange.CONTENT_TYPE, "application/json");
            exchange.getIn().setHeader(Exchange.HTTP_RESPONSE_CODE, HttpStatus.SC_BAD_REQUEST);
            throw new BadRequestException("No title field is present");
        }
        UUID uuid = UUID.randomUUID();
        map.put("jobID",uuid.toString());
        exchange.setProperty("jobID",uuid.toString());
        exchange.getOut().setBody(map);
        exchange.setProperty("map",map);
    }

    private boolean isValidEmailFormat(String email){
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
    }
}
