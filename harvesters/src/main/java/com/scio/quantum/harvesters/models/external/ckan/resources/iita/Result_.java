
package com.scio.quantum.harvesters.models.external.ckan.resources.iita;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Result_ {

    @SerializedName("restriction")
    @Expose
    private String restriction;
    @SerializedName("license_title")
    @Expose
    private Object licenseTitle;
    @SerializedName("maintainer")
    @Expose
    private Object maintainer;
    @SerializedName("content_type")
    @Expose
    private String contentType;
    @SerializedName("creator")
    @Expose
    private String creator;
    @SerializedName("subject_vocab")
    @Expose
    private String subjectVocab;
    @SerializedName("private")
    @Expose
    private boolean _private;
    @SerializedName("creation_date")
    @Expose
    private String creationDate;
    @SerializedName("num_tags")
    @Expose
    private int numTags;
    @SerializedName("contributor_person")
    @Expose
    private String contributorPerson;
    @SerializedName("relation")
    @Expose
    private String relation;
    @SerializedName("embargo_end_date")
    @Expose
    private String embargoEndDate;
    @SerializedName("identifier_type")
    @Expose
    private String identifierType;
    @SerializedName("contributor_funder")
    @Expose
    private String contributorFunder;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("metadata_created")
    @Expose
    private String metadataCreated;
    @SerializedName("subject")
    @Expose
    private String subject;
    @SerializedName("contributor_projectlead_institute")
    @Expose
    private String contributorProjectleadInstitute;
    @SerializedName("contributor_project")
    @Expose
    private String contributorProject;
    @SerializedName("metadata_modified")
    @Expose
    private String metadataModified;
    @SerializedName("author")
    @Expose
    private Object author;
    @SerializedName("author_email")
    @Expose
    private Object authorEmail;
    @SerializedName("tags")
    @Expose
    private List<Tag> tags = null;
    @SerializedName("contributioncrp")
    @Expose
    private String contributioncrp;
    @SerializedName("source")
    @Expose
    private String source;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("version")
    @Expose
    private Object version;
    @SerializedName("relationships_as_object")
    @Expose
    private List<Object> relationshipsAsObject = null;
    @SerializedName("creator_user_id")
    @Expose
    private String creatorUserId;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("resources")
    @Expose
    private List<Resource> resources = null;
    @SerializedName("contributor_center")
    @Expose
    private String contributorCenter;
    @SerializedName("coverage_region")
    @Expose
    private String coverageRegion;
    @SerializedName("num_resources")
    @Expose
    private int numResources;
    @SerializedName("email_permission")
    @Expose
    private String emailPermission;
    @SerializedName("coverage_end_date")
    @Expose
    private String coverageEndDate;
    @SerializedName("format")
    @Expose
    private String format;
    @SerializedName("oa_status")
    @Expose
    private String oaStatus;
    @SerializedName("coverage_admin_unit")
    @Expose
    private String coverageAdminUnit;
    @SerializedName("agroecologicalzone")
    @Expose
    private String agroecologicalzone;
    @SerializedName("contact_email")
    @Expose
    private String contactEmail;
    @SerializedName("coverage_country")
    @Expose
    private String coverageCountry;
    @SerializedName("groups")
    @Expose
    private List<Object> groups = null;
    @SerializedName("organization")
    @Expose
    private Organization organization;
    @SerializedName("maintainer_email")
    @Expose
    private Object maintainerEmail;
    @SerializedName("creatorid_type")
    @Expose
    private String creatoridType;
    @SerializedName("relationships_as_subject")
    @Expose
    private List<Object> relationshipsAsSubject = null;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("publisher")
    @Expose
    private String publisher;
    @SerializedName("contributor_affiliation")
    @Expose
    private String contributorAffiliation;
    @SerializedName("language")
    @Expose
    private String language;
    @SerializedName("isopen")
    @Expose
    private boolean isopen;
    @SerializedName("rights")
    @Expose
    private String rights;
    @SerializedName("url")
    @Expose
    private Object url;
    @SerializedName("coverage_y")
    @Expose
    private String coverageY;
    @SerializedName("notes")
    @Expose
    private String notes;
    @SerializedName("owner_org")
    @Expose
    private String ownerOrg;
    @SerializedName("contributor_partnerid")
    @Expose
    private String contributorPartnerid;
    @SerializedName("coverage_x")
    @Expose
    private String coverageX;
    @SerializedName("creator_id")
    @Expose
    private String creatorId;
    @SerializedName("contact")
    @Expose
    private String contact;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("revision_id")
    @Expose
    private String revisionId;
    @SerializedName("identifier")
    @Expose
    private String identifier;
    @SerializedName("identifier_citation")
    @Expose
    private String identifierCitation;
    @SerializedName("coverage_start_date")
    @Expose
    private String coverageStartDate;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Result_() {
    }

    /**
     * 
     * @param resources
     * @param metadataCreated
     * @param coverageCountry
     * @param rights
     * @param contributorPartnerid
     * @param contentType
     * @param type
     * @param identifierCitation
     * @param revisionId
     * @param contributorFunder
     * @param numTags
     * @param coverageAdminUnit
     * @param maintainerEmail
     * @param _private
     * @param groups
     * @param metadataModified
     * @param contributorCenter
     * @param authorEmail
     * @param restriction
     * @param relation
     * @param embargoEndDate
     * @param creatoridType
     * @param format
     * @param url
     * @param coverageY
     * @param coverageX
     * @param language
     * @param identifier
     * @param oaStatus
     * @param emailPermission
     * @param subject
     * @param numResources
     * @param state
     * @param contributioncrp
     * @param ownerOrg
     * @param contact
     * @param publisher
     * @param creator
     * @param version
     * @param id
     * @param author
     * @param title
     * @param creatorUserId
     * @param organization
     * @param contributorProject
     * @param name
     * @param coverageStartDate
     * @param identifierType
     * @param tags
     * @param isopen
     * @param contributorAffiliation
     * @param contactEmail
     * @param contributorProjectleadInstitute
     * @param creatorId
     * @param contributorPerson
     * @param relationshipsAsSubject
     * @param coverageEndDate
     * @param agroecologicalzone
     * @param maintainer
     * @param creationDate
     * @param licenseTitle
     * @param source
     * @param subjectVocab
     * @param notes
     * @param coverageRegion
     * @param relationshipsAsObject
     */
    public Result_(String restriction, Object licenseTitle, Object maintainer, String contentType, String creator, String subjectVocab, boolean _private, String creationDate, int numTags, String contributorPerson, String relation, String embargoEndDate, String identifierType, String contributorFunder, String id, String metadataCreated, String subject, String contributorProjectleadInstitute, String contributorProject, String metadataModified, Object author, Object authorEmail, List<Tag> tags, String contributioncrp, String source, String state, Object version, List<Object> relationshipsAsObject, String creatorUserId, String type, List<Resource> resources, String contributorCenter, String coverageRegion, int numResources, String emailPermission, String coverageEndDate, String format, String oaStatus, String coverageAdminUnit, String agroecologicalzone, String contactEmail, String coverageCountry, List<Object> groups, Organization organization, Object maintainerEmail, String creatoridType, List<Object> relationshipsAsSubject, String name, String publisher, String contributorAffiliation, String language, boolean isopen, String rights, Object url, String coverageY, String notes, String ownerOrg, String contributorPartnerid, String coverageX, String creatorId, String contact, String title, String revisionId, String identifier, String identifierCitation, String coverageStartDate) {
        super();
        this.restriction = restriction;
        this.licenseTitle = licenseTitle;
        this.maintainer = maintainer;
        this.contentType = contentType;
        this.creator = creator;
        this.subjectVocab = subjectVocab;
        this._private = _private;
        this.creationDate = creationDate;
        this.numTags = numTags;
        this.contributorPerson = contributorPerson;
        this.relation = relation;
        this.embargoEndDate = embargoEndDate;
        this.identifierType = identifierType;
        this.contributorFunder = contributorFunder;
        this.id = id;
        this.metadataCreated = metadataCreated;
        this.subject = subject;
        this.contributorProjectleadInstitute = contributorProjectleadInstitute;
        this.contributorProject = contributorProject;
        this.metadataModified = metadataModified;
        this.author = author;
        this.authorEmail = authorEmail;
        this.tags = tags;
        this.contributioncrp = contributioncrp;
        this.source = source;
        this.state = state;
        this.version = version;
        this.relationshipsAsObject = relationshipsAsObject;
        this.creatorUserId = creatorUserId;
        this.type = type;
        this.resources = resources;
        this.contributorCenter = contributorCenter;
        this.coverageRegion = coverageRegion;
        this.numResources = numResources;
        this.emailPermission = emailPermission;
        this.coverageEndDate = coverageEndDate;
        this.format = format;
        this.oaStatus = oaStatus;
        this.coverageAdminUnit = coverageAdminUnit;
        this.agroecologicalzone = agroecologicalzone;
        this.contactEmail = contactEmail;
        this.coverageCountry = coverageCountry;
        this.groups = groups;
        this.organization = organization;
        this.maintainerEmail = maintainerEmail;
        this.creatoridType = creatoridType;
        this.relationshipsAsSubject = relationshipsAsSubject;
        this.name = name;
        this.publisher = publisher;
        this.contributorAffiliation = contributorAffiliation;
        this.language = language;
        this.isopen = isopen;
        this.rights = rights;
        this.url = url;
        this.coverageY = coverageY;
        this.notes = notes;
        this.ownerOrg = ownerOrg;
        this.contributorPartnerid = contributorPartnerid;
        this.coverageX = coverageX;
        this.creatorId = creatorId;
        this.contact = contact;
        this.title = title;
        this.revisionId = revisionId;
        this.identifier = identifier;
        this.identifierCitation = identifierCitation;
        this.coverageStartDate = coverageStartDate;
    }

    public String getRestriction() {
        return restriction;
    }

    public void setRestriction(String restriction) {
        this.restriction = restriction;
    }

    public Object getLicenseTitle() {
        return licenseTitle;
    }

    public void setLicenseTitle(Object licenseTitle) {
        this.licenseTitle = licenseTitle;
    }

    public Object getMaintainer() {
        return maintainer;
    }

    public void setMaintainer(Object maintainer) {
        this.maintainer = maintainer;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getSubjectVocab() {
        return subjectVocab;
    }

    public void setSubjectVocab(String subjectVocab) {
        this.subjectVocab = subjectVocab;
    }

    public boolean isPrivate() {
        return _private;
    }

    public void setPrivate(boolean _private) {
        this._private = _private;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public int getNumTags() {
        return numTags;
    }

    public void setNumTags(int numTags) {
        this.numTags = numTags;
    }

    public String getContributorPerson() {
        return contributorPerson;
    }

    public void setContributorPerson(String contributorPerson) {
        this.contributorPerson = contributorPerson;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getEmbargoEndDate() {
        return embargoEndDate;
    }

    public void setEmbargoEndDate(String embargoEndDate) {
        this.embargoEndDate = embargoEndDate;
    }

    public String getIdentifierType() {
        return identifierType;
    }

    public void setIdentifierType(String identifierType) {
        this.identifierType = identifierType;
    }

    public String getContributorFunder() {
        return contributorFunder;
    }

    public void setContributorFunder(String contributorFunder) {
        this.contributorFunder = contributorFunder;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMetadataCreated() {
        return metadataCreated;
    }

    public void setMetadataCreated(String metadataCreated) {
        this.metadataCreated = metadataCreated;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContributorProjectleadInstitute() {
        return contributorProjectleadInstitute;
    }

    public void setContributorProjectleadInstitute(String contributorProjectleadInstitute) {
        this.contributorProjectleadInstitute = contributorProjectleadInstitute;
    }

    public String getContributorProject() {
        return contributorProject;
    }

    public void setContributorProject(String contributorProject) {
        this.contributorProject = contributorProject;
    }

    public String getMetadataModified() {
        return metadataModified;
    }

    public void setMetadataModified(String metadataModified) {
        this.metadataModified = metadataModified;
    }

    public Object getAuthor() {
        return author;
    }

    public void setAuthor(Object author) {
        this.author = author;
    }

    public Object getAuthorEmail() {
        return authorEmail;
    }

    public void setAuthorEmail(Object authorEmail) {
        this.authorEmail = authorEmail;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public String getContributioncrp() {
        return contributioncrp;
    }

    public void setContributioncrp(String contributioncrp) {
        this.contributioncrp = contributioncrp;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
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

    public List<Object> getRelationshipsAsObject() {
        return relationshipsAsObject;
    }

    public void setRelationshipsAsObject(List<Object> relationshipsAsObject) {
        this.relationshipsAsObject = relationshipsAsObject;
    }

    public String getCreatorUserId() {
        return creatorUserId;
    }

    public void setCreatorUserId(String creatorUserId) {
        this.creatorUserId = creatorUserId;
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

    public String getContributorCenter() {
        return contributorCenter;
    }

    public void setContributorCenter(String contributorCenter) {
        this.contributorCenter = contributorCenter;
    }

    public String getCoverageRegion() {
        return coverageRegion;
    }

    public void setCoverageRegion(String coverageRegion) {
        this.coverageRegion = coverageRegion;
    }

    public int getNumResources() {
        return numResources;
    }

    public void setNumResources(int numResources) {
        this.numResources = numResources;
    }

    public String getEmailPermission() {
        return emailPermission;
    }

    public void setEmailPermission(String emailPermission) {
        this.emailPermission = emailPermission;
    }

    public String getCoverageEndDate() {
        return coverageEndDate;
    }

    public void setCoverageEndDate(String coverageEndDate) {
        this.coverageEndDate = coverageEndDate;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getOaStatus() {
        return oaStatus;
    }

    public void setOaStatus(String oaStatus) {
        this.oaStatus = oaStatus;
    }

    public String getCoverageAdminUnit() {
        return coverageAdminUnit;
    }

    public void setCoverageAdminUnit(String coverageAdminUnit) {
        this.coverageAdminUnit = coverageAdminUnit;
    }

    public String getAgroecologicalzone() {
        return agroecologicalzone;
    }

    public void setAgroecologicalzone(String agroecologicalzone) {
        this.agroecologicalzone = agroecologicalzone;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getCoverageCountry() {
        return coverageCountry;
    }

    public void setCoverageCountry(String coverageCountry) {
        this.coverageCountry = coverageCountry;
    }

    public List<Object> getGroups() {
        return groups;
    }

    public void setGroups(List<Object> groups) {
        this.groups = groups;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public Object getMaintainerEmail() {
        return maintainerEmail;
    }

    public void setMaintainerEmail(Object maintainerEmail) {
        this.maintainerEmail = maintainerEmail;
    }

    public String getCreatoridType() {
        return creatoridType;
    }

    public void setCreatoridType(String creatoridType) {
        this.creatoridType = creatoridType;
    }

    public List<Object> getRelationshipsAsSubject() {
        return relationshipsAsSubject;
    }

    public void setRelationshipsAsSubject(List<Object> relationshipsAsSubject) {
        this.relationshipsAsSubject = relationshipsAsSubject;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getContributorAffiliation() {
        return contributorAffiliation;
    }

    public void setContributorAffiliation(String contributorAffiliation) {
        this.contributorAffiliation = contributorAffiliation;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public boolean isIsopen() {
        return isopen;
    }

    public void setIsopen(boolean isopen) {
        this.isopen = isopen;
    }

    public String getRights() {
        return rights;
    }

    public void setRights(String rights) {
        this.rights = rights;
    }

    public Object getUrl() {
        return url;
    }

    public void setUrl(Object url) {
        this.url = url;
    }

    public String getCoverageY() {
        return coverageY;
    }

    public void setCoverageY(String coverageY) {
        this.coverageY = coverageY;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getOwnerOrg() {
        return ownerOrg;
    }

    public void setOwnerOrg(String ownerOrg) {
        this.ownerOrg = ownerOrg;
    }

    public String getContributorPartnerid() {
        return contributorPartnerid;
    }

    public void setContributorPartnerid(String contributorPartnerid) {
        this.contributorPartnerid = contributorPartnerid;
    }

    public String getCoverageX() {
        return coverageX;
    }

    public void setCoverageX(String coverageX) {
        this.coverageX = coverageX;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRevisionId() {
        return revisionId;
    }

    public void setRevisionId(String revisionId) {
        this.revisionId = revisionId;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getIdentifierCitation() {
        return identifierCitation;
    }

    public void setIdentifierCitation(String identifierCitation) {
        this.identifierCitation = identifierCitation;
    }

    public String getCoverageStartDate() {
        return coverageStartDate;
    }

    public void setCoverageStartDate(String coverageStartDate) {
        this.coverageStartDate = coverageStartDate;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("restriction", restriction).append("licenseTitle", licenseTitle).append("maintainer", maintainer).append("contentType", contentType).append("creator", creator).append("subjectVocab", subjectVocab).append("_private", _private).append("creationDate", creationDate).append("numTags", numTags).append("contributorPerson", contributorPerson).append("relation", relation).append("embargoEndDate", embargoEndDate).append("identifierType", identifierType).append("contributorFunder", contributorFunder).append("id", id).append("metadataCreated", metadataCreated).append("subject", subject).append("contributorProjectleadInstitute", contributorProjectleadInstitute).append("contributorProject", contributorProject).append("metadataModified", metadataModified).append("author", author).append("authorEmail", authorEmail).append("tags", tags).append("contributioncrp", contributioncrp).append("source", source).append("state", state).append("version", version).append("relationshipsAsObject", relationshipsAsObject).append("creatorUserId", creatorUserId).append("type", type).append("resources", resources).append("contributorCenter", contributorCenter).append("coverageRegion", coverageRegion).append("numResources", numResources).append("emailPermission", emailPermission).append("coverageEndDate", coverageEndDate).append("format", format).append("oaStatus", oaStatus).append("coverageAdminUnit", coverageAdminUnit).append("agroecologicalzone", agroecologicalzone).append("contactEmail", contactEmail).append("coverageCountry", coverageCountry).append("groups", groups).append("organization", organization).append("maintainerEmail", maintainerEmail).append("creatoridType", creatoridType).append("relationshipsAsSubject", relationshipsAsSubject).append("name", name).append("publisher", publisher).append("contributorAffiliation", contributorAffiliation).append("language", language).append("isopen", isopen).append("rights", rights).append("url", url).append("coverageY", coverageY).append("notes", notes).append("ownerOrg", ownerOrg).append("contributorPartnerid", contributorPartnerid).append("coverageX", coverageX).append("creatorId", creatorId).append("contact", contact).append("title", title).append("revisionId", revisionId).append("identifier", identifier).append("identifierCitation", identifierCitation).append("coverageStartDate", coverageStartDate).toString();
    }

}
