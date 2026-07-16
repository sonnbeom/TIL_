package com.example.ojt_ui.model;

/** 필터 스텝의 칩 하나 — 클릭 시 이동할 URL을 만들기 위한 target(캐스케이드 규칙 적용 후 결과 Selection)을 포함한다. */
public class OptionView {

    private final String id;
    private final String label;
    private final boolean selected;
    private final Selection target;

    public OptionView(String id, String label, boolean selected, Selection target) {
        this.id = id;
        this.label = label;
        this.selected = selected;
        this.target = target;
    }

    public String getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public boolean isSelected() {
        return selected;
    }

    public Selection getTarget() {
        return target;
    }
}
