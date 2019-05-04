
package com.scio.quantum.harvesters.models.external.ckan.resources.ilri;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Organization {

    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("created")
    @Expose
    private String created;
    @SerializedName("approval_status")
    @Expose
    private String approvalStatus;
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
    @SerializedName("name")
    @Expose
    private String name;

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
     * @param name
     * @param approvalStatus
     * @param state
     * @param type
     * @param revisionId
     */
    public Organization(String description, String title, String created, String approvalStatus, boolean isOrganization, String state, String imageUrl, String revisionId, String type, String id, String name) {
        super();
        this.description = description;
        this.title = title;
        this.created = created;
        this.approvalStatus = approvalStatus;
        this.isOrganization = isOrganization;
        this.state = state;
        this.imageUrl = imageUrl;
        this.revisionId = revisionId;
        this.type = type;
        this.id = id;
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("description", description).append("title", title).append("created", created).append("approvalStatus", approvalStatus).append("isOrganization", isOrganization).append("state", state).append("imageUrl", imageUrl).append("revisionId", revisionId).append("type", type).append("id", id).append("name", name).toString();
    }

}
