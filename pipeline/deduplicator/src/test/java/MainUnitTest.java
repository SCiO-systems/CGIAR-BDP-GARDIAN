import com.mongodb.MongoClient;
import org.apache.camel.CamelContext;
import org.apache.camel.component.kafka.KafkaComponent;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.SimpleRegistry;
import org.ehcache.CacheManager;
import org.ehcache.config.Configuration;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.xml.XmlConfiguration;

import java.net.URL;

public class MainUnitTest {


    public static void main(String[] args){


        MainUnitTest mut = new MainUnitTest();
        URL url = mut.getClass().getResource("/ehcache.xml");
        Configuration xmlConfig = new XmlConfiguration(url);
        CacheManager cacheManager = CacheManagerBuilder.newCacheManager(xmlConfig);
        cacheManager.init();

        SimpleRegistry sr = new SimpleRegistry();
        sr.put("mongoConnection",new MongoClient("192.168.122.191", 27017));
        sr.put("cacheManager",cacheManager);


        KafkaComponent kafka = new KafkaComponent();
        kafka.setBrokers("192.168.122.191:9092");


        final CamelContext context = new DefaultCamelContext(sr);

        try {

            System.out.println("Start Route");
            //context.addRoutes(new TestRouter());
            //context.addRoutes(new DeduplicatorRouter("raw","refined","192.168.122.191:9092","publication"));
            context.addComponent("kafka", kafka);

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
