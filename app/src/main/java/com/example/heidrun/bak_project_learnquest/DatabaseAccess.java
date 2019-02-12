package com.example.heidrun.bak_project_learnquest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Diese Klasse enthält alle Methoden, die für die Verbindung, Verbindungsabbau oder sämtliche Abfragen notwendig sind
 */
public class DatabaseAccess {

    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase db;
    private static DatabaseAccess instance;
    private Cursor c = null;

    private DatabaseAccess(Context context){
        this.openHelper = new DatabaseOpenHelper(context);
    }

    public static DatabaseAccess getInstance(Context context){
        if(instance == null){
            instance=new DatabaseAccess(context);
        }
        return instance;
    }

    /**
     * öffnet die DB
     */
    public void open(){
        this.db=openHelper.getWritableDatabase();
    }

    /**
     * schließt die DB
     */
    public void close(){
        if(db!=null){
            this.db.close();
        }
    }

    /**
     * liest die Subjects aus der DB ein
     * @return ein Array mit allen Subjects-Bezeichnungen
     */
    public ArrayList<String> getSubjects(){
        ArrayList<String> arrSub = new ArrayList<String>();
        c = db.rawQuery("select Fach from fach", null);

        while(c.moveToNext()){
            arrSub.add(c.getString(0));
        }
        return arrSub;
    }

    /**
     * liest alle Question zu einem bestimmten Fach aus der DB aus
     * @param fach vom Typ String
     * @return ArrayList mit allen Questions
     */
    public ArrayList<Question> getQuestion(String fach){
        ArrayList<Question> arrQ = new ArrayList<Question>();
        c = db.rawQuery("select Fach_ID from fach where Fach='" + fach + "'",null);
        c.moveToNext();
        int f_id = c.getInt(c.getColumnIndex("Fach_ID"));
        c = db.rawQuery("select * from fragen where Fach_ID='" + f_id +"' ", null);

        while(c.moveToNext()){
            Question q = new Question();
            q.question = c.getString(c.getColumnIndex("Frage"));
            q.a1 = c.getString(c.getColumnIndex("Antwort1"));
            q.a2 = c.getString(c.getColumnIndex("Antwort2"));
            q.a3 = c.getString(c.getColumnIndex("Antwort3"));
            q.a4 = c.getString(c.getColumnIndex("Antwort4"));

            arrQ.add(q);
        }
        return arrQ;
    }

    /**
     * gibt zurück, wie viele Fragen zu diesem Fach vorhanden sind
     * @param fach vom Typ String
     * @return integer, Anzahl der Fragen
     */
    public int getQuestionCounter(String fach){
        int counter=0;
        c = db.rawQuery("select Fach_ID from fach where Fach='" + fach + "'",null);
        c.moveToNext();
        int f_id = c.getInt(c.getColumnIndex("Fach_ID"));

        c = db.rawQuery("select Fach_ID from fragen where Fach_ID='" + f_id + "'", null);
        c.moveToNext();
        counter = c.getCount();

        return counter;
    }

    /**
     * holt die richtige Antwort aus der DB und überprüft diese mit der ausgewählten Antwort
     * @param Frage vom Typ String
     * @param Antwort vom Typ String
     * @return true, wenn Frage richtig, false, wenn Frage falsch
     */
    public boolean getCheckedAnswer(String Frage, String Antwort){
        c = db.rawQuery("select * from fragen where Frage='" + Frage +"'", null);
        c.moveToNext();
        int fid = c.getInt(c.getColumnIndex("F_ID"));
        c = db.rawQuery("select * from fragen_single where F_ID='" + fid +"'", null);
        c.moveToNext();
        String a = c.getString(c.getColumnIndex("Loesung1"));

        if(a.equals(Antwort)){
            return true;
        }else{
            return false;
        }

    }

    /**
     * aktualisiert den Wert in der DB bezüglich den Trophies
     * @param trophieID welche Trophy aktualisiert werden soll, vom Typ int
     * @param user für welchen User der DB Eintrag aktualisiert werden soll, Typ String
     */
    public void writeToTrophie(int trophieID, String user){
        c = db.rawQuery("select * from trophies_user where TR_ID='" + trophieID + "' and Username='" + user +"'", null);
        int counter=0;
        if(c.moveToNext()){
            //update
            counter = c.getInt(c.getColumnIndex("Done"));
            counter++;

            ContentValues values = new ContentValues();
            db.beginTransaction();

            values.put("Done",counter);

            db.update("trophies_user", values, "TR_ID='"+trophieID+"' and Username='" + user +"'",null );
            db.setTransactionSuccessful();
            db.endTransaction();
        }else{
            //new DB Entry
            int lastID=0;
            c= db.rawQuery("select * from trophies_user where ID = (select max(ID) from trophies_user)",null);
            if(c.moveToFirst()) {
                lastID = c.getInt(c.getColumnIndex("ID"));
            }else{
                lastID=1;
            }
            ContentValues values = new ContentValues();
            db.beginTransaction();

            values.put("ID",lastID+1);
            values.put("Username",user); //noch beachten
            values.put("TR_ID",trophieID);
            values.put("Done",1);

            db.insert("trophies_user",null, values );
            db.setTransactionSuccessful();
            db.endTransaction();
        }
    }

    /**
     * prüft, ob Trophy freigeschaltet
     * @param u für welchen User, Typ String
     * @return ArrayList mit allen Trophy_IDs, wenn freigeschaltet
     */
    public ArrayList<Integer> checkForTrophie(String u){
        ArrayList<Integer> trophies = new ArrayList<Integer>();
        int tid;
        int done;
        int need;
        c = db.rawQuery("Select * from trophies_user where Username='" + u +"'",null);
        while(c.moveToNext()){
            tid = c.getInt((c.getColumnIndex("TR_ID")));
            done = c.getInt(c.getColumnIndex("Done"));
            Cursor cc = db.rawQuery("select * from trophies where TR_ID='" + tid +"'", null);
            if(cc.moveToNext()){
                need = cc.getInt(cc.getColumnIndex("Need"));

                if(done >= need){
                    trophies.add(c.getInt(c.getColumnIndex("TR_ID")));
                }
            }
        }


        return trophies;
    }


}
