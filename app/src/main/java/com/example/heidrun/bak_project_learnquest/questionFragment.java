package com.example.heidrun.bak_project_learnquest;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.airbnb.lottie.LottieAnimationView;

public class questionFragment extends Fragment implements View.OnClickListener {

    ToggleButton one;
    ToggleButton two;
    ToggleButton three;
    ToggleButton four;
    Button check;
    LottieAnimationView lottieView;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View myFragmentView = inflater.inflate(R.layout.question_fragement, container, false);
        one = myFragmentView.findViewById(R.id.Answ1);
        two = myFragmentView.findViewById(R.id.Answ2);
        three = myFragmentView.findViewById(R.id.Answ3);
        four = myFragmentView.findViewById(R.id.Answ4);
        check = myFragmentView.findViewById(R.id.check);
        lottieView = myFragmentView.findViewById(R.id.success);

        one.setOnClickListener(this);
        two.setOnClickListener(this);
        three.setOnClickListener(this);
        four.setOnClickListener(this);

        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lottieView.setVisibility(View.VISIBLE);
            }
        });

      /* one.setOnCheckedChangeListener(changeChecker);
       two.setOnCheckedChangeListener(changeChecker);
       three.setOnCheckedChangeListener(changeChecker);
       four.setOnCheckedChangeListener(changeChecker);*/

        return myFragmentView;
    }

    CompoundButton.OnCheckedChangeListener changeChecker = new CompoundButton.OnCheckedChangeListener() {

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                if (buttonView == one) {
                    two.setChecked(false);
                    three.setChecked(false);
                    four.setChecked(false);
                }
                if (buttonView == two) {
                    one.setChecked(false);
                    three.setChecked(false);
                    four.setChecked(false);
                }
                if (buttonView == three) {
                    two.setChecked(false);
                    one.setChecked(false);
                    four.setChecked(false);
                }
                if (buttonView == four) {
                    two.setChecked(false);
                    three.setChecked(false);
                    one.setChecked(false);
                }
            }
        }
    };


    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.Answ1:
                if (one.isChecked()) {
                    two.setChecked(false);
                    three.setChecked(false);
                    four.setChecked(false);
                }
                break;

            case R.id.Answ2:
                if (two.isChecked()) {
                    one.setChecked(false);
                    three.setChecked(false);
                    four.setChecked(false);
                }
                break;

            case R.id.Answ3:
                if (three.isChecked()) {
                    two.setChecked(false);
                    one.setChecked(false);
                    four.setChecked(false);
                }
                break;

            case R.id.Answ4:
                if (four.isChecked()) {
                    two.setChecked(false);
                    three.setChecked(false);
                    one.setChecked(false);
                }
                break;

            default:
                break;
        }
    }





    /**/


}
