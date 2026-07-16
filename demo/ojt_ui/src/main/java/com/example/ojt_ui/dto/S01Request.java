package com.example.ojt_ui.dto;

/** GET /s01 쿼리 파라미터. 값이 없으면 필드 초기값이 유지되어 @RequestParam(defaultValue=...)와 동일하게 동작한다. */
public class S01Request {

    private String group = "port";

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }
}
