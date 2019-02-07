package com.example.heidrun.bak_project_learnquest;

import android.app.Dialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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

        // NullPointerException (!!!!????)
        /*ImageView profile = findViewById(R.id.imageUser);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            int resId = bundle.getInt("resId");
            System.out.println(resId);
            profile.setImageResource(resId);
        }*/

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new Fragment_maps_class()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }
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
        /*ImageView profile = findViewById(R.id.imageUser);
        Intent intent = new Intent(this, chooseCharacter.class);
        startActivity(intent);*/

        // custom dialog
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.activity_choose_character);
        dialog.setTitle("Chosse your Character...");

        ImageButton loewe = dialog.findViewById(R.id.loewe);
        // if button is clicked, close the custom dialog
        loewe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ImageView) findViewById(R.id.imageUser)).setImageResource(R.drawable.fuchs);
                ((ImageView) findViewById(R.id.imageUser)).invalidate();
                dialog.dismiss();
            }
        });

        dialog.show();

        /*
        Button einhorn = f.findViewById(R.id.imageButtonEinhorn);
        Button loewe = f.findViewById(R.id.imageButtonLoewe);
        Button katze = f.findViewById(R.id.imageButtonKatze);
        Button fuchs = f.findViewById(R.id.imageButtonFuchs);
        ImageView profilbild = f.findViewById(R.id.imageUser);

        if (einhorn.isSelected()) {
            profilbild.setImageResource(R.drawable.einhorn_1);
        } else if (loewe.isSelected()) {
            profilbild.setImageResource(R.drawable.loewe);
        } else if (katze.isSelected()) {
            profilbild.setImageResource(R.drawable.katze_1);
        } else if (fuchs.isSelected()) {
            profilbild.setImageResource(R.drawable.fuchs);
        }*/

    }
}

