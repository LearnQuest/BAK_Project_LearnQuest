package com.example.heidrun.bak_project_learnquest;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class loginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

    }

    public void onClickSwitchtoMain(View view) {
        CardView cardView = (CardView) findViewById(R.id.cardview);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void onClickOpenURL(View view){
        String url = "https://pwms.fh-joanneum.at/";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }
    public void openDialog(View view){
        final Dialog d = new Dialog(this);
        d.setTitle("Help");
        d.setContentView(R.layout.fragezeichendalog);
        d.show();

        TextView tv = d.findViewById(R.id.ok);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.cancel();
            }
        });
    }
}
