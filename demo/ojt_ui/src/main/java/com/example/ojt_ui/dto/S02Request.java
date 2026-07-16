package com.example.ojt_ui.dto;

/** GET /s02 쿼리 파라미터. */
public class S02Request {

    private String node = "type_vessel";
    private String tab = "fields";

    public String getNode() {
        return node;
    }

    public void setNode(String node) {
        this.node = node;
    }

    public String getTab() {
        return tab;
    }

    public void setTab(String tab) {
        this.tab = tab;
    }
}
