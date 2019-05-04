
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

import java.util.List;

public class ExtractedMetadata{

    @SerializedName("vocabulary")
    @Expose
    private String vocabulary;
    @SerializedName("ready")
    @Expose
    private boolean ready;
    @SerializedName("data")
    @Expose
    private List<Object> data = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ExtractedMetadata() {
    }

    /**
     * 
     * @param vocabulary
     * @param data
     * @param ready
     */
    public ExtractedMetadata(String vocabulary, boolean ready, List<Object> data) {
        super();
        this.vocabulary = vocabulary;
        this.ready = ready;
        this.data = data;
    }

    public String getVocabulary() {
        return vocabulary;
    }

    public void setVocabulary(String vocabulary) {
        this.vocabulary = vocabulary;
    }

    public boolean isReady() {
        return ready;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }

    public List<Object> getData() {
        return data;
    }

    public void setData(List<Object> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("vocabulary", vocabulary).append("ready", ready).append("data", data).toString();
    }

}
