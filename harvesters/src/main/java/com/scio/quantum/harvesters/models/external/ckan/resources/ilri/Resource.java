
package com.scio.quantum.harvesters.models.external.ckan.resources.ilri;

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
    @SerializedName("resource_description")
    @Expose
    private String resourceDescription;
    @SerializedName("hash")
    @Expose
    private String hash;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("cache_last_updated")
    @Expose
    private Object cacheLastUpdated;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("format")
    @Expose
    private String format;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("created")
    @Expose
    private String created;
    @SerializedName("package_id")
    @Expose
    private String packageId;
    @SerializedName("mimetype_inner")
    @Expose
    private Object mimetypeInner;
    @SerializedName("url_type")
    @Expose
    private Object urlType;
    @SerializedName("last_modified")
    @Expose
    private Object lastModified;
    @SerializedName("position")
    @Expose
    private int position;
    @SerializedName("revision_id")
    @Expose
    private String revisionId;
    @SerializedName("size")
    @Expose
    private Object size;
    @SerializedName("ILRIResourceAccess")
    @Expose
    private String iLRIResourceAccess;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("resource_type")
    @Expose
    private Object resourceType;
    @SerializedName("name")
    @Expose
    private String name;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Resource() {
    }

    /**
     * 
     * @param position
     * @param lastModified
     * @param hash
     * @param state
     * @param cacheUrl
     * @param packageId
     * @param format
     * @param revisionId
     * @param urlType
     * @param url
     * @param resourceType
     * @param size
     * @param id
     * @param mimetypeInner
     * @param cacheLastUpdated
     * @param iLRIResourceAccess
     * @param mimetype
     * @param created
     * @param description
     * @param name
     * @param resourceDescription
     */
    public Resource(Object mimetype, Object cacheUrl, String resourceDescription, String hash, String description, Object cacheLastUpdated, String url, String format, String state, String created, String packageId, Object mimetypeInner, Object urlType, Object lastModified, int position, String revisionId, Object size, String iLRIResourceAccess, String id, Object resourceType, String name) {
        super();
        this.mimetype = mimetype;
        this.cacheUrl = cacheUrl;
        this.resourceDescription = resourceDescription;
        this.hash = hash;
        this.description = description;
        this.cacheLastUpdated = cacheLastUpdated;
        this.url = url;
        this.format = format;
        this.state = state;
        this.created = created;
        this.packageId = packageId;
        this.mimetypeInner = mimetypeInner;
        this.urlType = urlType;
        this.lastModified = lastModified;
        this.position = position;
        this.revisionId = revisionId;
        this.size = size;
        this.iLRIResourceAccess = iLRIResourceAccess;
        this.id = id;
        this.resourceType = resourceType;
        this.name = name;
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

    public String getResourceDescription() {
        return resourceDescription;
    }

    public void setResourceDescription(String resourceDescription) {
        this.resourceDescription = resourceDescription;
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

    public Object getCacheLastUpdated() {
        return cacheLastUpdated;
    }

    public void setCacheLastUpdated(Object cacheLastUpdated) {
        this.cacheLastUpdated = cacheLastUpdated;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getPackageId() {
        return packageId;
    }

    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }

    public Object getMimetypeInner() {
        return mimetypeInner;
    }

    public void setMimetypeInner(Object mimetypeInner) {
        this.mimetypeInner = mimetypeInner;
    }

    public Object getUrlType() {
        return urlType;
    }

    public void setUrlType(Object urlType) {
        this.urlType = urlType;
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

    public Object getSize() {
        return size;
    }

    public void setSize(Object size) {
        this.size = size;
    }

    public String getILRIResourceAccess() {
        return iLRIResourceAccess;
    }

    public void setILRIResourceAccess(String iLRIResourceAccess) {
        this.iLRIResourceAccess = iLRIResourceAccess;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("mimetype", mimetype).append("cacheUrl", cacheUrl).append("resourceDescription", resourceDescription).append("hash", hash).append("description", description).append("cacheLastUpdated", cacheLastUpdated).append("url", url).append("format", format).append("state", state).append("created", created).append("packageId", packageId).append("mimetypeInner", mimetypeInner).append("urlType", urlType).append("lastModified", lastModified).append("position", position).append("revisionId", revisionId).append("size", size).append("iLRIResourceAccess", iLRIResourceAccess).append("id", id).append("resourceType", resourceType).append("name", name).toString();
    }

}
