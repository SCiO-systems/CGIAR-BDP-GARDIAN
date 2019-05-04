
package com.scio.quantum.deduplicator.models.dataset;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class ProviderLink {

    @SerializedName("link")
    @Expose
    private String link;
    @SerializedName("active")
    @Expose
    private String active;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ProviderLink() {
    }

    /**
     * 
     * @param link
     * @param active
     */
    public ProviderLink(String link, String active) {
        super();
        this.link = link;
        this.active = active;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("link", link).append("active", active).toString();
    }

}
