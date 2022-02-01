package com.example.teachererp.Model;

import androidx.annotation.NonNull;

public class CoursesModel {
    private int id;
    private String course_name;

    public CoursesModel(int id, String course_name) {
        this.id = id;
        this.course_name = course_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    @NonNull
    @Override
    public String toString() {
        return this.course_name;
    }
}
