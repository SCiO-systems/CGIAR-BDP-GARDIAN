
package com.scio.quantum.deduplicator.models.dataset;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DatasetMetadata {

    @SerializedName("Geographic")
    @Expose
    private List<String> geographic = null;
    @SerializedName("Category")
    @Expose
    private String category;
    @SerializedName("Topics")
    @Expose
    private List<String> topics = null;
    @SerializedName("Accessibility")
    @Expose
    private String accessibility;
    @SerializedName("TermsOfUse")
    @Expose
    private String termsOfUse;
    @SerializedName("ContentProvider")
    @Expose
    private List<ContentProvider> contentProvider = null;
    @SerializedName("Authors")
    @Expose
    private String authors;
    @SerializedName("License")
    @Expose
    private String license;
    @SerializedName("Title")
    @Expose
    private String title;
    @SerializedName("Series")
    @Expose
    private String series;
    @SerializedName("PubYear")
    @Expose
    private String pubYear;
    @SerializedName("IssuedYear")
    @Expose
    private String issuedYear;
    @SerializedName("Language")
    @Expose
    private String language;
    @SerializedName("Summary")
    @Expose
    private String summary;
    @SerializedName("Citation")
    @Expose
    private String citation;
    @SerializedName("Files")
    @Expose
    private List<Object> files = null;
    @SerializedName("DOI")
    @Expose
    private List<DOI> doi = null;
    @SerializedName("hasBeenTransferred")
    @Expose
    private String hasBeenTransferred;
    @SerializedName("hasBeenHarvested")
    @Expose
    private String hasBeenHarvested;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("ExtractedMetadata")
    @Expose
    private List<ExtractedMetadata> extractedMetadata = null;
    @SerializedName("QuantumId")
    @Expose
    private String quantumId;
    @SerializedName("FAIR")
    @Expose
    private FAIR fair = null;
    @SerializedName("titleHash")
    @Expose
    private String titleHash;
    @SerializedName("doiHash")
    @Expose
    private String doiHash;

    /**
     * No args constructor for use in serialization
     * 
     */
    public DatasetMetadata() {
    }

    public DatasetMetadata(List<String> geographic, String category, List<String> topics, String accessibility,
                           String termsOfUse, List<ContentProvider> contentProvider, String authors,
                           String license, String title, String series, String pubYear, String issuedYear,
                           String language, String summary, String citation, List<Object> files, List<DOI> doi,
                           String hasBeenTransferred, String hasBeenHarvested, String status,
                           List<ExtractedMetadata> extractedMetadata,String quantumId,
                           FAIR fair, String titleHash, String doiHash) {
        this.geographic = geographic;
        this.category = category;
        this.topics = topics;
        this.accessibility = accessibility;
        this.termsOfUse = termsOfUse;
        this.contentProvider = contentProvider;
        this.authors = authors;
        this.license = license;
        this.title = title;
        this.series = series;
        this.pubYear = pubYear;
        this.issuedYear = issuedYear;
        this.language = language;
        this.summary = summary;
        this.citation = citation;
        this.files = files;
        this.doi = doi;
        this.hasBeenTransferred = hasBeenTransferred;
        this.hasBeenHarvested = hasBeenHarvested;
        this.status = status;
        this.extractedMetadata = extractedMetadata;
        this.quantumId = quantumId;
        this.fair = fair;
        this.titleHash = titleHash;
        this.doiHash = doiHash;
    }

    public List<String> getGeographic() {
        return geographic;
    }

    public void setGeographic(List<String> geographic) {
        this.geographic = geographic;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<String> getTopics() {
        return topics;
    }

    public void setTopics(List<String> topics) {
        this.topics = topics;
    }

    public String getAccessibility() {
        return accessibility;
    }

    public void setAccessibility(String accessibility) {
        this.accessibility = accessibility;
    }

    public String getTermsOfUse() {
        return termsOfUse;
    }

    public void setTermsOfUse(String termsOfUse) {
        this.termsOfUse = termsOfUse;
    }

    public List<ContentProvider> getContentProvider() {
        return contentProvider;
    }

    public void setContentProvider(List<ContentProvider> contentProvider) {
        this.contentProvider = contentProvider;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getPubYear() {
        return pubYear;
    }

    public void setPubYear(String pubYear) {
        this.pubYear = pubYear;
    }

    public String getIssuedYear() {
        return issuedYear;
    }

    public void setIssuedYear(String issuedYear) {
        this.issuedYear = issuedYear;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getCitation() {
        return citation;
    }

    public void setCitation(String citation) {
        this.citation = citation;
    }

    public List<Object> getFiles() {
        return files;
    }

    public void setFiles(List<Object> files) {
        this.files = files;
    }

    public List<DOI> getDoi() {
        return doi;
    }

    public void setDoi(List<DOI> doi) {
        this.doi = doi;
    }

    public String getHasBeenTransferred() {
        return hasBeenTransferred;
    }

    public void setHasBeenTransferred(String hasBeenTransferred) {
        this.hasBeenTransferred = hasBeenTransferred;
    }

    public String getHasBeenHarvested() {
        return hasBeenHarvested;
    }

    public void setHasBeenHarvested(String hasBeenHarvested) {
        this.hasBeenHarvested = hasBeenHarvested;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getQuantumId() {
        return quantumId;
    }

    public void setQuantumId(String quantumId) {
        this.quantumId = quantumId;
    }

    public FAIR getFair() {
        return fair;
    }

    public void setFair(FAIR fair) {
        this.fair = fair;
    }

    public String getTitleHash() {
        return titleHash;
    }

    public void setTitleHash(String titleHash) {
        this.titleHash = titleHash;
    }

    public String getDoiHash() {
        return doiHash;
    }

    public void setDoiHash(String doiHash) {
        this.doiHash = doiHash;
    }

    public List<ExtractedMetadata> getExtractedMetadata() {
        return extractedMetadata;
    }

    public void setExtractedMetadata(List<ExtractedMetadata> extractedMetadata) {
        this.extractedMetadata = extractedMetadata;
    }

    @Override
    public String toString() {
        return "DatasetMetadata{" +
                "geographic=" + geographic +
                ", category='" + category + '\'' +
                ", topics=" + topics +
                ", accessibility='" + accessibility + '\'' +
                ", termsOfUse='" + termsOfUse + '\'' +
                ", contentProvider=" + contentProvider +
                ", authors='" + authors + '\'' +
                ", license='" + license + '\'' +
                ", title='" + title + '\'' +
                ", series='" + series + '\'' +
                ", pubYear='" + pubYear + '\'' +
                ", issuedYear='" + issuedYear + '\'' +
                ", language='" + language + '\'' +
                ", summary='" + summary + '\'' +
                ", citation='" + citation + '\'' +
                ", files=" + files +
                ", doi=" + doi +
                ", hasBeenTransferred='" + hasBeenTransferred + '\'' +
                ", hasBeenHarvested='" + hasBeenHarvested + '\'' +
                ", status='" + status + '\'' +
                ", extractedMetadata=" + extractedMetadata +
                ", quantumId='" + quantumId + '\'' +
                ", fair=" + fair +
                ", titleHash='" + titleHash + '\'' +
                ", doiHash='" + doiHash + '\'' +
                '}';
    }
}
