package com.example.heidrun.bak_project_learnquest;

public class Progress {

    private int ID;
    private int U_ID;
    private int Themen_gesamt;
    private int Fragen_gesamt;
    private int Fragen_richtig;
    private int Fragen_falsch;
    private int Facher_gesamt;
    private int Faecher_user;
    private int Themen_user;

    public Progress(int ID, int U_ID, int Themen_gesamt, int Fragen_gesamt, int Fragen_richtig,
             int Fragen_falsch, int Facher_gesamt, int Faecher_user, int Themen_user) {

        this.Facher_gesamt = Facher_gesamt;
        this.Faecher_user = Faecher_user;
        this.Themen_gesamt = Themen_gesamt;
        this.Themen_user = Themen_user;
        this.Fragen_falsch = Fragen_falsch;
        this.Fragen_gesamt = Fragen_gesamt;
        this.Fragen_richtig = Fragen_richtig;
        this.U_ID = U_ID;
        this.ID = ID;

    }

    public int getFacher_gesamt() {
        return Facher_gesamt;
    }

    public int getFaecher_user() {
        return Faecher_user;
    }

    public int getFragen_falsch() {
        return Fragen_falsch;
    }

    public int getFragen_gesamt() {
        return Fragen_gesamt;
    }

    public int getFragen_richtig() {
        return Fragen_richtig;
    }

    public int getID() {
        return ID;
    }

    public int getThemen_gesamt() {
        return Themen_gesamt;
    }

    public int getThemen_user() {
        return Themen_user;
    }

    public int getU_ID() {
        return U_ID;
    }

    public void setFragen_gesamt(int fragen_gesamt) {
        Fragen_gesamt = fragen_gesamt;
    }

    public void setFacher_gesamt(int facher_gesamt) {
        Facher_gesamt = facher_gesamt;
    }

    public void setFaecher_user(int faecher_user) {
        Faecher_user = faecher_user;
    }

    public void setFragen_falsch(int fragen_falsch) {
        Fragen_falsch = fragen_falsch;
    }

    public void setFragen_richtig(int fragen_richtig) {
        Fragen_richtig = fragen_richtig;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setThemen_gesamt(int themen_gesamt) {
        Themen_gesamt = themen_gesamt;
    }

    public void setThemen_user(int themen_user) {
        Themen_user = themen_user;
    }

    public void setU_ID(int u_ID) {
        U_ID = u_ID;
    }
}


