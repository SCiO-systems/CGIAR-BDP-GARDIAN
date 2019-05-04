
package com.scio.quantum.harvesters.models.external.ckan.resources.iita;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Organization {

    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("created")
    @Expose
    private String created;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("is_organization")
    @Expose
    private boolean isOrganization;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("image_url")
    @Expose
    private String imageUrl;
    @SerializedName("revision_id")
    @Expose
    private String revisionId;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("approval_status")
    @Expose
    private String approvalStatus;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Organization() {
    }

    /**
     * 
     * @param id
     * @param title
     * @param isOrganization
     * @param imageUrl
     * @param created
     * @param description
     * @param approvalStatus
     * @param name
     * @param state
     * @param type
     * @param revisionId
     */
    public Organization(String description, String created, String title, String name, boolean isOrganization, String state, String imageUrl, String revisionId, String type, String id, String approvalStatus) {
        super();
        this.description = description;
        this.created = created;
        this.title = title;
        this.name = name;
        this.isOrganization = isOrganization;
        this.state = state;
        this.imageUrl = imageUrl;
        this.revisionId = revisionId;
        this.type = type;
        this.id = id;
        this.approvalStatus = approvalStatus;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isIsOrganization() {
        return isOrganization;
    }

    public void setIsOrganization(boolean isOrganization) {
        this.isOrganization = isOrganization;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getRevisionId() {
        return revisionId;
    }

    public void setRevisionId(String revisionId) {
        this.revisionId = revisionId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("description", description).append("created", created).append("title", title).append("name", name).append("isOrganization", isOrganization).append("state", state).append("imageUrl", imageUrl).append("revisionId", revisionId).append("type", type).append("id", id).append("approvalStatus", approvalStatus).toString();
    }

}
