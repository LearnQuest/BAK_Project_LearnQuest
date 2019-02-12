package com.example.heidrun.bak_project_learnquest;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class MySharedPreference {

    final static String PREF_IMAGE = "Image";

    static SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    /**
     * hier möchte ich ein Image "speichern" -> einzusetzedes Profilbild
     * @param ctx
     * @param image
     */
    public static void setPrefImage(Context ctx, int image) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putInt(PREF_IMAGE, image);
        editor.apply();
    }

    public static int getPrefImage(Context ctx) {
        return getSharedPreferences(ctx).getInt(PREF_IMAGE, R.drawable.hinzufuegen);
    }
}
