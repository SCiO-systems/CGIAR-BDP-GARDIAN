
package com.scio.quantum.harvesters.models.external.dataverse.resource;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Dataverse {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("identifier")
    @Expose
    private String identifier;
    @SerializedName("persistentUrl")
    @Expose
    private String persistentUrl;
    @SerializedName("protocol")
    @Expose
    private String protocol;
    @SerializedName("authority")
    @Expose
    private String authority;
    @SerializedName("publisher")
    @Expose
    private String publisher;
    @SerializedName("publicationDate")
    @Expose
    private String publicationDate;
    @SerializedName("datasetVersion")
    @Expose
    private DatasetVersion datasetVersion;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Dataverse() {
    }

    /**
     * 
     * @param id
     * @param authority
     * @param protocol
     * @param persistentUrl
     * @param datasetVersion
     * @param identifier
     * @param publicationDate
     * @param publisher
     */
    public Dataverse(int id, String identifier, String persistentUrl, String protocol, String authority, String publisher, String publicationDate, DatasetVersion datasetVersion) {
        super();
        this.id = id;
        this.identifier = identifier;
        this.persistentUrl = persistentUrl;
        this.protocol = protocol;
        this.authority = authority;
        this.publisher = publisher;
        this.publicationDate = publicationDate;
        this.datasetVersion = datasetVersion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getPersistentUrl() {
        return persistentUrl;
    }

    public void setPersistentUrl(String persistentUrl) {
        this.persistentUrl = persistentUrl;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(String publicationDate) {
        this.publicationDate = publicationDate;
    }

    public DatasetVersion getDatasetVersion() {
        return datasetVersion;
    }

    public void setDatasetVersion(DatasetVersion datasetVersion) {
        this.datasetVersion = datasetVersion;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("id", id).append("identifier", identifier).append("persistentUrl", persistentUrl).append("protocol", protocol).append("authority", authority).append("publisher", publisher).append("publicationDate", publicationDate).append("datasetVersion", datasetVersion).toString();
    }

}
