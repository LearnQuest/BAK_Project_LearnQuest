package com.example.heidrun.bak_project_learnquest;

public class AnswerSingle {

    private int F_ID;
    private int ID;
    private String Loesung1;

    public AnswerSingle(int F_ID, int ID, String Loesung1) {

        this.F_ID = F_ID;
        this.ID = ID;
        this.Loesung1 = Loesung1;
    }

    public void setF_ID(int f_ID) {
        F_ID = f_ID;
    }

    public int getF_ID() {
        return F_ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getID() {
        return ID;
    }

    public String getLoesung1() {
        return Loesung1;
    }

    public void setLoesung1(String loesung1) {
        Loesung1 = loesung1;
    }
}


