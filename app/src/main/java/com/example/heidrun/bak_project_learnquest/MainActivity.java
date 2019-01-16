package com.example.heidrun.bak_project_learnquest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //hi test this push function

        questionFragment frg = new questionFragment();
       // getSupportFragmentManager().beginTransaction().add(R.id.container, frg).commit();

        trophiesFragment troph = new trophiesFragment();
        //getSupportFragmentManager().beginTransaction().add(R.id.container, troph).commit();

        subjectFragment subj = new subjectFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.container, subj).commit();

    }

}
