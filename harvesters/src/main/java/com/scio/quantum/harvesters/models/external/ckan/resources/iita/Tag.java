
package com.scio.quantum.harvesters.models.external.ckan.resources.iita;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Tag {

    @SerializedName("vocabulary_id")
    @Expose
    private Object vocabularyId;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("display_name")
    @Expose
    private String displayName;
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
    public Tag() {
    }

    /**
     * 
     * @param id
     * @param vocabularyId
     * @param name
     * @param state
     * @param displayName
     */
    public Tag(Object vocabularyId, String state, String displayName, String id, String name) {
        super();
        this.vocabularyId = vocabularyId;
        this.state = state;
        this.displayName = displayName;
        this.id = id;
        this.name = name;
    }

    public Object getVocabularyId() {
        return vocabularyId;
    }

    public void setVocabularyId(Object vocabularyId) {
        this.vocabularyId = vocabularyId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
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
        return new ToStringBuilder(this).append("vocabularyId", vocabularyId).append("state", state).append("displayName", displayName).append("id", id).append("name", name).toString();
    }

}
