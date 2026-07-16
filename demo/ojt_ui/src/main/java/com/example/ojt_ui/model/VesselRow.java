package com.example.ojt_ui.model;

public class VesselRow {

    private final int index;
    private final String name;
    private final int progress;

    public VesselRow(int index, String name, int progress) {
        this.index = index;
        this.name = name;
        this.progress = progress;
    }

    public int getIndex() {
        return index;
    }

    public String getName() {
        return name;
    }

    public int getProgress() {
        return progress;
    }
}
