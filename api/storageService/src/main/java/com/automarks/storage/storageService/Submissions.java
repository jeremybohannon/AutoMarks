package com.automarks.storage.storageService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Submissions {

    Date date;
    Assignment assignment;
    List<Case> cases;
    String file;
    int score;

    public Submissions() {
        this.cases = new ArrayList<>();
    }

    public Submissions(Date date, Assignment assignment, List<Case> cases, String file, int score) {
        this.date = date;
        this.assignment = assignment;
        this.cases = cases;
        this.file = file;
        this.score = score;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Assignment getAssignment() {
        return assignment;
    }

    public void setAssignment(Assignment assignment) {
        this.assignment = assignment;
    }

    public void addCases(Case cases) {
        this.cases.add(cases);
    }

    public List<Case> getCases() {
        return cases;
    }

    public void setCases(List<Case> cases) {
        this.cases = cases;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
