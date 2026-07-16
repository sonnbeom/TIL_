package com.example.ojt_ui.model;

public class FieldRow {

    private final String name;
    private final String type;
    private final boolean required;

    public FieldRow(String name, String type, boolean required) {
        this.name = name;
        this.type = type;
        this.required = required;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public boolean isRequired() {
        return required;
    }
}
