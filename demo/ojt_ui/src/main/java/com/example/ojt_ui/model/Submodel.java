package com.example.ojt_ui.model;

public class Submodel {

    private final String id;
    private final String label;
    private final String std;
    private final String desc;

    public Submodel(String id, String label, String std, String desc) {
        this.id = id;
        this.label = label;
        this.std = std;
        this.desc = desc;
    }

    public String getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public String getStd() {
        return std;
    }

    public String getDesc() {
        return desc;
    }
}
