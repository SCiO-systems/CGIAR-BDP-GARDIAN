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

package com.scio.quantum.modelvalidator.server;

import com.mongodb.MongoClient;
import com.scio.quantum.modelvalidator.process.ModelValidationBean;
import com.scio.quantum.modelvalidator.route.ColdModelValidationRouter;
import com.scio.quantum.modelvalidator.route.ModelValidationRouter;
import org.apache.camel.component.kafka.KafkaComponent;
import org.apache.camel.main.Main;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ModelValidationMicroservice extends Main {

    //TODO: Implement Dataset Model
    //TODO: Add ELK Log Appender
    //TODO: Implement Dataset Route

    private static final Logger logger = LoggerFactory.getLogger(ModelValidationMicroservice.class);

    public static void main(String[] args){

        try {
            ModelValidationMicroserviceOptions mvmo = new ModelValidationMicroserviceOptions();
            CommandLineParser parser = new DefaultParser();

            CommandLine cmd = parser.parse( mvmo.createOptions(), args);
            String kafkaBroker = cmd.getOptionValue("kafka");
            String mongo = cmd.getOptionValue("mongo");

            String mongoIp = mongo.split(":")[0];
            String mongoPort = mongo.split(":")[1];

            String filePool = cmd.getOptionValue("filepool");
            String resourceType = cmd.getOptionValue("resource");


            KafkaComponent kafka = new KafkaComponent();
            kafka.setBrokers(kafkaBroker);


            ModelValidationMicroservice modelValidationMicroservice = new ModelValidationMicroservice();
            modelValidationMicroservice.bind("kafka",kafka);
            modelValidationMicroservice.bind("mongoConnection",new MongoClient(mongoIp, Integer.parseInt(mongoPort)));
            modelValidationMicroservice.bind("modelvalidationbean",new ModelValidationBean());

            filePool = "C:\\Users\\SOTIRIS SON\\Desktop\\quantum_test_pool\\harvester";
            resourceType = "dataset";

            modelValidationMicroservice.addRouteBuilder(new ModelValidationRouter(filePool,kafkaBroker,resourceType));
            //modelValidationMicroservice.addRouteBuilder(new ColdModelValidationRouter(filePool,kafkaBroker,resourceType));
            logger.info("======   Model Validation Microservice is running   =====");
            logger.info("Kafka Broker: "+kafkaBroker);
            logger.info("Mongo Database: "+mongo);
            logger.info("File Pool: "+filePool);
            logger.info("Resource Type: "+resourceType);
            modelValidationMicroservice.run();

        } catch (ParseException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
