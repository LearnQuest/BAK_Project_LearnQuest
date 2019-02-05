package com.example.heidrun.bak_project_learnquest;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
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

public class trophiesFragment extends Fragment implements View.OnClickListener {

    ImageView one;
    ImageView two;
    ImageView three;
    ImageView four;
    ImageView five;
    ImageView six;
    ImageView seven;
    ImageView eigth;
    ImageView nine;
    ImageView ten;
    ImageView eleven;
    ImageView twelve;

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


        //aktuelle Trophies anzeigen
        //tag setzen!

        return myFragmentView;
    }

    @Override
    public void onClick(View view) {

        ImageView iv = (ImageView) view;

        Integer dr = (Integer) iv.getTag();

        if (dr.equals(R.drawable.ic_lock_black_24dp)) {

            ObjectAnimator rotation = ObjectAnimator.ofFloat(iv,"rotation", 0f, 360f);
            rotation.setDuration(1000);

            AnimatorSet animSet = new AnimatorSet();
            animSet.playTogether(rotation);
            animSet.start();

            Snackbar.make(view, "Du hast diese Trophäe noch nicht freigeschaltet!", Snackbar.LENGTH_LONG)
                    .setAction("No action", null).show();

        } //else {
        //text aus DB anzeigen, was du erreicht hast
            //Toast.makeText(getContext(), "unlocked", Toast.LENGTH_SHORT).show();
        //}

    }

}
