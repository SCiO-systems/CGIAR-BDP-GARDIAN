
package com.scio.quantum.harvesters.models.external.dataverse.resource;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class MetadataBlocks {

    @SerializedName("citation")
    @Expose
    private Citation citation;
    @SerializedName("geospatial")
    @Expose
    private Geospatial geospatial;

    /**
     * No args constructor for use in serialization
     * 
     */
    public MetadataBlocks() {
    }

    /**
     * 
     * @param citation
     * @param geospatial
     */
    public MetadataBlocks(Citation citation, Geospatial geospatial) {
        super();
        this.citation = citation;
        this.geospatial = geospatial;
    }

    public Citation getCitation() {
        return citation;
    }

    public void setCitation(Citation citation) {
        this.citation = citation;
    }

    public Geospatial getGeospatial() {
        return geospatial;
    }

    public void setGeospatial(Geospatial geospatial) {
        this.geospatial = geospatial;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("citation", citation).append("geospatial", geospatial).toString();
    }

}
