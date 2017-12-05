package com.automarks.storage.storageService;

public class Case {
    int id;
    String text;
    double pointValue;
    boolean passed;

    public Case() {
    }

    public Case(int id, String text, double pointValue, boolean passed) {
        this.id = id;
        this.text = text;
        this.pointValue = pointValue;
        this.passed = passed;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}
