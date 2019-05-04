
package com.scio.quantum.harvesters.models.external.ckan.resources.ilri;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Result {

    @SerializedName("license_title")
    @Expose
    private String licenseTitle;
    @SerializedName("maintainer")
    @Expose
    private Object maintainer;
    @SerializedName("ILRI_actyregions")
    @Expose
    private List<String> iLRIActyregions = null;
    @SerializedName("relationships_as_object")
    @Expose
    private List<Object> relationshipsAsObject = null;
    @SerializedName("private")
    @Expose
    private boolean _private;
    @SerializedName("maintainer_email")
    @Expose
    private Object maintainerEmail;
    @SerializedName("ILRI_prjcountries")
    @Expose
    private String iLRIPrjcountries;
    @SerializedName("ILRI_actyproduct")
    @Expose
    private List<String> iLRIActyproduct = null;
    @SerializedName("ILRI_actystaff")
    @Expose
    private String iLRIActystaff;
    @SerializedName("ILRI_prjedate")
    @Expose
    private String iLRIPrjedate;
    @SerializedName("ILRI_actycustodian")
    @Expose
    private String iLRIActycustodian;
    @SerializedName("ILRI_actyboundboxcenter")
    @Expose
    private String iLRIActyboundboxcenter;
    @SerializedName("ILRI_prjabstract")
    @Expose
    private String iLRIPrjabstract;
    @SerializedName("ILRI_actycitation")
    @Expose
    private String iLRIActycitation;
    @SerializedName("ILRI_actymapzoom")
    @Expose
    private String iLRIActymapzoom;
    @SerializedName("metadata_created")
    @Expose
    private String metadataCreated;
    @SerializedName("ILRI_actydatavailable")
    @Expose
    private String iLRIActydatavailable;
    @SerializedName("ILRI_prjstaff")
    @Expose
    private String iLRIPrjstaff;
    @SerializedName("ILRI_prjspecies")
    @Expose
    private String iLRIPrjspecies;
    @SerializedName("ILRI_actymapextent")
    @Expose
    private String iLRIActymapextent;
    @SerializedName("metadata_modified")
    @Expose
    private String metadataModified;
    @SerializedName("ILRI_prjwebsite")
    @Expose
    private String iLRIPrjwebsite;
    @SerializedName("author_email")
    @Expose
    private Object authorEmail;
    @SerializedName("ILRI_actydatecollectedend")
    @Expose
    private String iLRIActydatecollectedend;
    @SerializedName("ILRI_prjgrant")
    @Expose
    private String iLRIPrjgrant;
    @SerializedName("ILRI_actycountries")
    @Expose
    private List<String> iLRIActycountries = null;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("version")
    @Expose
    private Object version;
    @SerializedName("author")
    @Expose
    private Object author;
    @SerializedName("ILRI_actycustodianemail")
    @Expose
    private String iLRIActycustodianemail;
    @SerializedName("creator_user_id")
    @Expose
    private String creatorUserId;
    @SerializedName("ILRI_prjdonor")
    @Expose
    private String iLRIPrjdonor;
    @SerializedName("ILRI_prjpiemail")
    @Expose
    private String iLRIPrjpiemail;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("resources")
    @Expose
    private List<Resource> resources = null;
    @SerializedName("ILRI_prjregions")
    @Expose
    private String iLRIPrjregions;
    @SerializedName("ILRI_actycontactperson")
    @Expose
    private String iLRIActycontactperson;
    @SerializedName("ILRI_actypartners")
    @Expose
    private String iLRIActypartners;
    @SerializedName("tags")
    @Expose
    private List<Tag> tags = null;
    @SerializedName("ILRI_actyotheruse")
    @Expose
    private String iLRIActyotheruse;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("ILRI_actyboundbox")
    @Expose
    private String iLRIActyboundbox;
    @SerializedName("ILRI_actydatecollected")
    @Expose
    private String iLRIActydatecollected;
    @SerializedName("ILRI_actypiemail")
    @Expose
    private String iLRIActypiemail;
    @SerializedName("relationships_as_subject")
    @Expose
    private List<Object> relationshipsAsSubject = null;
    @SerializedName("ILRI_prjtitle")
    @Expose
    private String iLRIPrjtitle;
    @SerializedName("num_resources")
    @Expose
    private int numResources;
    @SerializedName("license_id")
    @Expose
    private String licenseId;
    @SerializedName("ILRI_actypi")
    @Expose
    private String iLRIActypi;
    @SerializedName("ILRI_prjsubjects")
    @Expose
    private List<String> iLRIPrjsubjects = null;
    @SerializedName("groups")
    @Expose
    private List<Object> groups = null;
    @SerializedName("num_tags")
    @Expose
    private int numTags;
    @SerializedName("organization")
    @Expose
    private Organization organization;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("isopen")
    @Expose
    private boolean isopen;
    @SerializedName("ILRI_actyipownership")
    @Expose
    private String iLRIActyipownership;
    @SerializedName("ILRI_actynatlevel")
    @Expose
    private String iLRIActynatlevel;
    @SerializedName("owner_org")
    @Expose
    private String ownerOrg;
    @SerializedName("ILRI_actycitationacknowledge")
    @Expose
    private String iLRIActycitationacknowledge;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("ILRI_prjpi")
    @Expose
    private String iLRIPrjpi;
    @SerializedName("license_url")
    @Expose
    private String licenseUrl;
    @SerializedName("ILRI_actyspecies")
    @Expose
    private List<String> iLRIActyspecies = null;
    @SerializedName("ILRI_prjsdate")
    @Expose
    private String iLRIPrjsdate;
    @SerializedName("revision_id")
    @Expose
    private String revisionId;
    @SerializedName("ILRI_prjpartners")
    @Expose
    private String iLRIPrjpartners;
    @SerializedName("notes")
    @Expose
    private String notes;
    @SerializedName("ILRI_actycontactemail")
    @Expose
    private String iLRIActycontactemail;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Result() {
    }

    /**
     * 
     * @param licenseId
     * @param iLRIPrjgrant
     * @param resources
     * @param iLRIPrjwebsite
     * @param iLRIActycustodianemail
     * @param metadataCreated
     * @param iLRIActyboundbox
     * @param iLRIActypartners
     * @param type
     * @param revisionId
     * @param numTags
     * @param iLRIActyipownership
     * @param iLRIActypiemail
     * @param iLRIActymapextent
     * @param maintainerEmail
     * @param _private
     * @param iLRIActycontactperson
     * @param iLRIActyregions
     * @param iLRIPrjpartners
     * @param iLRIActypi
     * @param iLRIActydatavailable
     * @param iLRIActyboundboxcenter
     * @param groups
     * @param metadataModified
     * @param authorEmail
     * @param iLRIActycitation
     * @param iLRIActydatecollectedend
     * @param iLRIPrjregions
     * @param url
     * @param iLRIActycountries
     * @param iLRIPrjtitle
     * @param iLRIActymapzoom
     * @param iLRIPrjedate
     * @param iLRIPrjcountries
     * @param iLRIPrjabstract
     * @param licenseUrl
     * @param iLRIActydatecollected
     * @param iLRIPrjdonor
     * @param iLRIActynatlevel
     * @param numResources
     * @param state
     * @param ownerOrg
     * @param iLRIPrjspecies
     * @param version
     * @param id
     * @param iLRIPrjpiemail
     * @param iLRIActystaff
     * @param iLRIActycustodian
     * @param author
     * @param organization
     * @param creatorUserId
     * @param title
     * @param name
     * @param iLRIActyproduct
     * @param tags
     * @param iLRIPrjpi
     * @param isopen
     * @param iLRIActycontactemail
     * @param relationshipsAsSubject
     * @param iLRIPrjsdate
     * @param maintainer
     * @param iLRIActyspecies
     * @param iLRIPrjsubjects
     * @param licenseTitle
     * @param iLRIActycitationacknowledge
     * @param iLRIPrjstaff
     * @param notes
     * @param relationshipsAsObject
     * @param iLRIActyotheruse
     */
    public Result(String licenseTitle, Object maintainer, List<String> iLRIActyregions, List<Object> relationshipsAsObject, boolean _private, Object maintainerEmail, String iLRIPrjcountries, List<String> iLRIActyproduct, String iLRIActystaff, String iLRIPrjedate, String iLRIActycustodian, String iLRIActyboundboxcenter, String iLRIPrjabstract, String iLRIActycitation, String iLRIActymapzoom, String metadataCreated, String iLRIActydatavailable, String iLRIPrjstaff, String iLRIPrjspecies, String iLRIActymapextent, String metadataModified, String iLRIPrjwebsite, Object authorEmail, String iLRIActydatecollectedend, String iLRIPrjgrant, List<String> iLRIActycountries, String id, String state, Object version, Object author, String iLRIActycustodianemail, String creatorUserId, String iLRIPrjdonor, String iLRIPrjpiemail, String type, List<Resource> resources, String iLRIPrjregions, String iLRIActycontactperson, String iLRIActypartners, List<Tag> tags, String iLRIActyotheruse, String title, String iLRIActyboundbox, String iLRIActydatecollected, String iLRIActypiemail, List<Object> relationshipsAsSubject, String iLRIPrjtitle, int numResources, String licenseId, String iLRIActypi, List<String> iLRIPrjsubjects, List<Object> groups, int numTags, Organization organization, String name, boolean isopen, String iLRIActyipownership, String iLRIActynatlevel, String ownerOrg, String iLRIActycitationacknowledge, String url, String iLRIPrjpi, String licenseUrl, List<String> iLRIActyspecies, String iLRIPrjsdate, String revisionId, String iLRIPrjpartners, String notes, String iLRIActycontactemail) {
        super();
        this.licenseTitle = licenseTitle;
        this.maintainer = maintainer;
        this.iLRIActyregions = iLRIActyregions;
        this.relationshipsAsObject = relationshipsAsObject;
        this._private = _private;
        this.maintainerEmail = maintainerEmail;
        this.iLRIPrjcountries = iLRIPrjcountries;
        this.iLRIActyproduct = iLRIActyproduct;
        this.iLRIActystaff = iLRIActystaff;
        this.iLRIPrjedate = iLRIPrjedate;
        this.iLRIActycustodian = iLRIActycustodian;
        this.iLRIActyboundboxcenter = iLRIActyboundboxcenter;
        this.iLRIPrjabstract = iLRIPrjabstract;
        this.iLRIActycitation = iLRIActycitation;
        this.iLRIActymapzoom = iLRIActymapzoom;
        this.metadataCreated = metadataCreated;
        this.iLRIActydatavailable = iLRIActydatavailable;
        this.iLRIPrjstaff = iLRIPrjstaff;
        this.iLRIPrjspecies = iLRIPrjspecies;
        this.iLRIActymapextent = iLRIActymapextent;
        this.metadataModified = metadataModified;
        this.iLRIPrjwebsite = iLRIPrjwebsite;
        this.authorEmail = authorEmail;
        this.iLRIActydatecollectedend = iLRIActydatecollectedend;
        this.iLRIPrjgrant = iLRIPrjgrant;
        this.iLRIActycountries = iLRIActycountries;
        this.id = id;
        this.state = state;
        this.version = version;
        this.author = author;
        this.iLRIActycustodianemail = iLRIActycustodianemail;
        this.creatorUserId = creatorUserId;
        this.iLRIPrjdonor = iLRIPrjdonor;
        this.iLRIPrjpiemail = iLRIPrjpiemail;
        this.type = type;
        this.resources = resources;
        this.iLRIPrjregions = iLRIPrjregions;
        this.iLRIActycontactperson = iLRIActycontactperson;
        this.iLRIActypartners = iLRIActypartners;
        this.tags = tags;
        this.iLRIActyotheruse = iLRIActyotheruse;
        this.title = title;
        this.iLRIActyboundbox = iLRIActyboundbox;
        this.iLRIActydatecollected = iLRIActydatecollected;
        this.iLRIActypiemail = iLRIActypiemail;
        this.relationshipsAsSubject = relationshipsAsSubject;
        this.iLRIPrjtitle = iLRIPrjtitle;
        this.numResources = numResources;
        this.licenseId = licenseId;
        this.iLRIActypi = iLRIActypi;
        this.iLRIPrjsubjects = iLRIPrjsubjects;
        this.groups = groups;
        this.numTags = numTags;
        this.organization = organization;
        this.name = name;
        this.isopen = isopen;
        this.iLRIActyipownership = iLRIActyipownership;
        this.iLRIActynatlevel = iLRIActynatlevel;
        this.ownerOrg = ownerOrg;
        this.iLRIActycitationacknowledge = iLRIActycitationacknowledge;
        this.url = url;
        this.iLRIPrjpi = iLRIPrjpi;
        this.licenseUrl = licenseUrl;
        this.iLRIActyspecies = iLRIActyspecies;
        this.iLRIPrjsdate = iLRIPrjsdate;
        this.revisionId = revisionId;
        this.iLRIPrjpartners = iLRIPrjpartners;
        this.notes = notes;
        this.iLRIActycontactemail = iLRIActycontactemail;
    }

    public String getLicenseTitle() {
        return licenseTitle;
    }

    public void setLicenseTitle(String licenseTitle) {
        this.licenseTitle = licenseTitle;
    }

    public Object getMaintainer() {
        return maintainer;
    }

    public void setMaintainer(Object maintainer) {
        this.maintainer = maintainer;
    }

    public List<String> getILRIActyregions() {
        return iLRIActyregions;
    }

    public void setILRIActyregions(List<String> iLRIActyregions) {
        this.iLRIActyregions = iLRIActyregions;
    }

    public List<Object> getRelationshipsAsObject() {
        return relationshipsAsObject;
    }

    public void setRelationshipsAsObject(List<Object> relationshipsAsObject) {
        this.relationshipsAsObject = relationshipsAsObject;
    }

    public boolean isPrivate() {
        return _private;
    }

    public void setPrivate(boolean _private) {
        this._private = _private;
    }

    public Object getMaintainerEmail() {
        return maintainerEmail;
    }

    public void setMaintainerEmail(Object maintainerEmail) {
        this.maintainerEmail = maintainerEmail;
    }

    public String getILRIPrjcountries() {
        return iLRIPrjcountries;
    }

    public void setILRIPrjcountries(String iLRIPrjcountries) {
        this.iLRIPrjcountries = iLRIPrjcountries;
    }

    public List<String> getILRIActyproduct() {
        return iLRIActyproduct;
    }

    public void setILRIActyproduct(List<String> iLRIActyproduct) {
        this.iLRIActyproduct = iLRIActyproduct;
    }

    public String getILRIActystaff() {
        return iLRIActystaff;
    }

    public void setILRIActystaff(String iLRIActystaff) {
        this.iLRIActystaff = iLRIActystaff;
    }

    public String getILRIPrjedate() {
        return iLRIPrjedate;
    }

    public void setILRIPrjedate(String iLRIPrjedate) {
        this.iLRIPrjedate = iLRIPrjedate;
    }

    public String getILRIActycustodian() {
        return iLRIActycustodian;
    }

    public void setILRIActycustodian(String iLRIActycustodian) {
        this.iLRIActycustodian = iLRIActycustodian;
    }

    public String getILRIActyboundboxcenter() {
        return iLRIActyboundboxcenter;
    }

    public void setILRIActyboundboxcenter(String iLRIActyboundboxcenter) {
        this.iLRIActyboundboxcenter = iLRIActyboundboxcenter;
    }

    public String getILRIPrjabstract() {
        return iLRIPrjabstract;
    }

    public void setILRIPrjabstract(String iLRIPrjabstract) {
        this.iLRIPrjabstract = iLRIPrjabstract;
    }

    public String getILRIActycitation() {
        return iLRIActycitation;
    }

    public void setILRIActycitation(String iLRIActycitation) {
        this.iLRIActycitation = iLRIActycitation;
    }

    public String getILRIActymapzoom() {
        return iLRIActymapzoom;
    }

    public void setILRIActymapzoom(String iLRIActymapzoom) {
        this.iLRIActymapzoom = iLRIActymapzoom;
    }

    public String getMetadataCreated() {
        return metadataCreated;
    }

    public void setMetadataCreated(String metadataCreated) {
        this.metadataCreated = metadataCreated;
    }

    public String getILRIActydatavailable() {
        return iLRIActydatavailable;
    }

    public void setILRIActydatavailable(String iLRIActydatavailable) {
        this.iLRIActydatavailable = iLRIActydatavailable;
    }

    public String getILRIPrjstaff() {
        return iLRIPrjstaff;
    }

    public void setILRIPrjstaff(String iLRIPrjstaff) {
        this.iLRIPrjstaff = iLRIPrjstaff;
    }

    public String getILRIPrjspecies() {
        return iLRIPrjspecies;
    }

    public void setILRIPrjspecies(String iLRIPrjspecies) {
        this.iLRIPrjspecies = iLRIPrjspecies;
    }

    public String getILRIActymapextent() {
        return iLRIActymapextent;
    }

    public void setILRIActymapextent(String iLRIActymapextent) {
        this.iLRIActymapextent = iLRIActymapextent;
    }

    public String getMetadataModified() {
        return metadataModified;
    }

    public void setMetadataModified(String metadataModified) {
        this.metadataModified = metadataModified;
    }

    public String getILRIPrjwebsite() {
        return iLRIPrjwebsite;
    }

    public void setILRIPrjwebsite(String iLRIPrjwebsite) {
        this.iLRIPrjwebsite = iLRIPrjwebsite;
    }

    public Object getAuthorEmail() {
        return authorEmail;
    }

    public void setAuthorEmail(Object authorEmail) {
        this.authorEmail = authorEmail;
    }

    public String getILRIActydatecollectedend() {
        return iLRIActydatecollectedend;
    }

    public void setILRIActydatecollectedend(String iLRIActydatecollectedend) {
        this.iLRIActydatecollectedend = iLRIActydatecollectedend;
    }

    public String getILRIPrjgrant() {
        return iLRIPrjgrant;
    }

    public void setILRIPrjgrant(String iLRIPrjgrant) {
        this.iLRIPrjgrant = iLRIPrjgrant;
    }

    public List<String> getILRIActycountries() {
        return iLRIActycountries;
    }

    public void setILRIActycountries(List<String> iLRIActycountries) {
        this.iLRIActycountries = iLRIActycountries;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Object getVersion() {
        return version;
    }

    public void setVersion(Object version) {
        this.version = version;
    }

    public Object getAuthor() {
        return author;
    }

    public void setAuthor(Object author) {
        this.author = author;
    }

    public String getILRIActycustodianemail() {
        return iLRIActycustodianemail;
    }

    public void setILRIActycustodianemail(String iLRIActycustodianemail) {
        this.iLRIActycustodianemail = iLRIActycustodianemail;
    }

    public String getCreatorUserId() {
        return creatorUserId;
    }

    public void setCreatorUserId(String creatorUserId) {
        this.creatorUserId = creatorUserId;
    }

    public String getILRIPrjdonor() {
        return iLRIPrjdonor;
    }

    public void setILRIPrjdonor(String iLRIPrjdonor) {
        this.iLRIPrjdonor = iLRIPrjdonor;
    }

    public String getILRIPrjpiemail() {
        return iLRIPrjpiemail;
    }

    public void setILRIPrjpiemail(String iLRIPrjpiemail) {
        this.iLRIPrjpiemail = iLRIPrjpiemail;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Resource> getResources() {
        return resources;
    }

    public void setResources(List<Resource> resources) {
        this.resources = resources;
    }

    public String getILRIPrjregions() {
        return iLRIPrjregions;
    }

    public void setILRIPrjregions(String iLRIPrjregions) {
        this.iLRIPrjregions = iLRIPrjregions;
    }

    public String getILRIActycontactperson() {
        return iLRIActycontactperson;
    }

    public void setILRIActycontactperson(String iLRIActycontactperson) {
        this.iLRIActycontactperson = iLRIActycontactperson;
    }

    public String getILRIActypartners() {
        return iLRIActypartners;
    }

    public void setILRIActypartners(String iLRIActypartners) {
        this.iLRIActypartners = iLRIActypartners;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public String getILRIActyotheruse() {
        return iLRIActyotheruse;
    }

    public void setILRIActyotheruse(String iLRIActyotheruse) {
        this.iLRIActyotheruse = iLRIActyotheruse;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getILRIActyboundbox() {
        return iLRIActyboundbox;
    }

    public void setILRIActyboundbox(String iLRIActyboundbox) {
        this.iLRIActyboundbox = iLRIActyboundbox;
    }

    public String getILRIActydatecollected() {
        return iLRIActydatecollected;
    }

    public void setILRIActydatecollected(String iLRIActydatecollected) {
        this.iLRIActydatecollected = iLRIActydatecollected;
    }

    public String getILRIActypiemail() {
        return iLRIActypiemail;
    }

    public void setILRIActypiemail(String iLRIActypiemail) {
        this.iLRIActypiemail = iLRIActypiemail;
    }

    public List<Object> getRelationshipsAsSubject() {
        return relationshipsAsSubject;
    }

    public void setRelationshipsAsSubject(List<Object> relationshipsAsSubject) {
        this.relationshipsAsSubject = relationshipsAsSubject;
    }

    public String getILRIPrjtitle() {
        return iLRIPrjtitle;
    }

    public void setILRIPrjtitle(String iLRIPrjtitle) {
        this.iLRIPrjtitle = iLRIPrjtitle;
    }

    public int getNumResources() {
        return numResources;
    }

    public void setNumResources(int numResources) {
        this.numResources = numResources;
    }

    public String getLicenseId() {
        return licenseId;
    }

    public void setLicenseId(String licenseId) {
        this.licenseId = licenseId;
    }

    public String getILRIActypi() {
        return iLRIActypi;
    }

    public void setILRIActypi(String iLRIActypi) {
        this.iLRIActypi = iLRIActypi;
    }

    public List<String> getILRIPrjsubjects() {
        return iLRIPrjsubjects;
    }

    public void setILRIPrjsubjects(List<String> iLRIPrjsubjects) {
        this.iLRIPrjsubjects = iLRIPrjsubjects;
    }

    public List<Object> getGroups() {
        return groups;
    }

    public void setGroups(List<Object> groups) {
        this.groups = groups;
    }

    public int getNumTags() {
        return numTags;
    }

    public void setNumTags(int numTags) {
        this.numTags = numTags;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isIsopen() {
        return isopen;
    }

    public void setIsopen(boolean isopen) {
        this.isopen = isopen;
    }

    public String getILRIActyipownership() {
        return iLRIActyipownership;
    }

    public void setILRIActyipownership(String iLRIActyipownership) {
        this.iLRIActyipownership = iLRIActyipownership;
    }

    public String getILRIActynatlevel() {
        return iLRIActynatlevel;
    }

    public void setILRIActynatlevel(String iLRIActynatlevel) {
        this.iLRIActynatlevel = iLRIActynatlevel;
    }

    public String getOwnerOrg() {
        return ownerOrg;
    }

    public void setOwnerOrg(String ownerOrg) {
        this.ownerOrg = ownerOrg;
    }

    public String getILRIActycitationacknowledge() {
        return iLRIActycitationacknowledge;
    }

    public void setILRIActycitationacknowledge(String iLRIActycitationacknowledge) {
        this.iLRIActycitationacknowledge = iLRIActycitationacknowledge;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getILRIPrjpi() {
        return iLRIPrjpi;
    }

    public void setILRIPrjpi(String iLRIPrjpi) {
        this.iLRIPrjpi = iLRIPrjpi;
    }

    public String getLicenseUrl() {
        return licenseUrl;
    }

    public void setLicenseUrl(String licenseUrl) {
        this.licenseUrl = licenseUrl;
    }

    public List<String> getILRIActyspecies() {
        return iLRIActyspecies;
    }

    public void setILRIActyspecies(List<String> iLRIActyspecies) {
        this.iLRIActyspecies = iLRIActyspecies;
    }

    public String getILRIPrjsdate() {
        return iLRIPrjsdate;
    }

    public void setILRIPrjsdate(String iLRIPrjsdate) {
        this.iLRIPrjsdate = iLRIPrjsdate;
    }

    public String getRevisionId() {
        return revisionId;
    }

    public void setRevisionId(String revisionId) {
        this.revisionId = revisionId;
    }

    public String getILRIPrjpartners() {
        return iLRIPrjpartners;
    }

    public void setILRIPrjpartners(String iLRIPrjpartners) {
        this.iLRIPrjpartners = iLRIPrjpartners;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getILRIActycontactemail() {
        return iLRIActycontactemail;
    }

    public void setILRIActycontactemail(String iLRIActycontactemail) {
        this.iLRIActycontactemail = iLRIActycontactemail;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("licenseTitle", licenseTitle).append("maintainer", maintainer).append("iLRIActyregions", iLRIActyregions).append("relationshipsAsObject", relationshipsAsObject).append("_private", _private).append("maintainerEmail", maintainerEmail).append("iLRIPrjcountries", iLRIPrjcountries).append("iLRIActyproduct", iLRIActyproduct).append("iLRIActystaff", iLRIActystaff).append("iLRIPrjedate", iLRIPrjedate).append("iLRIActycustodian", iLRIActycustodian).append("iLRIActyboundboxcenter", iLRIActyboundboxcenter).append("iLRIPrjabstract", iLRIPrjabstract).append("iLRIActycitation", iLRIActycitation).append("iLRIActymapzoom", iLRIActymapzoom).append("metadataCreated", metadataCreated).append("iLRIActydatavailable", iLRIActydatavailable).append("iLRIPrjstaff", iLRIPrjstaff).append("iLRIPrjspecies", iLRIPrjspecies).append("iLRIActymapextent", iLRIActymapextent).append("metadataModified", metadataModified).append("iLRIPrjwebsite", iLRIPrjwebsite).append("authorEmail", authorEmail).append("iLRIActydatecollectedend", iLRIActydatecollectedend).append("iLRIPrjgrant", iLRIPrjgrant).append("iLRIActycountries", iLRIActycountries).append("id", id).append("state", state).append("version", version).append("author", author).append("iLRIActycustodianemail", iLRIActycustodianemail).append("creatorUserId", creatorUserId).append("iLRIPrjdonor", iLRIPrjdonor).append("iLRIPrjpiemail", iLRIPrjpiemail).append("type", type).append("resources", resources).append("iLRIPrjregions", iLRIPrjregions).append("iLRIActycontactperson", iLRIActycontactperson).append("iLRIActypartners", iLRIActypartners).append("tags", tags).append("iLRIActyotheruse", iLRIActyotheruse).append("title", title).append("iLRIActyboundbox", iLRIActyboundbox).append("iLRIActydatecollected", iLRIActydatecollected).append("iLRIActypiemail", iLRIActypiemail).append("relationshipsAsSubject", relationshipsAsSubject).append("iLRIPrjtitle", iLRIPrjtitle).append("numResources", numResources).append("licenseId", licenseId).append("iLRIActypi", iLRIActypi).append("iLRIPrjsubjects", iLRIPrjsubjects).append("groups", groups).append("numTags", numTags).append("organization", organization).append("name", name).append("isopen", isopen).append("iLRIActyipownership", iLRIActyipownership).append("iLRIActynatlevel", iLRIActynatlevel).append("ownerOrg", ownerOrg).append("iLRIActycitationacknowledge", iLRIActycitationacknowledge).append("url", url).append("iLRIPrjpi", iLRIPrjpi).append("licenseUrl", licenseUrl).append("iLRIActyspecies", iLRIActyspecies).append("iLRIPrjsdate", iLRIPrjsdate).append("revisionId", revisionId).append("iLRIPrjpartners", iLRIPrjpartners).append("notes", notes).append("iLRIActycontactemail", iLRIActycontactemail).toString();
    }

}
