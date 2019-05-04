
package com.scio.quantum.harvesters.models.external.dataverse.resource;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class File {

    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("label")
    @Expose
    private String label;
    @SerializedName("version")
    @Expose
    private int version;
    @SerializedName("datasetVersionId")
    @Expose
    private int datasetVersionId;
    @SerializedName("dataFile")
    @Expose
    private DataFile dataFile;

    /**
     * No args constructor for use in serialization
     * 
     */
    public File() {
    }

    /**
     * 
     * @param description
     * @param label
     * @param datasetVersionId
     * @param dataFile
     * @param version
     */
    public File(String description, String label, int version, int datasetVersionId, DataFile dataFile) {
        super();
        this.description = description;
        this.label = label;
        this.version = version;
        this.datasetVersionId = datasetVersionId;
        this.dataFile = dataFile;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public int getDatasetVersionId() {
        return datasetVersionId;
    }

    public void setDatasetVersionId(int datasetVersionId) {
        this.datasetVersionId = datasetVersionId;
    }

    public DataFile getDataFile() {
        return dataFile;
    }

    public void setDataFile(DataFile dataFile) {
        this.dataFile = dataFile;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("description", description).append("label", label).append("version", version).append("datasetVersionId", datasetVersionId).append("dataFile", dataFile).toString();
    }

}
