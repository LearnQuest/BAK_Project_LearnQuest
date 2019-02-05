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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_character);
      /*
        e.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                image = (ImageView) findViewById(R.id.imageUser);
                image.setImageDrawable(getResources().getDrawable(R.drawable.einhorn_1));
            }
        });
        ImageButton f = findViewById(R.id.imageButtonFuchs);
        f.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                System.out.println("hier");
                image.setImageResource(R.drawable.fuchs);
            }
        });
        ImageButton l = findViewById(R.id.imageButtonLoewe);
        l.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                image = (ImageView) findViewById(R.id.imageUser);
                image.setImageResource(R.drawable.loewe);
            }
        });
        ImageButton k = findViewById(R.id.imageButtonKatze);
        k.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                image = (ImageView) findViewById(R.id.imageUser);
                image.setImageResource(R.drawable.katze_1);
            }
        });*/
    }

    public void onClickChangeProfile(View view) {

        Intent i = new Intent(chooseCharacter.this, MainActivity.class);
        i.putExtra("resId",R.drawable.loewe);
        startActivity(i);
        /*ImageView profile = (ImageView) view.findViewById(R.id.imageUser);
        System.out.println("Hello");
        profile.setImageResource(R.drawable.loewe);*/
    }

   /* public void onClickEinhorn(View view) {
        System.out.println("HIHI");

        image = (ImageView) findViewById(R.id.imageUser);
        image.setImageResource(R.drawable.einhorn_1);
    }

    public void onClickfuchs(View view) {
        ImageView profile = findViewById(R.id.imageUser);
        profile.setImageResource(R.drawable.fuchs);
    }

    public void onClickloewe(View view) {
        ImageView profile = findViewById(R.id.imageUser);
        profile.setImageResource(R.drawable.loewe);
    }

    public void onClickkatze(View view) {
        ImageView profile = findViewById(R.id.imageUser);
        profile.setImageResource(R.drawable.katze_1);
    }*/
}
