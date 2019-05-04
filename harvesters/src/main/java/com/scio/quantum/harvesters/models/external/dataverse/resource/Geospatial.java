
package com.scio.quantum.harvesters.models.external.dataverse.resource;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Geospatial {

    @SerializedName("displayName")
    @Expose
    private String displayName;
    @SerializedName("fields")
    @Expose
    private List<Object> fields = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Geospatial() {
    }

    /**
     * 
     * @param displayName
     * @param fields
     */
    public Geospatial(String displayName, List<Object> fields) {
        super();
        this.displayName = displayName;
        this.fields = fields;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public List<Object> getFields() {
        return fields;
    }

    public void setFields(List<Object> fields) {
        this.fields = fields;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("displayName", displayName).append("fields", fields).toString();
    }

}
