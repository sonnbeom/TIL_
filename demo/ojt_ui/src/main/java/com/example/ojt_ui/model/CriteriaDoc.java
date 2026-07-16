package com.example.ojt_ui.model;

public class CriteriaDoc {

    private final String doctypeId;
    private final String submission;
    private final String sourceRuleId;
    private final boolean substituted;
    private final String timing;

    public CriteriaDoc(String doctypeId, String submission, String sourceRuleId, boolean substituted, String timing) {
        this.doctypeId = doctypeId;
        this.submission = submission;
        this.sourceRuleId = sourceRuleId;
        this.substituted = substituted;
        this.timing = timing;
    }

    public String getDoctypeId() {
        return doctypeId;
    }

    public String getSubmission() {
        return submission;
    }

    public String getSourceRuleId() {
        return sourceRuleId;
    }

    public boolean isSubstituted() {
        return substituted;
    }

    public String getTiming() {
        return timing;
    }

    public boolean isApproval() {
        return "AP".equals(submission);
    }
}
