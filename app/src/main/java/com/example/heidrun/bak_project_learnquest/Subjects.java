package com.example.heidrun.bak_project_learnquest;

/**
 * erzeugt ein Objekt Subject
 */
public class Subjects {

    String subjectName;

    /**
     * Konstruktor Subject, erzeugt ein Subject-Objekt und der Name wird definiert
     * @param name
     */
    public Subjects(String name) {
        subjectName = name;
    }

    /**
     * retourniert den Namen eines Subjects,
     * @return String
     */
    public String getSubjectName() {
        return subjectName;
    }



}
