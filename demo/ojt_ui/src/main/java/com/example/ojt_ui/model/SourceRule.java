package com.example.ojt_ui.model;

public class SourceRule {

    private final String label;
    private final String cls;

    public SourceRule(String label, String cls) {
        this.label = label;
        this.cls = cls;
    }

    public String getLabel() {
        return label;
    }

    public String getCls() {
        return cls;
    }
}
