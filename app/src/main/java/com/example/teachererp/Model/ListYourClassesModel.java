package com.example.teachererp.Model;

public class ListYourClassesModel {
    private String Course;
    private String Branch;
    private String Semester;
    private String Subject;

    public ListYourClassesModel(String course, String branch, String semester, String subject) {
        Course = course;
        Branch = branch;
        Semester = semester;
        Subject = subject;
    }

    public String getCourse() {
        return Course;
    }

    public void setCourse(String course) {
        Course = course;
    }

    public String getBranch() {
        return Branch;
    }

    public void setBranch(String branch) {
        Branch = branch;
    }

    public String getSemester() {
        return Semester;
    }

    public void setSemester(String semester) {
        Semester = semester;
    }

    public String getSubject() {
        return Subject;
    }

    public void setSubject(String subject) {
        Subject = subject;
    }
}
