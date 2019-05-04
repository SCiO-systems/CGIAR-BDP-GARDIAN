import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUnitTest {

    public static void main(String[] args){

        //JsonElement jelement = new JsonParser().parse("no json");


        //String regex = "(\\d+\\-\\d+)";
        //String regex = "(\\d\\-)";
        //String regex = "\\([^\\d]*(\\d+)[^\\d]*\\)";
        String regex = "(\\d+)\\([^\\d]*(\\d+)[^\\d]*\\)";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher("Journal of International Development 23(1): 82-102");

        if(matcher.find() == true){
            System.out.println("FOUND");
            System.out.println(matcher.groupCount());
            System.out.println(matcher.group(1));
        }


        /*String cleanSource = "Proceedings of the National Academy of Sciences of the United States of America (PNAS) 109(31): 12332–12337";
        int startIndex = cleanSource.indexOf(":");
        String pages = cleanSource.substring(startIndex+1).trim();
        System.out.println(pages);*/

    }

}
