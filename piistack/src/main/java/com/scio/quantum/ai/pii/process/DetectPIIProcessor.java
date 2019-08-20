package com.scio.quantum.ai.pii.process;

import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ie.crf.*;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.CoreAnnotations;

import com.scio.quantum.ai.pii.models.file.CandidateFile;
import com.scio.quantum.ai.pii.models.job.Job;
import com.scio.quantum.ai.pii.models.namedentity.NamedEntity;
import com.scio.quantum.ai.pii.models.namedentity.NamedEntityClass;
import com.scio.quantum.ai.pii.models.namedentity.NamedEntityPosition;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.util.*;

public class DetectPIIProcessor implements Processor {

    private int maxLength = 151;
    private int minLength = 3;

    @Override
    public void process(Exchange exchange) throws Exception {
        Job job = exchange.getProperty("JOB",Job.class);
        ArrayList<String> customTerms = job.getCustomTerms();
        Set<String> customTermsSet = new HashSet<String>(customTerms);
        CandidateFile cf = exchange.getIn().getBody(CandidateFile.class);


        String serializedClassifier = "edu/stanford/nlp/models/ner/english.all.3class.distsim.crf.ser.gz";
        AbstractSequenceClassifier<CoreLabel> classifier = CRFClassifier.getClassifier(serializedClassifier);
        List<List<CoreLabel>> out = classifier.classify(cf.getContent());

        ArrayList<NamedEntity> namedEntities = new ArrayList<>();
        for (List<CoreLabel> sentence : out) {
            for (CoreLabel word : sentence) {
                if(word.get(CoreAnnotations.AnswerAnnotation.class).equalsIgnoreCase("PERSON")){
                    String lemma = word.word();
                    if((lemma.length()<maxLength)&&(lemma.length()>minLength)){
                        if(!isCustomTerm(lemma,customTermsSet)) {
                            double confidence = 0.0;
                            int start = word.beginPosition();
                            int stop = word.endPosition();
                            NamedEntityPosition nep = new NamedEntityPosition(start, stop);
                            NamedEntityClass nec = NamedEntityClass.PERSON;
                            NamedEntity ne = new NamedEntity(lemma, confidence, nep, nec);
                            namedEntities.add(ne);
                        }
                    }
                }
            }
        }

        if(namedEntities != null){
            cf.setNamedEntities(namedEntities);
        }
        exchange.getOut().setBody(cf,CandidateFile.class);
    }

    private boolean isCustomTerm(String lemma,Set<String> customTermsSet){
        return customTermsSet.contains(lemma);
    }
}
