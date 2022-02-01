package com.example.teachererp.Model;

public class StudentsModel {
    private int id;
    private String name;
    private int course_id;
    private int branch_id;
    private int semester_id;

    public StudentsModel(int i, String sumit, int i1, int i2, int i3) {
        this.id = id;
        this.name = name;
        this.course_id = course_id;
        this.branch_id = branch_id;
        this.semester_id = semester_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCourse_id() {
        return course_id;
    }

    public void setCourse_id(int course_id) {
        this.course_id = course_id;
    }

    public int getBranch_id() {
        return branch_id;
    }

    public void setBranch_id(int branch_id) {
        this.branch_id = branch_id;
    }

    public int getSemester_id() {
        return semester_id;
    }

    public void setSemester_id(int semester_id) {
        this.semester_id = semester_id;
    }
}
