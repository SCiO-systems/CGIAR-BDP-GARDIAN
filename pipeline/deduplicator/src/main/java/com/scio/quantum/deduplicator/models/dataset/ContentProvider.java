
package com.scio.quantum.deduplicator.models.dataset;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

public class ContentProvider {

    @SerializedName("HDL")
    @Expose
    private List<HDL> hdl = null;
    @SerializedName("ProviderLink")
    @Expose
    private List<ProviderLink> providerLink = null;
    @SerializedName("ContentProviderID")
    @Expose
    private String contentProviderID;
    @SerializedName("ContentProviderName")
    @Expose
    private String contentProviderName;
    @SerializedName("OwnSpace")
    @Expose
    private String ownSpace;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ContentProvider() {
    }

    /**
     * 
     * @param ownSpace
     * @param providerLink
     * @param hdl
     * @param contentProviderID
     * @param contentProviderName
     */
    public ContentProvider(List<HDL> hdl, List<ProviderLink> providerLink, String contentProviderID, String contentProviderName, String ownSpace) {
        super();
        this.hdl = hdl;
        this.providerLink = providerLink;
        this.contentProviderID = contentProviderID;
        this.contentProviderName = contentProviderName;
        this.ownSpace = ownSpace;
    }

    public List<HDL> gethdl() {
        return hdl;
    }

    public void sethdl(List<HDL> hdl) {
        this.hdl = hdl;
    }

    public List<ProviderLink> getProviderLink() {
        return providerLink;
    }

    public void setProviderLink(List<ProviderLink> providerLink) {
        this.providerLink = providerLink;
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

    public String getOwnSpace() {
        return ownSpace;
    }

    public void setOwnSpace(String ownSpace) {
        this.ownSpace = ownSpace;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("hdl", hdl).append("providerLink", providerLink).append("contentProviderID", contentProviderID).append("contentProviderName", contentProviderName).append("ownSpace", ownSpace).toString();
    }

}
