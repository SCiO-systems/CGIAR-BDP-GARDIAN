
package com.scio.quantum.harvesters.models.dataset;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class File {

    @SerializedName("DownloadLink")
    @Expose
    private String downloadLink;
    @SerializedName("Accessibility")
    @Expose
    private String accessibility;
    @SerializedName("ContentType")
    @Expose
    private String contentType;
    @SerializedName("Label")
    @Expose
    private String label;
    @SerializedName("URL")
    @Expose
    private String url;
    @SerializedName("filename")
    @Expose
    private String filename;


    /**
     * No args constructor for use in serialization
     * 
     */
    public File() {
    }

    /**
     * 
     * @param downloadLink
     * @param url
     * @param label
     * @param contentType
     * @param accessibility
     */
    public File(String downloadLink, String accessibility, String contentType, String label, String url, String filename) {
        super();
        this.downloadLink = downloadLink;
        this.accessibility = accessibility;
        this.contentType = contentType;
        this.label = label;
        this.url = url;
        this.filename = filename;
    }

    public String getDownloadLink() {
        return downloadLink;
    }

    public void setDownloadLink(String downloadLink) {
        this.downloadLink = downloadLink;
    }

    public String getAccessibility() {
        return accessibility;
    }

    public void setAccessibility(String accessibility) {
        this.accessibility = accessibility;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getURL() {
        return url;
    }

    public void setURL(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("downloadLink", downloadLink)
                .append("accessibility", accessibility)
                .append("contentType", contentType)
                .append("label", label)
                .append("url", url)
                .append("filename", this.filename)
                .toString();
    }

}
