package com.example.ojt_ui.model;

public class Doctype {

    private final String id;
    private final String name;
    private final String form;

    public Doctype(String id, String name, String form) {
        this.id = id;
        this.name = name;
        this.form = form;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getForm() {
        return form;
    }
}
