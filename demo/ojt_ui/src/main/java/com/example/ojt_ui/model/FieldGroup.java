package com.example.ojt_ui.model;

import java.util.List;

public class FieldGroup {

    private final String cat;
    private final String submodelId;
    private final String unit;
    private final boolean collapsedByDefault;
    private final List<FieldRow> rows;

    public FieldGroup(String cat, String submodelId, String unit, boolean collapsedByDefault, List<FieldRow> rows) {
        this.cat = cat;
        this.submodelId = submodelId;
        this.unit = unit;
        this.collapsedByDefault = collapsedByDefault;
        this.rows = rows;
    }

    public String getCat() {
        return cat;
    }

    public String getSubmodelId() {
        return submodelId;
    }

    public String getUnit() {
        return unit;
    }

    public boolean isCollapsedByDefault() {
        return collapsedByDefault;
    }

    public List<FieldRow> getRows() {
        return rows;
    }
}
