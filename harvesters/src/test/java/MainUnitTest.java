import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.mongodb.MongoClient;
import com.scio.quantum.harvesters.process.DataverseBean;
import com.scio.quantum.harvesters.route.datasets.DataverseRouter;
import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.SimpleRegistry;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainUnitTest {


    public static void main(String[] args) throws IOException {

        String url = "https://data.cimmyt.org/dataverse/root?q=&types=datasets&sort=dateSort&order=asc&page=1";
        Document doc = Jsoup.connect(url).get();

        Elements newsHeadlines = doc.select("#resultsCountPaginatorBlock .results-count");

        Elements news = doc.select(".card-title-icon-block");

        Iterator<Element> itNews = news.iterator();

        while(itNews.hasNext()){
            Element element = itNews.next();
            Elements link = element.select("a");
            String href = link.attr("href");

            System.out.println(href.substring(href.indexOf("=")+1));




        }


        //System.out.println("TEST: "+newsHeadlines.text());




        /*SimpleRegistry sr = new SimpleRegistry();
        sr.put("mongoConnection",new MongoClient("192.168.122.191", 27017));
        sr.put("dataversebean",new DataverseBean());

        final CamelContext context = new DefaultCamelContext(sr);
        try {
            System.out.println("Start Route");
            context.addRoutes(new DataverseRouter("test","dataset","africarice"));
            context.start();
            //context.startRoute("ModelValidationRawKafkaRoute");
            System.out.println("End Route");

            Thread.sleep(5 * 60 * 1000);


            context.stop();


        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }
}
