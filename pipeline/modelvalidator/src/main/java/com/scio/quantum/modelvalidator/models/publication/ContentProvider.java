
/*
 * Copyright (C) 2019 SCiO
 *  This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *  This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 */

package com.scio.quantum.modelvalidator.models.publication;

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
    private List<HDL> hDL = null;

    /**
     * No args constructor for use in serialization
     *
     */
    public ContentProvider() {
    }

    /**
     *
     * @param providerLink
     * @param hDL
     * @param contentProviderID
     * @param contentProviderName
     */
    public ContentProvider(String contentProviderID, String contentProviderName, List<ProviderLink> providerLink, List<HDL> hDL) {
        super();
        this.contentProviderID = contentProviderID;
        this.contentProviderName = contentProviderName;
        this.providerLink = providerLink;
        this.hDL = hDL;
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
        return hDL;
    }

    public void setHDL(List<HDL> hDL) {
        this.hDL = hDL;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("contentProviderID", contentProviderID).append("contentProviderName", contentProviderName).append("providerLink", providerLink).append("hDL", hDL).toString();
    }

}
