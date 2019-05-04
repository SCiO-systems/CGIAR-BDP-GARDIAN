
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

public class DatasetModel {

    @SerializedName("DatasetMetadata")
    @Expose
    private DatasetMetadata datasetMetadata;

    /**
     * No args constructor for use in serialization
     * 
     */
    public DatasetModel() {
    }

    /**
     * 
     * @param datasetMetadata
     */
    public DatasetModel(DatasetMetadata datasetMetadata) {
        super();
        this.datasetMetadata = datasetMetadata;
    }

    public DatasetMetadata getDatasetMetadata() {
        return datasetMetadata;
    }

    public void setDatasetMetadata(DatasetMetadata datasetMetadata) {
        this.datasetMetadata = datasetMetadata;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("datasetMetadata", datasetMetadata).toString();
    }

}
