package com.example.heidrun.bak_project_learnquest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

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
           // c.moveToLast();
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

    public String checkForTrophie(String u){
        String t="";
        c = db.rawQuery("Select * from trophies_user where Username='" + u +"'",null);
        while(c.moveToNext()){
            t = c.getString(c.getColumnIndex("Done"));
        }


        return t;
    }


}
