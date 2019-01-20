package com.example.heidrun.bak_project_learnquest;

public class Subject {

    int id;
    String subjectName;

    public Subject(int id, String name) {
        this.id = id;
        this.subjectName = name;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
