package com.example.heidrun.bak_project_learnquest;

import java.io.Serializable;

/**
 * Klasse, die ein Questions Objekt darstellt
 */
public class Question implements Serializable {

    String question;
    String a1;
    String a2;
    String a3;
    String a4;


    public Question(){

    }

    /**
     * retourniert das Attribut Question
     * @return String
     */
    public String getQuestion() {
        return question;
    }

    /**
     * retourniert das Attribut a1 = Antwort 1
     * @return String
     */
    public String getA1() {
        return a1;
    }

    /**
     * retourniert das Attribut a2 = Antwort 2
     * @return String
     */
    public String getA2() {
        return a2;
    }

    /**
     * retourniert das Attribut a3 = Antwort 3
     * @return String
     */
    public String getA3() {
        return a3;
    }

    /**
     * retourniert das Attribut a4 = Antwort 4
     * @return String
     */
    public String getA4() {
        return a4;
    }
}
