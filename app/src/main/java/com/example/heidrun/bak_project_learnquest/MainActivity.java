package com.example.heidrun.bak_project_learnquest;

import android.app.Dialog;

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
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    ArrayList<Question> QuestionArrayForFragment;
    subjectFragment subfragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View parentLayout = findViewById(android.R.id.content);
       // Snackbar.make(parentLayout, "Login war erfolgreich!", Snackbar.LENGTH_LONG)
       //         .setAction("No action", null).show();
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Home");
       // getSupportActionBar();
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
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

    }

    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
         Bundle b = intent.getExtras();
            QuestionArrayForFragment = (ArrayList<Question>)b.getSerializable("questions");

            //Toast.makeText(MainActivity.this,String.valueOf(a.size()) ,Toast.LENGTH_SHORT).show();
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

                TextView tv= d.findViewById(R.id.textView7);
                tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                      d.cancel();
                    }
                });
            case R.id.action_logout:
                Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_subjects:




                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new subjectFragment()).commit();
                break;
            case R.id.nav_trophies:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new trophiesFragment()).commit();
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

                startActivity(intent);
        }
    }


    public void onClickOpenDialogue(View view){
        final Dialog f = new Dialog(MainActivity.this);
        f.setTitle("Choose your character");
        f.setContentView(R.layout.activity_choose_character);
        f.show();
       }


}

