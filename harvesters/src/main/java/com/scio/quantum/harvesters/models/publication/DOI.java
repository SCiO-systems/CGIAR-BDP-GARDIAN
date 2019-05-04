
package com.scio.quantum.harvesters.models.publication;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class DOI {

    @SerializedName("link")
    @Expose
    private String link;
    @SerializedName("active")
    @Expose
    private boolean active;

    /**
     * No args constructor for use in serialization
     *
     */
    public DOI() {
    }

    /**
     *
     * @param link
     * @param active
     */
    public DOI(String link, boolean active) {
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("link", link).append("active", active).toString();
    }

}
