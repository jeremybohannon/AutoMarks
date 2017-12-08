package com.automarks.storage.storageService;

import java.util.ArrayList;

public class Assignment {
    long id;
    String description;
    String fileId;
    ArrayList<Case> cases;
    String assignmentName;

    public Assignment() {
        this.cases = new ArrayList<Case>();
    }

    public Assignment(long id, String description, String fileId, ArrayList<Case> cases, String assignmentName) {
        this.id = id;
        this.description = description;
        this.fileId = fileId;
        this.cases = cases;
        this.assignmentName = assignmentName;
    }

    public void addCase(int id, String text, double points, boolean passed){
        cases.add(new Case(id, text, points, passed));
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public ArrayList<Case> getCases() {
        return cases;
    }

    public void setCases(ArrayList<Case> cases) {
        this.cases = cases;
    }

    public String getAssignmentName() {
        return assignmentName;
    }

    public void setAssignmentName(String assignmentName) {
        this.assignmentName = assignmentName;
    }
}
