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
import org.apache.camel.component.kafka.KafkaConstants;
import org.bson.Document;
import org.bson.types.ObjectId;

public class PrepareMessageProcessor implements Processor {

    @Override
    public void process(Exchange exchange){
        ObjectId objectId = ((Document)exchange.getIn().getBody()).getObjectId("_id");

        exchange.getOut().setHeader(KafkaConstants.PARTITION_KEY, 0);
        exchange.getOut().setHeader(KafkaConstants.KEY, "id");

        exchange.getOut().setBody(objectId.toString(),String.class);
    }
}