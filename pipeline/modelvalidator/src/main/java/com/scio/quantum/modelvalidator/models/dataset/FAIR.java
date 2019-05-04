
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

package com.scio.quantum.modelvalidator.models.dataset;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class FAIR {

    @SerializedName("findability")
    @Expose
    private String findability;
    @SerializedName("accessibility")
    @Expose
    private String accessibility;
    @SerializedName("interoperability")
    @Expose
    private String interoperability;
    @SerializedName("reusability")
    @Expose
    private String reusability;

    public String getFindability() {
        return findability;
    }

    public void setFindability(String findability) {
        this.findability = findability;
    }

    public String getAccessibility() {
        return accessibility;
    }

    public void setAccessibility(String accessibility) {
        this.accessibility = accessibility;
    }

    public String getInteroperability() {
        return interoperability;
    }

    public void setInteroperability(String interoperability) {
        this.interoperability = interoperability;
    }

    public String getReusability() {
        return reusability;
    }

    public void setReusability(String reusability) {
        this.reusability = reusability;
    }

    /**
     * No args constructor for use in serialization
     *
     */
    public FAIR() {
    }

    public FAIR(String findability, String accessibility, String interoperability, String reusability) {
        this.findability = findability;
        this.accessibility = accessibility;
        this.interoperability = interoperability;
        this.reusability = reusability;
    }



    @Override
    public String toString() {
        return new ToStringBuilder(this).append("findability", findability).append("accessibility", accessibility).append("interoperability", interoperability).toString();
    }

}
