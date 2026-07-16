package com.example.ojt_ui.model;

public class CriteriaVersion {

    private final String version;
    private final String status;
    private final String date;
    private final String registrant;
    private final String sourceRef;

    public CriteriaVersion(String version, String status, String date, String registrant, String sourceRef) {
        this.version = version;
        this.status = status;
        this.date = date;
        this.registrant = registrant;
        this.sourceRef = sourceRef;
    }

    public String getVersion() {
        return version;
    }

    public String getStatus() {
        return status;
    }

    public String getDate() {
        return date;
    }

    public String getRegistrant() {
        return registrant;
    }

    public String getSourceRef() {
        return sourceRef;
    }
}
