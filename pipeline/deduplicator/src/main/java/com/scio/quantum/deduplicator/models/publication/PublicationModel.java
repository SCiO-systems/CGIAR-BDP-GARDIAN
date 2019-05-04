package com.scio.quantum.deduplicator.models.publication;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class PublicationModel {

    @SerializedName("PubMetadata")
    @Expose
    private PubMetadata pubMetadata;

    /**
     * No args constructor for use in serialization
     *
     */
    public PublicationModel() {
    }

    /**
     *
     * @param pubMetadata
     */
    public PublicationModel(PubMetadata pubMetadata,String QuantumId) {
        super();
        this.pubMetadata = pubMetadata;
    }

    public PubMetadata getPubMetadata() {
        return pubMetadata;
    }

    public void setPubMetadata(PubMetadata pubMetadata) {
        this.pubMetadata = pubMetadata;
    }



    @Override
    public String toString() {
        return new ToStringBuilder(this).append("pubMetadata", pubMetadata).toString();
    }

}