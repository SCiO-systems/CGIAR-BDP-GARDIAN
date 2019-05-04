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

//TODO: Add abstract extraction {"extraction"[{ontology:"", terms:{term,freq}}], {ready: true}}
//TODO: Add main text extraction {"extraction"[{ontology:"", terms:{term,freq}}], {ready: true}}
//TODO: Status update
//TODO: Add FAIR Computation


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