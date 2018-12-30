package com.example.heidrun.bak_project_learnquest;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;



public class splash_screen extends AppCompatActivity {
private static int splash_time_out= 4000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent homeIntent = new Intent(splash_screen.this, MainActivity.class);
                startActivity(homeIntent);
                finish();

            }
        }, splash_time_out);

    };
}
