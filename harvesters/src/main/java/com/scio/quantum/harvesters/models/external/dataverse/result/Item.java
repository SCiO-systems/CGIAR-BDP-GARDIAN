
package com.scio.quantum.harvesters.models.external.dataverse.result;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Item {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("global_id")
    @Expose
    private String globalId;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("published_at")
    @Expose
    private String publishedAt;
    @SerializedName("citationHtml")
    @Expose
    private String citationHtml;
    @SerializedName("identifier_of_dataverse")
    @Expose
    private String identifierOfDataverse;
    @SerializedName("name_of_dataverse")
    @Expose
    private String nameOfDataverse;
    @SerializedName("citation")
    @Expose
    private String citation;
    @SerializedName("authors")
    @Expose
    private List<String> authors = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Item() {
    }

    /**
     * 
     * @param publishedAt
     * @param authors
     * @param citation
     * @param identifierOfDataverse
     * @param citationHtml
     * @param description
     * @param name
     * @param nameOfDataverse
     * @param globalId
     * @param type
     * @param url
     */
    public Item(String name, String type, String url, String globalId, String description, String publishedAt, String citationHtml, String identifierOfDataverse, String nameOfDataverse, String citation, List<String> authors) {
        super();
        this.name = name;
        this.type = type;
        this.url = url;
        this.globalId = globalId;
        this.description = description;
        this.publishedAt = publishedAt;
        this.citationHtml = citationHtml;
        this.identifierOfDataverse = identifierOfDataverse;
        this.nameOfDataverse = nameOfDataverse;
        this.citation = citation;
        this.authors = authors;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getGlobalId() {
        return globalId;
    }

    public void setGlobalId(String globalId) {
        this.globalId = globalId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getCitationHtml() {
        return citationHtml;
    }

    public void setCitationHtml(String citationHtml) {
        this.citationHtml = citationHtml;
    }

    public String getIdentifierOfDataverse() {
        return identifierOfDataverse;
    }

    public void setIdentifierOfDataverse(String identifierOfDataverse) {
        this.identifierOfDataverse = identifierOfDataverse;
    }

    public String getNameOfDataverse() {
        return nameOfDataverse;
    }

    public void setNameOfDataverse(String nameOfDataverse) {
        this.nameOfDataverse = nameOfDataverse;
    }

    public String getCitation() {
        return citation;
    }

    public void setCitation(String citation) {
        this.citation = citation;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("name", name).append("type", type).append("url", url).append("globalId", globalId).append("description", description).append("publishedAt", publishedAt).append("citationHtml", citationHtml).append("identifierOfDataverse", identifierOfDataverse).append("nameOfDataverse", nameOfDataverse).append("citation", citation).append("authors", authors).toString();
    }

}
