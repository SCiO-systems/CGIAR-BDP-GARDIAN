package com.scio.quantum.deduplicator.models;

public class QuantumResourceHash {

    private String title = "";
    private String titleHash = "";

    private String doi = "";
    private String doiHash = "";

    public QuantumResourceHash(String title, String titleHash, String doi, String doiHash) {
        this.title = title;
        this.titleHash = titleHash;
        this.doi = doi;
        this.doiHash = doiHash;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitleHash() {
        return titleHash;
    }

    public void setTitleHash(String titleHash) {
        this.titleHash = titleHash;
    }

    public String getDoi() {
        return doi;
    }

    public void setDoi(String doi) {
        this.doi = doi;
    }

    public String getDoiHash() {
        return doiHash;
    }

    public void setDoiHash(String doiHash) {
        this.doiHash = doiHash;
    }

    @Override
    public String toString() {
        return "QuantumResourceHash{" +
                "title='" + title + '\'' +
                ", titleHash='" + titleHash + '\'' +
                ", doi='" + doi + '\'' +
                ", doiHash='" + doiHash + '\'' +
                '}';
    }
}
