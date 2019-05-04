
package com.scio.quantum.deduplicator.models.dataset;

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
