import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ie.crf.*;
import edu.stanford.nlp.io.IOUtils;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.sequences.DocumentReaderAndWriter;
import edu.stanford.nlp.util.Triple;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class TestCORENLP {

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        String serializedClassifier = "edu/stanford/nlp/models/ner/english.all.3class.distsim.crf.ser.gz";

        AbstractSequenceClassifier<CoreLabel> classifier = CRFClassifier.getClassifier(serializedClassifier);
        String text = "My Name is Sotiris L. Konstantinidis and I live in Greece. Also I work with Maritina, living in Davi ";

        List<List<CoreLabel>> out = classifier.classify(text);

        System.out.println(classifier.classifyToString(text,"xml", true));


        for (List<CoreLabel> sentence : out) {
            for (CoreLabel word : sentence) {
                if(word.get(CoreAnnotations.AnswerAnnotation.class).equalsIgnoreCase("PERSON")){

                    System.out.println(word.word());

                    //System.out.println(word.beginPosition());
                    //System.out.println(word.endPosition());
                }
            }
            System.out.println();
        }





        System.out.println( "End of Processing" );



    }


}
