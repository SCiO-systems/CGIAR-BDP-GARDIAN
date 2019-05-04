/*
 * Copyright (C) 2019 SCiO
 *  This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *  This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 */

package com.scio.quantum.modelvalidator.process;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.commons.codec.binary.Base64;


public class Base64DecoderProcessor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        String resource = exchange.getProperty("RESOURCE",String.class);
        if(resource.equalsIgnoreCase("PUBLICATION")){
            String stringEncoded = exchange.getIn().getBody(String.class);
            byte[] decodedBytes = Base64.decodeBase64(stringEncoded.getBytes("ISO-8859-1"));
            String stringDecoded = new String(decodedBytes,"ISO-8859-1");
            String utf8String = new String(stringDecoded.getBytes("UTF-8"));
            exchange.getOut().setBody(utf8String);
        }else if(resource.equalsIgnoreCase("DATASET")){
            String stringEncoded = exchange.getIn().getBody(String.class);
            byte[] decodedBytes = Base64.decodeBase64(stringEncoded.getBytes("UTF-8"));
            String stringDecoded = new String(decodedBytes,"UTF-8");
            String utf8String = new String(stringDecoded.getBytes("UTF-8"));
            exchange.getOut().setBody(utf8String);
        }
    }
}