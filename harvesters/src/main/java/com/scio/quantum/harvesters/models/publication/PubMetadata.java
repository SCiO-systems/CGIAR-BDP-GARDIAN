
package com.scio.quantum.harvesters.models.publication;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

public class PubMetadata {

    @SerializedName("ContentProvider")
    @Expose
    private List<ContentProvider> contentProvider = null;
    @SerializedName("DOI")
    @Expose
    private List<DOI> doi = null;
    @SerializedName("IsOpenAccess")
    @Expose
    private boolean isOpenAccess;
    @SerializedName("DocLink")
    @Expose
    private String docLink;
    @SerializedName("Citation")
    @Expose
    private String citation;
    @SerializedName("FeaturedImgURL")
    @Expose
    private String featuredImgURL;
    @SerializedName("Title")
    @Expose
    private String title;
    @SerializedName("Summary")
    @Expose
    private String summary;
    @SerializedName("Series")
    @Expose
    private String series;
    @SerializedName("Volume")
    @Expose
    private String volume;
    @SerializedName("Num")
    @Expose
    private String num;
    @SerializedName("Pages")
    @Expose
    private String pages;
    @SerializedName("IssuedYear")
    @Expose
    private String issuedYear;
    @SerializedName("PubYear")
    @Expose
    private String pubYear;
    @SerializedName("Language")
    @Expose
    private String language;
    @SerializedName("Category")
    @Expose
    private String category;
    @SerializedName("Publisher")
    @Expose
    private String publisher;
    @SerializedName("PublisherLink")
    @Expose
    private String publisherLink;
    @SerializedName("Authors")
    @Expose
    private String authors;
    @SerializedName("Topics")
    @Expose
    private List<String> topics = null;
    @SerializedName("Geographic")
    @Expose
    private List<String> geographic = null;
    @SerializedName("hasBeenTransferred")
    @Expose
    private String hasBeenTransferred;
    @SerializedName("hasBeenHarvested")
    @Expose
    private String hasBeenHarvested;
    @SerializedName("status")
    @Expose
    private String status;

    /**
     * No args constructor for use in serialization
     *
     */
    public PubMetadata() {
    }

    /**
     *
     * @param summary
     * @param isOpenAccess
     * @param citation
     * @param series
     * @param geographic
     * @param issuedYear
     * @param status
     * @param pages
     * @param featuredImgURL
     * @param publisherLink
     * @param publisher
     * @param authors
     * @param topics
     * @param category
     * @param title
     * @param num
     * @param doi
     * @param contentProvider
     * @param volume
     * @param hasBeenHarvested
     * @param docLink
     * @param language
     * @param pubYear
     * @param hasBeenTransferred
     */
    public PubMetadata(List<ContentProvider> contentProvider, List<DOI> doi, boolean isOpenAccess, String docLink, String citation, String featuredImgURL, String title, String summary, String series, String volume, String num, String pages, String issuedYear, String pubYear, String language, String category, String publisher, String publisherLink, String authors, List<String> topics, List<String> geographic, String hasBeenTransferred, String hasBeenHarvested, String status) {
        super();
        this.contentProvider = contentProvider;
        this.doi = doi;
        this.isOpenAccess = isOpenAccess;
        this.docLink = docLink;
        this.citation = citation;
        this.featuredImgURL = featuredImgURL;
        this.title = title;
        this.summary = summary;
        this.series = series;
        this.volume = volume;
        this.num = num;
        this.pages = pages;
        this.issuedYear = issuedYear;
        this.pubYear = pubYear;
        this.language = language;
        this.category = category;
        this.publisher = publisher;
        this.publisherLink = publisherLink;
        this.authors = authors;
        this.topics = topics;
        this.geographic = geographic;
        this.hasBeenTransferred = hasBeenTransferred;
        this.hasBeenHarvested = hasBeenHarvested;
        this.status = status;
    }

    public List<ContentProvider> getContentProvider() {
        return contentProvider;
    }

    public void setContentProvider(List<ContentProvider> contentProvider) {
        this.contentProvider = contentProvider;
    }

    public List<DOI> getDoi() {
        return doi;
    }

    public void setDoi(List<DOI> doi) {
        this.doi = doi;
    }

    public boolean isIsOpenAccess() {
        return isOpenAccess;
    }

    public void setIsOpenAccess(boolean isOpenAccess) {
        this.isOpenAccess = isOpenAccess;
    }

    public String getDocLink() {
        return docLink;
    }

    public void setDocLink(String docLink) {
        this.docLink = docLink;
    }

    public String getCitation() {
        return citation;
    }

    public void setCitation(String citation) {
        this.citation = citation;
    }

    public String getFeaturedImgURL() {
        return featuredImgURL;
    }

    public void setFeaturedImgURL(String featuredImgURL) {
        this.featuredImgURL = featuredImgURL;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    public String getIssuedYear() {
        return issuedYear;
    }

    public void setIssuedYear(String issuedYear) {
        this.issuedYear = issuedYear;
    }

    public String getPubYear() {
        return pubYear;
    }

    public void setPubYear(String pubYear) {
        this.pubYear = pubYear;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getPublisherLink() {
        return publisherLink;
    }

    public void setPublisherLink(String publisherLink) {
        this.publisherLink = publisherLink;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public List<String> getTopics() {
        return topics;
    }

    public void setTopics(List<String> topics) {
        this.topics = topics;
    }

    public List<String> getGeographic() {
        return geographic;
    }

    public void setGeographic(List<String> geographic) {
        this.geographic = geographic;
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

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("contentProvider", contentProvider).append("doi", doi).append("isOpenAccess", isOpenAccess).append("docLink", docLink).append("citation", citation).append("featuredImgURL", featuredImgURL).append("title", title).append("summary", summary).append("series", series).append("volume", volume).append("num", num).append("pages", pages).append("issuedYear", issuedYear).append("pubYear", pubYear).append("language", language).append("category", category).append("publisher", publisher).append("publisherLink", publisherLink).append("authors", authors).append("topics", topics).append("geographic", geographic).append("hasBeenTransferred", hasBeenTransferred).append("hasBeenHarvested", hasBeenHarvested).append("status", status).toString();
    }

}
