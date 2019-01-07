package com.example.heidrun.bak_project_learnquest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.EditText;

public class loginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

   public void onClickSwitchtoMain(View view){
        CardView cardView = (CardView) findViewById(R.id.cardview);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
