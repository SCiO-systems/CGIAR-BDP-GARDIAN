package com.scio.quantum.extractor.models.vocabulary;

public class AgrovocTerm extends Term{

    private String URI;
    private String agrovocId;

    public AgrovocTerm(String URI, String agrovocId, String term, String frequency) {
        super(term, frequency);
        this.URI = URI;
        this.agrovocId = agrovocId;
    }

    public String getURI() {
        return URI;
    }

    public void setURI(String URI) {
        this.URI = URI;
    }

    public String getAgrovocId() {
        return agrovocId;
    }

    public void setAgrovocId(String agrovocId) {
        this.agrovocId = agrovocId;
    }

    @Override
    public String toString() {
        return "AgrovocTerm{" +
                "URI='" + URI + '\'' +
                ", agrovocId='" + agrovocId + '\'' +
                '}';
    }
}