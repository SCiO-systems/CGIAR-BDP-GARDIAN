
package com.scio.quantum.harvesters.models.external.dataverse.resource;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Value {

    @SerializedName("country")
    @Expose
    private Country country;
    @SerializedName("otherGeographicCoverage")
    @Expose
    private OtherGeographicCoverage otherGeographicCoverage;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Value() {
    }

    /**
     * 
     * @param otherGeographicCoverage
     * @param country
     */
    public Value(Country country, OtherGeographicCoverage otherGeographicCoverage) {
        super();
        this.country = country;
        this.otherGeographicCoverage = otherGeographicCoverage;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public OtherGeographicCoverage getOtherGeographicCoverage() {
        return otherGeographicCoverage;
    }

    public void setOtherGeographicCoverage(OtherGeographicCoverage otherGeographicCoverage) {
        this.otherGeographicCoverage = otherGeographicCoverage;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("country", country).append("otherGeographicCoverage", otherGeographicCoverage).toString();
    }

}
