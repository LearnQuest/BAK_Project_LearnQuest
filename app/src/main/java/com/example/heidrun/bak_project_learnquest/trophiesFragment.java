package com.example.heidrun.bak_project_learnquest;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

/**
 * Klasse f. Trophy-Fragment
 */
public class trophiesFragment extends Fragment implements View.OnClickListener {

    private ImageView one;
    private ImageView two;
    private ImageView three;
    private ImageView four;
    private ImageView five;
    private ImageView six;
    private ImageView seven;
    private ImageView eigth;
    private ImageView nine;
    private ImageView ten;
    private ImageView eleven;
    private ImageView twelve;

    private final static String MY_PREFS_NAME = "LearnQuest_Pref_Subject";

    /**
     * erzeugt den Inhalt des Fragments, OnClickListener für alle ImageViews werden zugewiesen
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View myFragmentView = inflater.inflate(R.layout.trophies_fragment, container, false);

        one = (ImageView) myFragmentView.findViewById(R.id.troph1);
        two = (ImageView) myFragmentView.findViewById(R.id.troph2);
        three = (ImageView) myFragmentView.findViewById(R.id.troph3);
        four = (ImageView) myFragmentView.findViewById(R.id.troph4);

        five = (ImageView) myFragmentView.findViewById(R.id.troph5);
        six = (ImageView) myFragmentView.findViewById(R.id.troph6);
        seven = (ImageView) myFragmentView.findViewById(R.id.troph7);
        eigth = (ImageView) myFragmentView.findViewById(R.id.troph8);

        nine = (ImageView) myFragmentView.findViewById(R.id.troph10);
        ten = (ImageView) myFragmentView.findViewById(R.id.troph11);
        eleven = (ImageView) myFragmentView.findViewById(R.id.troph12);
        twelve = (ImageView) myFragmentView.findViewById(R.id.troph13);

        one.setOnClickListener(this);
        two.setOnClickListener(this);
        three.setOnClickListener(this);
        four.setOnClickListener(this);

        five.setOnClickListener(this);
        six.setOnClickListener(this);
        seven.setOnClickListener(this);
        eigth.setOnClickListener(this);

        nine.setOnClickListener(this);
        ten.setOnClickListener(this);
        eleven.setOnClickListener(this);
        twelve.setOnClickListener(this);

        one.setTag(R.drawable.ic_lock_black_24dp);
        two.setTag(R.drawable.ic_lock_black_24dp);
        three.setTag(R.drawable.ic_lock_black_24dp);
        four.setTag(R.drawable.ic_lock_black_24dp);
        five.setTag(R.drawable.ic_lock_black_24dp);
        six.setTag(R.drawable.ic_lock_black_24dp);
        seven.setTag(R.drawable.ic_lock_black_24dp);
        eigth.setTag(R.drawable.ic_lock_black_24dp);
        nine.setTag(R.drawable.ic_lock_black_24dp);
        ten.setTag(R.drawable.ic_lock_black_24dp);
        eleven.setTag(R.drawable.ic_lock_black_24dp);
        twelve.setTag(R.drawable.ic_lock_black_24dp);


        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(myFragmentView.getContext());
        databaseAccess.open();
        SharedPreferences prefs = getContext().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String v = prefs.getString("Email", "");
        ArrayList<Integer> trophie = databaseAccess.checkForTrophie(v);
        for (int i = 0; i < trophie.size(); i++) {
            switch (trophie.get(i)) {
                case 1:
                    one.setImageResource(R.drawable.trophie8);
                    one.setTag(R.drawable.trophie8);
                    break;
                case 2:
                    two.setImageResource(R.drawable.trophie2);
                    two.setTag(R.drawable.trophie2);
                    break;
                case 3:
                    three.setImageResource(R.drawable.trophie3);
                    three.setTag(R.drawable.trophie3);
                    break;
                case 4:
                    four.setImageResource(R.drawable.trophie4);
                    four.setTag(R.drawable.trophie4);
                    break;
                case 5:
                    five.setImageResource(R.drawable.trophie5);
                    five.setTag(R.drawable.trophie5);
                    break;
                case 6:
                    six.setImageResource(R.drawable.trophie6);
                    six.setTag(R.drawable.trophie6);
                    break;
                case 7:
                    seven.setImageResource(R.drawable.trophie7);
                    seven.setTag(R.drawable.trophie7);
                    break;
                case 8:
                    eigth.setImageResource(R.drawable.trophie1);
                    eigth.setTag(R.drawable.trophie1);
                    break;
                case 9:
                    nine.setImageResource(R.drawable.trophie9);
                    nine.setTag(R.drawable.trophie9);
                    break;
                default:
                    Snackbar.make(myFragmentView, "Diese Trophäe ist nocht in Arbeit!", Snackbar.LENGTH_LONG)
                            .setAction("No action", null).show();            }
        }
        return myFragmentView;
    }

    /**
     * OnClick Event für ImageView
     * @param view
     */
    @Override
    public void onClick(View view) {

        ImageView iv = (ImageView) view;

        Integer dr = (Integer) iv.getTag();

        ObjectAnimator rotation = ObjectAnimator.ofFloat(iv, "rotation", 0f, 360f);
        rotation.setDuration(1000);

        AnimatorSet animSet = new AnimatorSet();
        animSet.playTogether(rotation);
        animSet.start();

        if (dr.equals(R.drawable.ic_lock_black_24dp)) {

            Snackbar.make(view, "Du hast diese Trophäe noch nicht freigeschaltet!", Snackbar.LENGTH_LONG)
                    .setAction("No action", null).show();
        } else {

            Snackbar.make(view, "Du hast diese Trophäe freigeschaltet!", Snackbar.LENGTH_LONG)
                    .setAction("No action", null).show();
        }

    }

}
