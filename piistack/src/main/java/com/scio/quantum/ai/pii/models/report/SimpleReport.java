package com.scio.quantum.ai.pii.models.report;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.scio.quantum.ai.pii.models.file.CandidateFile;
import com.scio.quantum.ai.pii.models.job.Job;

import java.util.List;

public class SimpleReport {

    @SerializedName("Detection")
    @Expose
    private List<CandidateFile> detection = null;
    @SerializedName("Job")
    @Expose
    private Job job = null;

    public SimpleReport() {
    }

    public SimpleReport(List<CandidateFile> detection, Job job) {
        this.detection = detection;
        this.job = job;
    }

    public List<CandidateFile> getDetection() {
        return detection;
    }

    public void setDetection(List<CandidateFile> detection) {
        this.detection = detection;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    @Override
    public String toString() {
        return "SimpleReport{" +
                "detection=" + detection +
                ", job=" + job +
                '}';
    }
}
