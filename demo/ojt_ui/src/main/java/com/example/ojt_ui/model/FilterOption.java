package com.example.ojt_ui.model;

public class FilterOption {

    private final String id;
    private final String label;

    public FilterOption(String id, String label) {
        this.id = id;
        this.label = label;
    }

    public String getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }
}
