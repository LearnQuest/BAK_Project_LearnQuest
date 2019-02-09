package com.example.heidrun.bak_project_learnquest;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseAccess {

    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase db;
    private static DatabaseAccess instance;
    Cursor c = null;

    private DatabaseAccess(Context context){
        this.openHelper = new DatabaseOpenHelper(context);
    }

    public static DatabaseAccess getInstance(Context context){
        if(instance == null){
            instance=new DatabaseAccess(context);
        }
        return instance;
    }

    public void open(){
        this.db=openHelper.getWritableDatabase();
    }

    public void close(){
        if(db!=null){
            this.db.close();
        }
    }

    public ArrayList<String> getSubjects(){
        ArrayList<String> arrSub = new ArrayList<String>();
        c = db.rawQuery("select Fach from fach", null);

        while(c.moveToNext()){
            arrSub.add(c.getString(0));
        }
        return arrSub;
    }

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



}
