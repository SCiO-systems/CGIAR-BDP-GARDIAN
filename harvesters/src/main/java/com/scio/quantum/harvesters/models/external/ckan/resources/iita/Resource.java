
package com.scio.quantum.harvesters.models.external.ckan.resources.iita;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Resource {

    @SerializedName("mimetype")
    @Expose
    private Object mimetype;
    @SerializedName("cache_url")
    @Expose
    private Object cacheUrl;
    @SerializedName("hash")
    @Expose
    private String hash;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("format")
    @Expose
    private String format;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("datastore_active")
    @Expose
    private boolean datastoreActive;
    @SerializedName("cache_last_updated")
    @Expose
    private Object cacheLastUpdated;
    @SerializedName("package_id")
    @Expose
    private String packageId;
    @SerializedName("created")
    @Expose
    private String created;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("mimetype_inner")
    @Expose
    private Object mimetypeInner;
    @SerializedName("last_modified")
    @Expose
    private Object lastModified;
    @SerializedName("position")
    @Expose
    private int position;
    @SerializedName("revision_id")
    @Expose
    private String revisionId;
    @SerializedName("url_type")
    @Expose
    private Object urlType;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("resource_type")
    @Expose
    private Object resourceType;
    @SerializedName("size")
    @Expose
    private Object size;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Resource() {
    }

    /**
     * 
     * @param position
     * @param datastoreActive
     * @param lastModified
     * @param hash
     * @param state
     * @param cacheUrl
     * @param packageId
     * @param format
     * @param urlType
     * @param revisionId
     * @param url
     * @param size
     * @param resourceType
     * @param id
     * @param mimetypeInner
     * @param cacheLastUpdated
     * @param mimetype
     * @param created
     * @param description
     * @param name
     */
    public Resource(Object mimetype, Object cacheUrl, String hash, String description, String name, String format, String url, boolean datastoreActive, Object cacheLastUpdated, String packageId, String created, String state, Object mimetypeInner, Object lastModified, int position, String revisionId, Object urlType, String id, Object resourceType, Object size) {
        super();
        this.mimetype = mimetype;
        this.cacheUrl = cacheUrl;
        this.hash = hash;
        this.description = description;
        this.name = name;
        this.format = format;
        this.url = url;
        this.datastoreActive = datastoreActive;
        this.cacheLastUpdated = cacheLastUpdated;
        this.packageId = packageId;
        this.created = created;
        this.state = state;
        this.mimetypeInner = mimetypeInner;
        this.lastModified = lastModified;
        this.position = position;
        this.revisionId = revisionId;
        this.urlType = urlType;
        this.id = id;
        this.resourceType = resourceType;
        this.size = size;
    }

    public Object getMimetype() {
        return mimetype;
    }

    public void setMimetype(Object mimetype) {
        this.mimetype = mimetype;
    }

    public Object getCacheUrl() {
        return cacheUrl;
    }

    public void setCacheUrl(Object cacheUrl) {
        this.cacheUrl = cacheUrl;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isDatastoreActive() {
        return datastoreActive;
    }

    public void setDatastoreActive(boolean datastoreActive) {
        this.datastoreActive = datastoreActive;
    }

    public Object getCacheLastUpdated() {
        return cacheLastUpdated;
    }

    public void setCacheLastUpdated(Object cacheLastUpdated) {
        this.cacheLastUpdated = cacheLastUpdated;
    }

    public String getPackageId() {
        return packageId;
    }

    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Object getMimetypeInner() {
        return mimetypeInner;
    }

    public void setMimetypeInner(Object mimetypeInner) {
        this.mimetypeInner = mimetypeInner;
    }

    public Object getLastModified() {
        return lastModified;
    }

    public void setLastModified(Object lastModified) {
        this.lastModified = lastModified;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getRevisionId() {
        return revisionId;
    }

    public void setRevisionId(String revisionId) {
        this.revisionId = revisionId;
    }

    public Object getUrlType() {
        return urlType;
    }

    public void setUrlType(Object urlType) {
        this.urlType = urlType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Object getResourceType() {
        return resourceType;
    }

    public void setResourceType(Object resourceType) {
        this.resourceType = resourceType;
    }

    public Object getSize() {
        return size;
    }

    public void setSize(Object size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("mimetype", mimetype).append("cacheUrl", cacheUrl).append("hash", hash).append("description", description).append("name", name).append("format", format).append("url", url).append("datastoreActive", datastoreActive).append("cacheLastUpdated", cacheLastUpdated).append("packageId", packageId).append("created", created).append("state", state).append("mimetypeInner", mimetypeInner).append("lastModified", lastModified).append("position", position).append("revisionId", revisionId).append("urlType", urlType).append("id", id).append("resourceType", resourceType).append("size", size).toString();
    }

}
