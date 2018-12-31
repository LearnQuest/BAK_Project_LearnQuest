package com.example.heidrun.bak_project_learnquest;

import android.media.Image;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

public class trophiesFragment extends Fragment implements View.OnClickListener{

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

        return myFragmentView;
    }

    @Override
    public void onClick(View view) {

        Toast.makeText(getContext(), "test", Toast.LENGTH_LONG).show();


    }

}
