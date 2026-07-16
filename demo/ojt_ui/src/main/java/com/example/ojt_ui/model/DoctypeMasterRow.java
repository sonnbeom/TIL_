package com.example.ojt_ui.model;

import java.util.List;

public class DoctypeMasterRow {

    public static class Ref {
        private final Selection criteriaTarget;
        private final String criteriaLabel;
        private final String submission;
        private final String sourceCls;

        public Ref(Selection criteriaTarget, String criteriaLabel, String submission, String sourceCls) {
            this.criteriaTarget = criteriaTarget;
            this.criteriaLabel = criteriaLabel;
            this.submission = submission;
            this.sourceCls = sourceCls;
        }

        public Selection getCriteriaTarget() {
            return criteriaTarget;
        }

        public String getCriteriaLabel() {
            return criteriaLabel;
        }

        public String getSubmission() {
            return submission;
        }

        public boolean isApproval() {
            return "AP".equals(submission);
        }

        public String getSourceCls() {
            return sourceCls;
        }
    }

    private final String id;
    private final String name;
    private final String form;
    private final List<Ref> refs;

    public DoctypeMasterRow(String id, String name, String form, List<Ref> refs) {
        this.id = id;
        this.name = name;
        this.form = form;
        this.refs = refs;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getForm() {
        return form;
    }

    public List<Ref> getRefs() {
        return refs;
    }
}
