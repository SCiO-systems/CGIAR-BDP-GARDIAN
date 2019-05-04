
package com.scio.quantum.harvesters.models.external.dataverse.resource;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class DataFile {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("filename")
    @Expose
    private String filename;
    @SerializedName("contentType")
    @Expose
    private String contentType;
    @SerializedName("storageIdentifier")
    @Expose
    private String storageIdentifier;
    @SerializedName("originalFormatLabel")
    @Expose
    private String originalFormatLabel;
    @SerializedName("md5")
    @Expose
    private String md5;
    @SerializedName("description")
    @Expose
    private String description;

    /**
     * No args constructor for use in serialization
     * 
     */
    public DataFile() {
    }

    /**
     * 
     * @param id
     * @param storageIdentifier
     * @param originalFormatLabel
     * @param description
     * @param md5
     * @param filename
     * @param contentType
     */
    public DataFile(int id, String filename, String contentType, String storageIdentifier, String originalFormatLabel, String md5, String description) {
        super();
        this.id = id;
        this.filename = filename;
        this.contentType = contentType;
        this.storageIdentifier = storageIdentifier;
        this.originalFormatLabel = originalFormatLabel;
        this.md5 = md5;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getStorageIdentifier() {
        return storageIdentifier;
    }

    public void setStorageIdentifier(String storageIdentifier) {
        this.storageIdentifier = storageIdentifier;
    }

    public String getOriginalFormatLabel() {
        return originalFormatLabel;
    }

    public void setOriginalFormatLabel(String originalFormatLabel) {
        this.originalFormatLabel = originalFormatLabel;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("id", id).append("filename", filename).append("contentType", contentType).append("storageIdentifier", storageIdentifier).append("originalFormatLabel", originalFormatLabel).append("md5", md5).append("description", description).toString();
    }

}
