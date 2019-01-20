package com.example.heidrun.bak_project_learnquest;

public class Question {

    private int F_ID;
    private int T_ID;
    private int Fach_ID;
    private String Frage;
    private boolean Fragenart;
    private String Antwort1;
    private String Antwort2;
    private String Antwort3;
    private String Antwort4;

    public Question(int F_ID, int T_ID, int Fach_ID, String Frage, boolean Fragenart,
                    String Antwort1, String Antwort2, String Antwort3, String Antwort4) {

        this.F_ID = F_ID;
        this.T_ID = T_ID;
        this.Fach_ID = Fach_ID;
        this.Frage = Frage;
        this.Fragenart = Fragenart;
        this.Antwort1 = Antwort1;
        this.Antwort2 = Antwort2;
        this.Antwort3 = Antwort3;
        this.Antwort4 = Antwort4;
    }

    public boolean isFragenart() {
        return Fragenart;
    }

    public int getF_ID() {
        return F_ID;
    }

    public int getFach_ID() {
        return Fach_ID;
    }

    public int getT_ID() {
        return T_ID;
    }

    public String getAntwort1() {
        return Antwort1;
    }

    public String getAntwort2() {
        return Antwort2;
    }

    public String getAntwort3() {
        return Antwort3;
    }

    public String getAntwort4() {
        return Antwort4;
    }

    public String getFrage() {
        return Frage;
    }

    public void setAntwort1(String antwort1) {
        Antwort1 = antwort1;
    }

    public void setAntwort2(String antwort2) {
        Antwort2 = antwort2;
    }

    public void setAntwort3(String antwort3) {
        Antwort3 = antwort3;
    }

    public void setAntwort4(String antwort4) {
        Antwort4 = antwort4;
    }

    public void setF_ID(int f_ID) {
        F_ID = f_ID;
    }

    public void setFach_ID(int fach_ID) {
        Fach_ID = fach_ID;
    }

    public void setFrage(String frage) {
        Frage = frage;
    }

    public void setFragenart(boolean fragenart) {
        Fragenart = fragenart;
    }

    public void setT_ID(int t_ID) {
        T_ID = t_ID;
    }
}
