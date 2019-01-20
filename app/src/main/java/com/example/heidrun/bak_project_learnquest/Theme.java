package com.example.heidrun.bak_project_learnquest;

public class Theme {

    int id;
    String themeName;

    public Theme(int id, String name) {
        this.id = id;
        this.themeName = name;
    }

    public String getThemeName() {
        return themeName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
