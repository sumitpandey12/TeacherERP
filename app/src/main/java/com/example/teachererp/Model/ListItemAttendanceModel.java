package com.example.teachererp.Model;

public class ListItemAttendanceModel {
    private int id;
    private String name;
    private int course_id;
    private int branch_id;
    private int semester_id;
    private int status;

    public ListItemAttendanceModel(int id, String name, int course_id, int branch_id, int semester_id, int status) {
        this.id = id;
        this.name = name;
        this.course_id = course_id;
        this.branch_id = branch_id;
        this.semester_id = semester_id;
        this.status = status;
    }

    public ListItemAttendanceModel(int id, String name, int course_id, int branch_id, int semester_id) {
        this.id = id;
        this.name = name;
        this.course_id = course_id;
        this.branch_id = branch_id;
        this.semester_id = semester_id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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
