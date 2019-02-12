package com.example.heidrun.bak_project_learnquest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.sql.SQLOutput;
import java.util.zip.Inflater;

public class chooseCharacter extends AppCompatActivity {
    ImageView image;
    ImageButton button;

    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_character);

    }

    /**
     *
     * @param view
     */
    public void onClickChangeProfile(View view) {

        Intent i = new Intent(chooseCharacter.this, MainActivity.class);
        i.putExtra("resId", R.drawable.loewe);
        startActivity(i);

    }

}
