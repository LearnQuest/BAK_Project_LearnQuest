package com.example.heidrun.bak_project_learnquest;

import android.app.Dialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Dialog chooseCharacterDialog;
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Startseite");

        // getSupportActionBar();
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        LinearLayout navHeader = findViewById(R.id.nav_header_layout);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close){
          public void onDrawerSlide(View drawerView, float slideOffset){
              ((ImageView)((LinearLayout)(findViewById(R.id.nav_header_layout))).findViewById(R.id.imageUser)).setImageResource(MySharedPreference.getPrefImage(getBaseContext()));

          }
        };
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new Fragment_maps_class()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }

        chooseCharacterDialog = new Dialog(this);
        chooseCharacterDialog.setContentView(R.layout.activity_choose_character);

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new Fragment_maps_class()).commit();
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
            super.onBackPressed();
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

