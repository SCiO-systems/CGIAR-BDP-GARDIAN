import com.mongodb.MongoClient;
import com.scio.quantum.extractor.route.ExtractionRouter;
import org.apache.camel.CamelContext;
import org.apache.camel.component.kafka.KafkaComponent;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.SimpleRegistry;

public class ESUnitTest {

    public static void main(String[] args) throws Exception {

        /*SimpleRegistry sr = new SimpleRegistry();
        sr.put("mongoConnection",new MongoClient("192.168.122.191", 27017));

        KafkaComponent kafka = new KafkaComponent();
        kafka.setBrokers("192.168.122.191:9092");

        final CamelContext context = new DefaultCamelContext(sr);

        context.addRoutes(new ExtractionRouter());
        context.addComponent("kafka", kafka);
        context.start();


        //context.startRoute("ModelValidationRawKafkaRoute");

        System.out.println("End Route");

        Thread.sleep(5 * 60 * 1000);


        context.stop();*/



    }

}
