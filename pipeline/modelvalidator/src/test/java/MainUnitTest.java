
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

import com.mongodb.MongoClient;
import com.scio.quantum.modelvalidator.process.ModelValidationBean;
import com.scio.quantum.modelvalidator.route.ModelValidationRouter;
import com.scio.quantum.modelvalidator.route.TestRoute;
import org.apache.camel.CamelContext;
import org.apache.camel.component.kafka.KafkaComponent;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.SimpleRegistry;

public class MainUnitTest {


    public static void main(String[] args){


        SimpleRegistry sr = new SimpleRegistry();
        sr.put("mongoConnection",new MongoClient("192.168.122.191", 27017));
        sr.put("modelvalidationbean",new ModelValidationBean());


        KafkaComponent kafka = new KafkaComponent();
        kafka.setBrokers("192.168.122.191:9092");


        final CamelContext context = new DefaultCamelContext(sr);

        try {

            System.out.println("Start Route");
            context.addRoutes(new TestRoute());
            //context.addComponent("kafka", kafka);

            context.start();
            //context.startRoute("ModelValidationRawKafkaRoute");

            System.out.println("End Route");

            Thread.sleep(5 * 60 * 1000);


            context.stop();


        } catch (Exception e) {
            e.printStackTrace();
        }



    }

}
