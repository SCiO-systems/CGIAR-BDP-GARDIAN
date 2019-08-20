package com.scio.quantum.ai.pii.server;

import com.mongodb.MongoClient;
import com.scio.quantum.ai.pii.route.APIRouter;
import com.scio.quantum.ai.pii.route.PIIRouter;
import org.apache.camel.component.kafka.KafkaComponent;
import org.apache.camel.main.Main;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;

public class PIIMicroservice extends Main {

    public static void main(String[] args) throws Exception {

        PIIMicroserviceOptions piimo = new PIIMicroserviceOptions();
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse( piimo.createOptions(), args);
        String ip = cmd.getOptionValue("ip");

        KafkaComponent kafka = new KafkaComponent();
        kafka.setBrokers(ip+":9092");

        String kafkaBroker = ip+":9092";
        String mongoIP = ip;
        String mongoPort = "27017";

        PIIMicroservice piim = new PIIMicroservice();

        //piim.bind("sslContextParameters", parameters);
        piim.bind("kafka",kafka);
        piim.bind("mongoConnection",new MongoClient(mongoIP, Integer.parseInt(mongoPort)));

        piim.addRouteBuilder(new APIRouter(ip));
        piim.addRouteBuilder(new PIIRouter("docker-cluster",ip,kafkaBroker));


        System.out.println("API Online");

        piim.run();

    }
}
