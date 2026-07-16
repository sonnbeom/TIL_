package com.example.ojt_ui.dto;

/** GET /s03 쿼리 파라미터. society는 원래 @RequestParam(required = false)였으므로 기본값 없이 null을 허용한다. */
public class S03Request {

    private String regulatoryBody = "ClassificationSociety";
    private String society;
    private String vesselCategory = "LEISURE";
    private String hullMaterial = "FRP";
    private String sizeClass = "SMALL";
    private String applicableRule = "LEISURE_GUIDE";
    private String topTab = "mapping";
    private String detailTab = "docs";

    public String getRegulatoryBody() {
        return regulatoryBody;
    }

    public void setRegulatoryBody(String regulatoryBody) {
        this.regulatoryBody = regulatoryBody;
    }

    public String getSociety() {
        return society;
    }

    public void setSociety(String society) {
        this.society = society;
    }

    public String getVesselCategory() {
        return vesselCategory;
    }

    public void setVesselCategory(String vesselCategory) {
        this.vesselCategory = vesselCategory;
    }

    public String getHullMaterial() {
        return hullMaterial;
    }

    public void setHullMaterial(String hullMaterial) {
        this.hullMaterial = hullMaterial;
    }

    public String getSizeClass() {
        return sizeClass;
    }

    public void setSizeClass(String sizeClass) {
        this.sizeClass = sizeClass;
    }

    public String getApplicableRule() {
        return applicableRule;
    }

    public void setApplicableRule(String applicableRule) {
        this.applicableRule = applicableRule;
    }

    public String getTopTab() {
        return topTab;
    }

    public void setTopTab(String topTab) {
        this.topTab = topTab;
    }

    public String getDetailTab() {
        return detailTab;
    }

    public void setDetailTab(String detailTab) {
        this.detailTab = detailTab;
    }
}
