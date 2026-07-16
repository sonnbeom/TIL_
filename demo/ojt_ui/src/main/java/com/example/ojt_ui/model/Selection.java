package com.example.ojt_ui.model;

/** S03 6단계 드릴다운 필터의 현재 선택값(mockup_ui/test3.html의 s03Selection에 대응). */
public class Selection {

    private final String regulatoryBody;
    private final String society;
    private final String vesselCategory;
    private final String hullMaterial;
    private final String sizeClass;
    private final String applicableRule;

    public Selection(String regulatoryBody, String society, String vesselCategory,
                      String hullMaterial, String sizeClass, String applicableRule) {
        this.regulatoryBody = regulatoryBody;
        this.society = society;
        this.vesselCategory = vesselCategory;
        this.hullMaterial = hullMaterial;
        this.sizeClass = sizeClass;
        this.applicableRule = applicableRule;
    }

    public String getRegulatoryBody() {
        return regulatoryBody;
    }

    public String getSociety() {
        return society;
    }

    public String getVesselCategory() {
        return vesselCategory;
    }

    public String getHullMaterial() {
        return hullMaterial;
    }

    public String getSizeClass() {
        return sizeClass;
    }

    public String getApplicableRule() {
        return applicableRule;
    }

    /** step key 이름으로 현재 값을 조회 (필터 스텝 렌더링 시 dependsOn 체크·선택 여부 판정에 사용) */
    public String get(String key) {
        switch (key) {
            case "regulatoryBody": return regulatoryBody;
            case "society": return society;
            case "vesselCategory": return vesselCategory;
            case "hullMaterial": return hullMaterial;
            case "sizeClass": return sizeClass;
            case "applicableRule": return applicableRule;
            default: return null;
        }
    }

    /** S03_CRITERIA 조회 키 (mockup_ui/test3.html의 s03CriteriaKey와 동일한 조합 규칙) */
    public String toKey() {
        return String.join("|", regulatoryBody, society == null ? "-" : society,
                vesselCategory, hullMaterial, sizeClass, applicableRule);
    }
}
