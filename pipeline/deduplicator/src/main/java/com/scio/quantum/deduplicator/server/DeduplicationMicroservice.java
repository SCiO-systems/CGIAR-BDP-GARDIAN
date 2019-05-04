package com.scio.quantum.deduplicator.server;

import com.mongodb.MongoClient;
import com.scio.quantum.deduplicator.route.DeduplicatorRouter;
import org.apache.camel.component.kafka.KafkaComponent;
import org.apache.camel.main.Main;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.ParseException;
import org.ehcache.CacheManager;
import org.ehcache.config.Configuration;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.xml.XmlConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;

public class DeduplicationMicroservice extends Main {

    //TODO: Implement Dataset Model
    //TODO: Add ELK Log Appender
    //TODO: Implement Dataset Route

    private static final Logger logger = LoggerFactory.getLogger(DeduplicationMicroservice.class);

    public static void main(String[] args){

        try {

            DeduplicationMicroservice mut = new DeduplicationMicroservice();
            URL url = mut.getClass().getResource("/ehcache.xml");
            Configuration xmlConfig = new XmlConfiguration(url);
            CacheManager cacheManager = CacheManagerBuilder.newCacheManager(xmlConfig);
            cacheManager.init();

            DeduplicationMicroserviceOptions dmo = new DeduplicationMicroserviceOptions();
            CommandLineParser parser = new DefaultParser();

            CommandLine cmd = parser.parse( dmo.createOptions(), args);
            String kafkaBroker = cmd.getOptionValue("kafka");
            String mongo = cmd.getOptionValue("mongo");

            String mongoIp = mongo.split(":")[0];
            String mongoPort = mongo.split(":")[1];
            String resourceType = cmd.getOptionValue("resource");


            KafkaComponent kafka = new KafkaComponent();
            kafka.setBrokers(kafkaBroker);


            DeduplicationMicroservice duplicationMicroservice = new DeduplicationMicroservice();

            duplicationMicroservice.bind("kafka",kafka);
            duplicationMicroservice.bind("mongoConnection",new MongoClient(mongoIp, Integer.parseInt(mongoPort)));
            duplicationMicroservice.bind("cacheManager",cacheManager);

            resourceType = "dataset";

            duplicationMicroservice.addRouteBuilder(new DeduplicatorRouter(kafkaBroker,resourceType));

            duplicationMicroservice.getOrCreateCamelContext().startRoute("DeduplicatorConsumeRawTopic");
            //DeduplicatorConsumeRawTopic
            logger.info("======   Deduplication Microservice is running   =====");
            logger.info("Kafka Broker: "+kafkaBroker);
            logger.info("Mongo Database: "+mongo);
            logger.info("Resource Type: "+resourceType);
            duplicationMicroservice.run();


        } catch (ParseException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
