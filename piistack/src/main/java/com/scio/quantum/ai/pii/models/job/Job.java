package com.scio.quantum.ai.pii.models.job;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Job {

    @SerializedName("JobID")
    @Expose
    private String jobID;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("Title")
    @Expose
    private String title;
    @SerializedName("Path")
    @Expose
    private String path;
    @SerializedName("Mode")
    @Expose
    private String mode;
    @SerializedName("Identifier")
    @Expose
    private String identifier;
    @SerializedName("CustomTerms")
    @Expose
    private ArrayList<String> customTerms;

    public Job(String jobID, String email, String title, String path, String mode, String identifier, ArrayList<String> customTerms) {
        this.jobID = jobID;
        this.email = email;
        this.title = title;
        this.path = path;
        this.mode = mode;
        this.identifier = identifier;
        this.customTerms = customTerms;
    }

    public String getJobID() {
        return jobID;
    }

    public void setJobID(String jobID) {
        this.jobID = jobID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public ArrayList<String> getCustomTerms() {
        return customTerms;
    }

    public void setCustomTerms(ArrayList<String> customTerms) {
        this.customTerms = customTerms;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    @Override
    public String toString() {
        return "Job{" +
                "jobID='" + jobID + '\'' +
                ", email='" + email + '\'' +
                ", title='" + title + '\'' +
                ", path='" + path + '\'' +
                ", mode='" + mode + '\'' +
                ", identifier='" + identifier + '\'' +
                ", customTerms=" + customTerms +
                '}';
    }
}
