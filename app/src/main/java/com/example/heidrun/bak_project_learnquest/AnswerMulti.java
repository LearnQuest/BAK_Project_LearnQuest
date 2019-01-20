package com.example.heidrun.bak_project_learnquest;

public class AnswerMulti {

    private int F_ID;
    private int ID;
    private String Loesung1;
    private String Loesung2;
    private String Loesung3;
    private String Loesung4;

    public AnswerMulti(int F_ID, int ID, String Loesung1, String Loesung2, String Loesung3,
                       String Loesung4) {

        this.F_ID = F_ID;
        this.ID = ID;
        this.Loesung1 = Loesung1;
        this.Loesung2 = Loesung2;
        this.Loesung3 = Loesung3;
        this.Loesung4 = Loesung4;
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

    public String getLoesung2() {
        return Loesung2;
    }

    public String getLoesung3() {
        return Loesung3;
    }

    public String getLoesung4() {
        return Loesung4;
    }

    public void setLoesung1(String loesung1) {
        Loesung1 = loesung1;
    }

    public void setLoesung2(String loesung2) {
        Loesung2 = loesung2;
    }

    public void setLoesung3(String loesung3) {
        Loesung3 = loesung3;
    }

    public void setLoesung4(String loesung4) {
        Loesung4 = loesung4;
    }
}


