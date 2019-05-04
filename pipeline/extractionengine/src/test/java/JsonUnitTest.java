import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.Map;
import java.util.Set;

public class JsonUnitTest {

    public static void main(String[] args){

        String esRes = " {\"_index\":\"dummy\",\"_type\":\"dummy_record\",\"_version\":0,\"found\":true,\"took\":0,\"term_vectors\":{\"dummy_text\":{\"terms\":{\"test\":{\"term_freq\":3,\"tokens\":[{\"position\":1,\"start_offset\":8,\"end_offset\":12},{\"position\":2,\"start_offset\":13,\"end_offset\":17},{\"position\":3,\"start_offset\":18,\"end_offset\":22}]},\"twitter\":{\"term_freq\":1,\"tokens\":[{\"position\":0,\"start_offset\":0,\"end_offset\":7}]}}}}}";

        JsonParser parser = new JsonParser();
        JsonObject messageObject = parser.parse(esRes).getAsJsonObject();

        JsonObject termVectors = messageObject.get("term_vectors").getAsJsonObject();
        JsonObject dummyText = termVectors.get("dummy_text").getAsJsonObject();
        JsonObject terms = dummyText.getAsJsonObject("terms");

        Set<Map.Entry<String, JsonElement>> entries = terms.entrySet();//will return members of your object
        for (Map.Entry<String, JsonElement> entry: entries) {
            System.out.println(entry.getKey()+""+entry.getValue());
            String term = entry.getKey();
            JsonObject termStatistics = entry.getValue().getAsJsonObject();
            String termFrequency = termStatistics.get("term_freq").getAsString();
            System.out.println("Term: "+term+" Freq "+termFrequency);
        }



        //System.out.println((ArrayList)(terms.toArray()));

    }
}
