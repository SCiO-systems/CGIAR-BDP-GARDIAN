
package com.scio.quantum.extractor.models.publication;

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
