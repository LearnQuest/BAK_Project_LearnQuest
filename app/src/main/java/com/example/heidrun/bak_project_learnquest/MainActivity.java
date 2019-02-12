package com.example.heidrun.bak_project_learnquest;

import android.app.Dialog;

import android.content.SharedPreferences;
import android.support.v4.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Dialog chooseCharacterDialog;
    private static final String MY_PREFS_NAME ="LearnQuest_Pref_Subject";
    private DrawerLayout drawer;
    ArrayList<Question> QuestionArrayForFragment;
    subjectFragment subfragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        String uname = getIntent().getStringExtra("Username");
        String mail = getIntent().getStringExtra("Email");
        SharedPreferences pref ;
        pref = this.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("Email", mail);
        editor.apply();

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Startseite");

        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        LinearLayout navHeader = findViewById(R.id.nav_header_layout);


        View headerView = navigationView.getHeaderView(0);
        TextView u = (TextView) headerView.findViewById(R.id.Username);
        TextView m = (TextView) headerView.findViewById(R.id.Email);
         u.setText(uname);
         m.setText(mail);

         ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close){
          public void onDrawerSlide(View drawerView, float slideOffset){
              ((ImageView)((LinearLayout)(findViewById(R.id.nav_header_layout))).findViewById(R.id.imageUser)).setImageResource(MySharedPreference.getPrefImage(getBaseContext()));

          }
        };
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        //Datenbank öffnen
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter("custom-message"));

        subfragment = new subjectFragment();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new Fragment_maps_class()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }

        chooseCharacterDialog = new Dialog(this);
        chooseCharacterDialog.setContentView(R.layout.activity_choose_character);

    }

    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
         Bundle b = intent.getExtras();
            QuestionArrayForFragment = (ArrayList<Question>)b.getSerializable("questions");
        }
    };

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                //to send QuestionArray to MapsFragment
                Bundle bundle = new Bundle();
                bundle.putSerializable("questions", QuestionArrayForFragment);
            // set MyFragment Arguments
                Fragment_maps_class myObj = new Fragment_maps_class();
                myObj.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        myObj).commit();
                break;

            case R.id.action_help:
                final Dialog d = new Dialog(MainActivity.this);
                d.setTitle("Help");
                d.setContentView(R.layout.dialog_help);
                d.show();

                TextView tv = d.findViewById(R.id.textView7);
                tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        d.cancel();
                    }
                });

            case R.id.nav_subjects:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new subjectFragment()).commit();
                break;
            case R.id.nav_trophies:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new trophiesFragment()).commit();
                break;
            case R.id.action_logout:
                Intent intent = new Intent(this, loginActivity.class);
                startActivity(intent);
                finish();
                break;


        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
                Intent intent = new Intent(this, MainActivity.class);
                this.finish();
                startActivity(intent);
        }
    }




    public void onClickOpenDialogue(View view) {
        chooseCharacterDialog.show();


    }

    public void onClickChangeProfile(View view) {
        int imageID;
        switch (view.getId()) {
            case R.id.loewe:
                imageID = R.drawable.loewe;
                break;
            case R.id.fuchs:
                imageID = R.drawable.fuchs;
                break;
            case R.id.katze:
                imageID = R.drawable.katze_1;
                break;
            case R.id.einhorn:
                imageID = R.drawable.einhorn_1;
                break;
            default:
                imageID = R.drawable.userdefault;
                break;
        }
        ((ImageView) findViewById(R.id.imageUser)).setImageResource(imageID);
        MySharedPreference.setPrefImage(this,imageID);

        ((ImageView) findViewById(R.id.imageUser)).invalidate();
        chooseCharacterDialog.dismiss();
    }
}

