
package com.scio.quantum.harvesters.models.dataset;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;

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
    @SerializedName("Publisher")
    @Expose
    private String publisher;
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
    @SerializedName("PublisherLink")
    @Expose
    private String publisherLink;
    @SerializedName("Citation")
    @Expose
    private String citation;
    @SerializedName("Files")
    @Expose
    private List<File> files = null;
    @SerializedName("DOI")
    @Expose
    private List<DOI> doi = null;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("hasBeenHarvested")
    @Expose
    private String hasBeenHarvested;
    @SerializedName("hasBeenTransferred")
    @Expose
    private String hasBeenTransferred;

    /**
     * No args constructor for use in serialization
     * 
     */
    public DatasetMetadata() {
    }

    /**
     * 
     * @param summary
     * @param termsOfUse
     * @param files
     * @param citation
     * @param series
     * @param geographic
     * @param issuedYear
     * @param status
     * @param publisherLink
     * @param publisher
     * @param authors
     * @param category
     * @param topics
     * @param title
     * @param doi
     * @param contentProvider
     * @param hasBeenHarvested
     * @param language
     * @param pubYear
     * @param license
     * @param hasBeenTransferred
     * @param accessibility
     */
    public DatasetMetadata(List<String> geographic, String category, List<String> topics, String accessibility, String termsOfUse, List<ContentProvider> contentProvider, String authors, String license, String title, String publisher, String series, String pubYear, String issuedYear, String language, String summary, String publisherLink, String citation, List<File> files, List<DOI> doi, String status, String hasBeenHarvested, String hasBeenTransferred) {
        super();
        this.geographic = geographic;
        this.category = category;
        this.topics = topics;
        this.accessibility = accessibility;
        this.termsOfUse = termsOfUse;
        this.contentProvider = contentProvider;
        this.authors = authors;
        this.license = license;
        this.title = title;
        this.publisher = publisher;
        this.series = series;
        this.pubYear = pubYear;
        this.issuedYear = issuedYear;
        this.language = language;
        this.summary = summary;
        this.publisherLink = publisherLink;
        this.citation = citation;
        this.files = files;
        this.doi = doi;
        this.status = status;
        this.hasBeenHarvested = hasBeenHarvested;
        this.hasBeenTransferred = hasBeenTransferred;
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

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
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

    public String getPublisherLink() {
        return publisherLink;
    }

    public void setPublisherLink(String publisherLink) {
        this.publisherLink = publisherLink;
    }

    public String getCitation() {
        return citation;
    }

    public void setCitation(String citation) {
        this.citation = citation;
    }

    public List<File> getFiles() {
        return files;
    }

    public void setFiles(List<File> files) {
        this.files = files;
    }

    public List<DOI> getDOI() {
        return doi;
    }

    public void setDOI(List<DOI> doi) {
        this.doi = doi;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getHasBeenHarvested() {
        return hasBeenHarvested;
    }

    public void setHasBeenHarvested(String hasBeenHarvested) {
        this.hasBeenHarvested = hasBeenHarvested;
    }

    public String getHasBeenTransferred() {
        return hasBeenTransferred;
    }

    public void setHasBeenTransferred(String hasBeenTransferred) {
        this.hasBeenTransferred = hasBeenTransferred;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("geographic", geographic).append("category", category).append("topics", topics).append("accessibility", accessibility).append("termsOfUse", termsOfUse).append("contentProvider", contentProvider).append("authors", authors).append("license", license).append("title", title).append("publisher", publisher).append("series", series).append("pubYear", pubYear).append("issuedYear", issuedYear).append("language", language).append("summary", summary).append("publisherLink", publisherLink).append("citation", citation).append("files", files).append("doi", doi).append("status", status).append("hasBeenHarvested", hasBeenHarvested).append("hasBeenTransferred", hasBeenTransferred).toString();
    }

}
