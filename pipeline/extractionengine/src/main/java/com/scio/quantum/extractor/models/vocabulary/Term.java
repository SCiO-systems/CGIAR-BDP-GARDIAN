package com.scio.quantum.extractor.models.vocabulary;

public class Term {
    private String term;
    private String frequency;

    public Term(String term, String frequency) {
        this.term = term;
        this.frequency = frequency;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    @Override
    public String toString() {
        return "Term{" +
                "term='" + term + '\'' +
                ", frequency='" + frequency + '\'' +
                '}';
    }
}
