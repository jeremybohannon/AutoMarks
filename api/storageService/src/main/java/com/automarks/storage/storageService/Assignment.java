package com.automarks.storage.storageService;

import java.util.ArrayList;

public class Assignment {
    long id;
    String description;
    String fileId;
    ArrayList<Case> cases;

    public Assignment() {
        this.cases = new ArrayList<Case>();
    }

    public Assignment(long id, String description, String fileId, ArrayList<Case> cases) {
        this.id = id;
        this.description = description;
        this.fileId = fileId;
        this.cases = cases;
    }

    public void addCase(int id, String text, double points){
        cases.add(new Case(id, text, points));
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
}
