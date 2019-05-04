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
import org.apache.camel.CamelContext;
import org.apache.camel.component.kafka.KafkaComponent;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.SimpleRegistry;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class ModelValidationRouteTest extends CamelTestSupport {



    @Parameterized.Parameter(0)
    public String filePool;
    @Parameterized.Parameter(1)
    public String kafkaBroker;
    @Parameterized.Parameter(2)
    public String resourceType;
    @Parameterized.Parameter(3)
    public String mongodb;


    @Parameterized.Parameters(name = "{index}: Test with local installation m1={0} ")
    public static Collection<Object[]> data() {
        Object[][] data = new Object[][]{
                {"C:\\Users\\SOTIRIS SON\\Desktop\\quantum_test_pool\\test\\", "192.168.122.191:9092 ", "publication","192.168.122.191:27017"}
                //{"C:\\Users\\SOTIRIS SON\\Desktop\\quantum_test_pool\\in", "192.168.122.191:9092 ", "dataset","192.168.122.191:27017"}
        };
        return Arrays.asList(data);
    }

    /*@Override
    protected RoutesBuilder createRouteBuilder() throws Exception {
        return new ModelValidationRouter();
    }*/

    @Test
    public void testRawDocumentsInsertion() throws Exception {

        String kafkaTest = kafkaBroker;
        String mongo = mongodb;

        String mongoIp = mongo.split(":")[0];
        String mongoPort = mongo.split(":")[1];

        String filePoolTest = filePool;
        String dataType = resourceType;

        SimpleRegistry sr = new SimpleRegistry();
        sr.put("mongoConnection",new MongoClient(mongoIp, Integer.parseInt(mongoPort)));
        sr.put("modelvalidationbean",new ModelValidationBean());

        KafkaComponent kafka = new KafkaComponent();
        kafka.setBrokers(kafkaTest);

        final CamelContext context = new DefaultCamelContext(sr);
        System.out.println("Start Route");
        context.addRoutes(new ModelValidationRouter(filePoolTest,kafkaTest,dataType));
        context.addComponent("kafka", kafka);
        context.start();
        System.out.println("End Route");
        Thread.sleep(5 * 60 * 1000);
        context.stop();

    }
}
