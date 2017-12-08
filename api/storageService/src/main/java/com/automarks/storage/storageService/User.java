package com.automarks.storage.storageService;

import java.util.ArrayList;

public class User {

    String firstName;
    String lastName;
    String schoolID;
    ArrayList<Submissions> submissions;
    //login information
    String login;
    String password;
    boolean proffesor;
    boolean student;


    public User() {
        this.submissions = new ArrayList<>();
    }

    public User(String firstName, String lastName, String schoolID, ArrayList<Submissions> submissions) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.schoolID = schoolID;
        this.submissions = submissions;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSchoolID() {
        return schoolID;
    }

    public void setSchoolID(String schoolID) {
        this.schoolID = schoolID;
    }

    public void addSubmission(Submissions sub){
        submissions.add(sub);
    }

    public ArrayList<Submissions> getSubmissions() {
        return submissions;
    }

    public void setSubmissions(ArrayList<Submissions> submissions) {
        this.submissions = submissions;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isProffesor() {
        return proffesor;
    }

    public void setProffesor(boolean proffesor) {
        this.proffesor = proffesor;
    }

    public boolean isStudent() {
        return student;
    }

    public void setStudent(boolean student) {
        this.student = student;
    }
}
