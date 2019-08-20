import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class TestTIKA {

    public static void main(String[] args) throws IOException, TikaException, SAXException {

        Tika tika = new Tika();
        File file = new File("C:\\Users\\SOTIRIS SON\\" +
                "Desktop\\quantum_test_pool\\pii_in\\test2.doc");

        /*InputStream targetStream = new FileInputStream(file);
        Parser parser = new AutoDetectParser();
        ContentHandler handler = new BodyContentHandler();
        Metadata metadata = new Metadata();
        ParseContext context = new ParseContext();
        parser.parse(targetStream, handler, metadata, context);

        //System.out.println(metadata.toString());
        System.out.println(handler.toString());*/
        System.out.println(tika.detect(file));


    }
}
