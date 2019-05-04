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

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.bson.types.ObjectId;

public class PrepareMongoUpdateProcessor implements Processor {

    @Override
    public void process(Exchange exchange){
        String resource = exchange.getProperty("RESOURCE",String.class);
        if(resource.equalsIgnoreCase("PUBLICATION")){
            String objectId = (String)exchange.getIn().getBody();
            DBObject filterField = new BasicDBObject("_id",new ObjectId(objectId));
            DBObject updateObj = new BasicDBObject("$set", new BasicDBObject("PubMetadata.hasBeenTransferred", "true"));
            exchange.getIn().setBody(new Object[] {filterField, updateObj});
        }else if(resource.equalsIgnoreCase("DATASET")){
            String objectId = (String)exchange.getIn().getBody();
            DBObject filterField = new BasicDBObject("_id",new ObjectId(objectId));
            DBObject updateObj = new BasicDBObject("$set", new BasicDBObject("DatasetMetadata.hasBeenTransferred", "true"));
            exchange.getIn().setBody(new Object[] {filterField, updateObj});
        }
    }
}