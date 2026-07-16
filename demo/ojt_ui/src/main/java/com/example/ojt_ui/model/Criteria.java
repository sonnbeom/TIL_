package com.example.ojt_ui.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Criteria {

    private final String key;
    private final String label;
    private final String status;
    private final String version;
    private final int instanceCount;
    private final boolean estimated;
    private final List<CriteriaDoc> docs;
    private final List<CriteriaVersion> versions;

    public Criteria(String key, String label, String status, String version, int instanceCount,
                     boolean estimated, List<CriteriaDoc> docs, List<CriteriaVersion> versions) {
        this.key = key;
        this.label = label;
        this.status = status;
        this.version = version;
        this.instanceCount = instanceCount;
        this.estimated = estimated;
        this.docs = docs;
        this.versions = versions;
    }

    public String getKey() {
        return key;
    }

    public String getLabel() {
        return label;
    }

    public String getStatus() {
        return status;
    }

    public String getVersion() {
        return version;
    }

    public int getInstanceCount() {
        return instanceCount;
    }

    public boolean isEstimated() {
        return estimated;
    }

    public List<CriteriaDoc> getDocs() {
        return docs;
    }

    public List<CriteriaVersion> getVersions() {
        return versions;
    }

    /** 라벨의 두번째 조각(예: "KR · 레저용 · FRP · 소형선박" → "레저용") — 적용 호선 탭의 가짜 호선명에 쓰인다. */
    public String getVesselCategoryLabel() {
        String[] parts = label.split(" · ");
        return parts.length > 1 ? parts[1] : label;
    }

    /** 버전 이력 탭 — 최신 버전이 위로 오도록 뒤집은 목록. */
    public List<CriteriaVersion> getVersionsReversed() {
        List<CriteriaVersion> reversed = new ArrayList<>(versions);
        Collections.reverse(reversed);
        return reversed;
    }
}
