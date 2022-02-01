package com.example.teachererp.Model;

public class SemesterModel {
    private int id;
    private int sem;

    public SemesterModel(int id, int sem) {
        this.id = id;
        this.sem = sem;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSem() {
        return sem;
    }

    public void setSem(int sem) {
        this.sem = sem;
    }
    public String toString() {
        return String.valueOf(this.sem);
    }
}
