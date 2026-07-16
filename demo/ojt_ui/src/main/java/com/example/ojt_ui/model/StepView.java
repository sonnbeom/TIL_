package com.example.ojt_ui.model;

import java.util.List;

public class StepView {

    private final int stepNumber;
    private final String label;
    private final String hint;
    private final boolean done;
    private final List<OptionView> options;

    public StepView(int stepNumber, String label, String hint, boolean done, List<OptionView> options) {
        this.stepNumber = stepNumber;
        this.label = label;
        this.hint = hint;
        this.done = done;
        this.options = options;
    }

    public int getStepNumber() {
        return stepNumber;
    }

    public String getLabel() {
        return label;
    }

    public String getHint() {
        return hint;
    }

    public boolean isDone() {
        return done;
    }

    public List<OptionView> getOptions() {
        return options;
    }
}
