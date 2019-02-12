package com.example.heidrun.bak_project_learnquest;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
 * Helperklasse, welche DB verwendet werden soll mit welcher Version
 */
public class DatabaseOpenHelper extends SQLiteAssetHelper
{
    private static final String Database_Name="LearnQuest_DB_v2.db";
    private static final int Database_version=1;

    public DatabaseOpenHelper(Context context) {
        super(context, Database_Name, null, Database_version);
    }
}
