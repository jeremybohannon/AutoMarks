package com.automarks.storage.storageService;

public class Case {
    String text;
    double pointValue;
    boolean passed;

    public Case() {
    }

    public Case(String text, double pointValue, boolean passed) {
        this.text = text;
        this.pointValue = pointValue;
        this.passed = passed;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public double getPointValue() {
        return pointValue;
    }

    public void setPointValue(double pointValue) {
        this.pointValue = pointValue;
    }

    public boolean isPassed() {
        return passed;
    }

    public void setPassed(boolean passed) {
        this.passed = passed;
    }
}

