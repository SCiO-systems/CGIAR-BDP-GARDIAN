
package com.scio.quantum.harvesters.models.external.dataverse.resource;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Country {

    @SerializedName("typeName")
    @Expose
    private String typeName;
    @SerializedName("multiple")
    @Expose
    private boolean multiple;
    @SerializedName("typeClass")
    @Expose
    private String typeClass;
    @SerializedName("value")
    @Expose
    private String value;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Country() {
    }

    /**
     * 
     * @param typeName
     * @param value
     * @param typeClass
     * @param multiple
     */
    public Country(String typeName, boolean multiple, String typeClass, String value) {
        super();
        this.typeName = typeName;
        this.multiple = multiple;
        this.typeClass = typeClass;
        this.value = value;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public boolean isMultiple() {
        return multiple;
    }

    public void setMultiple(boolean multiple) {
        this.multiple = multiple;
    }

    public String getTypeClass() {
        return typeClass;
    }

    public void setTypeClass(String typeClass) {
        this.typeClass = typeClass;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("typeName", typeName).append("multiple", multiple).append("typeClass", typeClass).append("value", value).toString();
    }

}
