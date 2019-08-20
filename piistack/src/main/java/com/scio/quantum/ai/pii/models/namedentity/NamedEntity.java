package com.scio.quantum.ai.pii.models.namedentity;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class NamedEntity {
    @SerializedName("Lemma")
    @Expose
    private String lemma;
    @SerializedName("Confidence")
    @Expose
    private double  confidence;
    @SerializedName("Position")
    @Expose
    private NamedEntityPosition position;
    @SerializedName("Class")
    @Expose
    private NamedEntityClass namedEntityClass;

    private boolean remove = false;

    public NamedEntity(String lemma, double confidence, NamedEntityPosition position, NamedEntityClass namedEntityClass) {
        this.lemma = lemma;
        this.confidence = confidence;
        this.position = position;
        this.namedEntityClass = namedEntityClass;
    }

    public boolean isRemove() {
        return remove;
    }

    public void setRemove(boolean remove) {
        this.remove = remove;
    }
    public String getLemma() {
        return lemma;
    }

    public void setLemma(String lemma) {
        this.lemma = lemma;
    }

    public double getConfidence() {
        return confidence;
    }

    public void setConfidence(double confidence) {
        this.confidence = confidence;
    }

    public NamedEntityPosition getPosition() {
        return position;
    }

    public void setPosition(NamedEntityPosition position) {
        this.position = position;
    }

    public NamedEntityClass getNamedEntityClass() {
        return namedEntityClass;
    }

    public void setNamedEntityClass(NamedEntityClass namedEntityClass) {
        this.namedEntityClass = namedEntityClass;
    }

    @Override
    public String toString() {
        return "NamedEntity{" +
                "lemma='" + lemma + '\'' +
                ", confidence=" + confidence +
                ", position=" + position +
                ", namedEntityClass=" + namedEntityClass +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NamedEntity that = (NamedEntity) o;
        return Objects.equals(getLemma(), that.getLemma());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLemma());
    }
}
