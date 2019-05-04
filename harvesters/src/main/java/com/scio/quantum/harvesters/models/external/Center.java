package com.scio.quantum.harvesters.models.external;

public class Center {

    private String rootUrl;
    private String ownSpace;
    private String centerId;
    private String centerName;
    private String dataverseID;
    private String exporterURL;

    public Center(String rootUrl, String ownSpace, String centerId, String centerName, String dataverseID, String exporterURL) {
        this.rootUrl = rootUrl;
        this.ownSpace = ownSpace;
        this.centerId = centerId;
        this.centerName = centerName;
        this.dataverseID = dataverseID;
        this.exporterURL = exporterURL;
    }

    public String getRootUrl() {
        return rootUrl;
    }

    public void setRootUrl(String rootUrl) {
        this.rootUrl = rootUrl;
    }

    public String getOwnSpace() {
        return ownSpace;
    }

    public void setOwnSpace(String ownSpace) {
        this.ownSpace = ownSpace;
    }

    public String getCenterId() {
        return centerId;
    }

    public void setCenterId(String centerId) {
        this.centerId = centerId;
    }

    public String getCenterName() {
        return centerName;
    }

    public void setCenterName(String centerName) {
        this.centerName = centerName;
    }

    public String getDataverseID() {
        return dataverseID;
    }

    public void setDataverseID(String dataverseID) {
        this.dataverseID = dataverseID;
    }

    public String getExporterURL() {
        return exporterURL;
    }

    public void setExporterURL(String exporterURL) {
        this.exporterURL = exporterURL;
    }

    @Override
    public String toString() {
        return "Center{" +
                "rootUrl='" + rootUrl + '\'' +
                ", ownSpace='" + ownSpace + '\'' +
                ", centerId='" + centerId + '\'' +
                ", centerName='" + centerName + '\'' +
                ", dataverseID='" + dataverseID + '\'' +
                ", exporterURL='" + exporterURL + '\'' +
                '}';
    }
}
