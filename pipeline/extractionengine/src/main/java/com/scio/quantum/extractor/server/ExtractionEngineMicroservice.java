package com.scio.quantum.extractor.server;

import com.mongodb.MongoClient;
import com.scio.quantum.extractor.route.ExtractionRouter;
import org.apache.camel.component.kafka.KafkaComponent;
import org.apache.camel.main.Main;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExtractionEngineMicroservice extends Main {

    //TODO: Implement Dataset Model
    //TODO: Add ELK Log Appender
    //TODO: Implement Dataset Route

    private static final Logger logger = LoggerFactory.getLogger(ExtractionEngineMicroservice.class);

    public static void main(String[] args){

        try {
            ExtractionEngineMicroserviceOptions eemo = new ExtractionEngineMicroserviceOptions();
            CommandLineParser parser = new DefaultParser();

            CommandLine cmd = parser.parse( eemo.createOptions(), args);
            String kafkaBroker = cmd.getOptionValue("kafka");
            String mongo = cmd.getOptionValue("mongo");

            String mongoIp = mongo.split(":")[0];
            String mongoPort = mongo.split(":")[1];
            String resourceType = cmd.getOptionValue("resource");
            String vocabulary = cmd.getOptionValue("vocabulary");
            String termVectorBuilder = cmd.getOptionValue("termvector");
            String elasticsearch = cmd.getOptionValue("elasticsearch");


            KafkaComponent kafka = new KafkaComponent();
            kafka.setBrokers(kafkaBroker);


            ExtractionEngineMicroservice modelValidationMicroservice = new ExtractionEngineMicroservice();
            modelValidationMicroservice.bind("kafka",kafka);
            modelValidationMicroservice.bind("mongoConnection",new MongoClient(mongoIp, Integer.parseInt(mongoPort)));


            modelValidationMicroservice.addRouteBuilder(new ExtractionRouter(resourceType,kafkaBroker,vocabulary,termVectorBuilder,elasticsearch));
            logger.info("======   Extraction Microservice is running   =====");
            logger.info("Kafka Broker: "+kafkaBroker);
            logger.info("Mongo Database: "+mongo);
            logger.info("Resource Type: "+resourceType);
            modelValidationMicroservice.run();

        } catch (ParseException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
