package com.example.ojt_ui.model;

import java.util.List;

public class FilterStep {

    private final String key;
    private final String label;
    private final List<FilterOption> options;
    private final String dependsOn;
    private final String showIf;
    private final String hint;

    public FilterStep(String key, String label, List<FilterOption> options, String dependsOn, String showIf, String hint) {
        this.key = key;
        this.label = label;
        this.options = options;
        this.dependsOn = dependsOn;
        this.showIf = showIf;
        this.hint = hint;
    }

    public String getKey() {
        return key;
    }

    public String getLabel() {
        return label;
    }

    public List<FilterOption> getOptions() {
        return options;
    }

    public String getDependsOn() {
        return dependsOn;
    }

    public String getShowIf() {
        return showIf;
    }

    public String getHint() {
        return hint;
    }
}
