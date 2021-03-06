package com.example.heidrun.bak_project_learnquest;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import gr.net.maroulis.library.EasySplashScreen;

/**
 * Klasse von splashscreen Activity
 */
public class splash_screen extends AppCompatActivity {
    private static int splash_time_out = 4000;

    /**
     * Splashscreen wird erzeugt, danach auf Login weitergeleitet
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_splash_screen);
            Thread myThread = new Thread() {
                @Override
                public void run() {
                    try {
                        sleep(3000);
                        Intent intent = new Intent(getApplicationContext(), loginActivity.class);
                        intent.setFlags(intent.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY); // Adds the FLAG_ACTIVITY_NO_HISTORY flag
                        startActivity(intent);
                        finish();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            myThread.start();
        } catch (Exception ex) {
            Toast.makeText(this, getString(R.string.ex), Toast.LENGTH_SHORT).show();
        }

    }
}
