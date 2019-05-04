
package com.scio.quantum.harvesters.models.external.dataverse.resource;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class DatasetVersion {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("versionNumber")
    @Expose
    private int versionNumber;
    @SerializedName("versionMinorNumber")
    @Expose
    private int versionMinorNumber;
    @SerializedName("versionState")
    @Expose
    private String versionState;
    @SerializedName("versionNote")
    @Expose
    private String versionNote;
    @SerializedName("productionDate")
    @Expose
    private String productionDate;
    @SerializedName("lastUpdateTime")
    @Expose
    private String lastUpdateTime;
    @SerializedName("releaseTime")
    @Expose
    private String releaseTime;
    @SerializedName("createTime")
    @Expose
    private String createTime;
    @SerializedName("license")
    @Expose
    private String license;
    @SerializedName("termsOfUse")
    @Expose
    private String termsOfUse;
    @SerializedName("metadataBlocks")
    @Expose
    private MetadataBlocks metadataBlocks;
    @SerializedName("files")
    @Expose
    private List<File> files = null;
    @SerializedName("citation")
    @Expose
    private String citation;

    /**
     * No args constructor for use in serialization
     * 
     */
    public DatasetVersion() {
    }

    /**
     * 
     * @param termsOfUse
     * @param citation
     * @param files
     * @param createTime
     * @param versionNote
     * @param lastUpdateTime
     * @param versionMinorNumber
     * @param versionState
     * @param productionDate
     * @param id
     * @param metadataBlocks
     * @param releaseTime
     * @param license
     * @param versionNumber
     */
    public DatasetVersion(int id, int versionNumber, int versionMinorNumber, String versionState, String versionNote, String productionDate, String lastUpdateTime, String releaseTime, String createTime, String license, String termsOfUse, MetadataBlocks metadataBlocks, List<File> files, String citation) {
        super();
        this.id = id;
        this.versionNumber = versionNumber;
        this.versionMinorNumber = versionMinorNumber;
        this.versionState = versionState;
        this.versionNote = versionNote;
        this.productionDate = productionDate;
        this.lastUpdateTime = lastUpdateTime;
        this.releaseTime = releaseTime;
        this.createTime = createTime;
        this.license = license;
        this.termsOfUse = termsOfUse;
        this.metadataBlocks = metadataBlocks;
        this.files = files;
        this.citation = citation;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVersionNumber() {
        return versionNumber;
    }

    public void setVersionNumber(int versionNumber) {
        this.versionNumber = versionNumber;
    }

    public int getVersionMinorNumber() {
        return versionMinorNumber;
    }

    public void setVersionMinorNumber(int versionMinorNumber) {
        this.versionMinorNumber = versionMinorNumber;
    }

    public String getVersionState() {
        return versionState;
    }

    public void setVersionState(String versionState) {
        this.versionState = versionState;
    }

    public String getVersionNote() {
        return versionNote;
    }

    public void setVersionNote(String versionNote) {
        this.versionNote = versionNote;
    }

    public String getProductionDate() {
        return productionDate;
    }

    public void setProductionDate(String productionDate) {
        this.productionDate = productionDate;
    }

    public String getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(String lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(String releaseTime) {
        this.releaseTime = releaseTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public String getTermsOfUse() {
        return termsOfUse;
    }

    public void setTermsOfUse(String termsOfUse) {
        this.termsOfUse = termsOfUse;
    }

    public MetadataBlocks getMetadataBlocks() {
        return metadataBlocks;
    }

    public void setMetadataBlocks(MetadataBlocks metadataBlocks) {
        this.metadataBlocks = metadataBlocks;
    }

    public List<File> getFiles() {
        return files;
    }

    public void setFiles(List<File> files) {
        this.files = files;
    }

    public String getCitation() {
        return citation;
    }

    public void setCitation(String citation) {
        this.citation = citation;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("id", id).append("versionNumber", versionNumber).append("versionMinorNumber", versionMinorNumber).append("versionState", versionState).append("versionNote", versionNote).append("productionDate", productionDate).append("lastUpdateTime", lastUpdateTime).append("releaseTime", releaseTime).append("createTime", createTime).append("license", license).append("termsOfUse", termsOfUse).append("metadataBlocks", metadataBlocks).append("files", files).append("citation", citation).toString();
    }

}
