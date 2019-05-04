
package com.scio.quantum.harvesters.models.publication;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

public class ContentProvider {

    @SerializedName("ContentProviderID")
    @Expose
    private String contentProviderID;
    @SerializedName("ContentProviderName")
    @Expose
    private String contentProviderName;
    @SerializedName("ProviderLink")
    @Expose
    private List<ProviderLink> providerLink = null;
    @SerializedName("HDL")
    @Expose
    private List<HDL> hdl = null;

    /**
     * No args constructor for use in serialization
     *
     */
    public ContentProvider() {
    }

    /**
     *
     * @param providerLink
     * @param hdl
     * @param contentProviderID
     * @param contentProviderName
     */
    public ContentProvider(String contentProviderID, String contentProviderName, List<ProviderLink> providerLink, List<HDL> hdl) {
        super();
        this.contentProviderID = contentProviderID;
        this.contentProviderName = contentProviderName;
        this.providerLink = providerLink;
        this.hdl = hdl;
    }

    public String getContentProviderID() {
        return contentProviderID;
    }

    public void setContentProviderID(String contentProviderID) {
        this.contentProviderID = contentProviderID;
    }

    public String getContentProviderName() {
        return contentProviderName;
    }

    public void setContentProviderName(String contentProviderName) {
        this.contentProviderName = contentProviderName;
    }

    public List<ProviderLink> getProviderLink() {
        return providerLink;
    }

    public void setProviderLink(List<ProviderLink> providerLink) {
        this.providerLink = providerLink;
    }

    public List<HDL> getHDL() {
        return hdl;
    }

    public void setHDL(List<HDL> hDL) {
        this.hdl = hDL;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("contentProviderID", contentProviderID).append("contentProviderName", contentProviderName).append("providerLink", providerLink).append("hDL", hdl).toString();
    }

}
