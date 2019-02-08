package com.example.heidrun.bak_project_learnquest;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class DatabaseOpenHelper extends SQLiteAssetHelper
{
    private static final String Database_Name="LearnQuest_DB_1.db";
    private static final int Database_version=1;

    public DatabaseOpenHelper(Context context) {
        super(context, Database_Name, null, Database_version);
    }
}
