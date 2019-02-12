package com.example.heidrun.bak_project_learnquest;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.airbnb.lottie.LottieAnimationView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import static android.content.Context.MODE_PRIVATE;

/**
 * Klasse für das Question-Fragment
 */
public class questionFragment extends Fragment implements View.OnClickListener {

    private ToggleButton one;
    private ToggleButton two;
    private ToggleButton three;
    private ToggleButton four;
    private Button check;
    private TextView q;
    ArrayList<String> sub = new ArrayList<>();
    private final static String MY_PREFS_NAME = "LearnQuest_Pref_Subject";

    private String AnswerSelected;

    ArrayList<Question> Questions;
    private DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getContext());

    /**
     * Fragment QUestion wird erzeugt, Question aus zuvor befüllter ArrayList wird randomisiert angezeigt
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View myFragmentView = inflater.inflate(R.layout.question_fragement, container, false);
        try {
            if (getArguments() != null) {
                ArrayList<Question> ArrQuestion = (ArrayList<Question>) getArguments().getSerializable("questions");
                if (ArrQuestion != null) {
                    Questions = ArrQuestion;
                } else {
                    Questions = new ArrayList<Question>();
                }
            } else {
                Questions = new ArrayList<Question>();
            }
            //GetQuestion!

            q = myFragmentView.findViewById((R.id.Question));
            one = myFragmentView.findViewById(R.id.Answ1);
            two = myFragmentView.findViewById(R.id.Answ2);
            three = myFragmentView.findViewById(R.id.Answ3);
            four = myFragmentView.findViewById(R.id.Answ4);
            check = myFragmentView.findViewById(R.id.check);

            if (Questions.size() != 0) {
                //select Question
                Random randomGenerator = new Random();
                final int index = randomGenerator.nextInt(Questions.size());

                q.setText(Questions.get(index).question);
                one.setText(Questions.get(index).a1);
                one.setTextOff(Questions.get(index).a1);
                one.setTextOn(Questions.get(index).a1);
                two.setTextOff(Questions.get(index).a2);
                two.setTextOn(Questions.get(index).a2);
                two.setText(Questions.get(index).a2);
                three.setTextOff(Questions.get(index).a3);
                three.setTextOn(Questions.get(index).a3);
                three.setText(Questions.get(index).a3);
                four.setTextOff(Questions.get(index).a4);
                four.setTextOn(Questions.get(index).a4);
                four.setText(Questions.get(index).a4);

                one.setOnClickListener(this);
                two.setOnClickListener(this);
                three.setOnClickListener(this);
                four.setOnClickListener(this);

                check.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        databaseAccess.open();

                        boolean checked = databaseAccess.getCheckedAnswer(q.getText().toString(), AnswerSelected);

                        if (checked) {
                            if (Questions.size() != 0) {
                                Questions.remove(index);
                            }
                            System.out.println(index);


                            Bundle b = new Bundle();
                            b.putSerializable("questions", (Serializable) Questions);
                            Intent intent = new Intent("custom-message");
                            intent.putExtras(b);

                            //write to database
                            SharedPreferences prefs = getContext().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
                            String v = prefs.getString("Email", "");
                            databaseAccess.writeToTrophie(1, v);
                            databaseAccess.writeToTrophie(2, v);
                            databaseAccess.writeToTrophie(3, v);

                            LocalBroadcastManager.getInstance(getContext()).sendBroadcast(intent);
                            final Dialog d = new Dialog(getContext());
                            d.setTitle("Help");
                            d.setContentView(R.layout.succsessdialog);
                            d.show();
                            d.setCancelable(false);

                            TextView next = d.findViewById(R.id.next);
                            next.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    d.hide();

                                    if (Questions.size() == 0) {
                                        SharedPreferences prefs = getContext().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
                                        String ff = prefs.getString("CourseName", "");
                                        if (!sub.contains(ff) && !ff.equals("")) {
                                            sub.add(ff);
                                            String vv = prefs.getString("Email", "");
                                            databaseAccess.writeToTrophie(5, vv);
                                            databaseAccess.writeToTrophie(6, vv);
                                        }
                                        final Dialog d = new Dialog(getContext());
                                        d.setTitle("Help");
                                        d.setContentView(R.layout.allquestionsdone_dialog);
                                        d.show();
                                        d.setCancelable(false);

                                        TextView next = d.findViewById(R.id.allDone);
                                        next.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                d.hide();

                                                Bundle bundle = new Bundle();
                                                bundle.putSerializable("questions", Questions);

                                                Fragment_maps_class myObj = new Fragment_maps_class();
                                                myObj.setArguments(bundle);
                                                getFragmentManager().beginTransaction().replace(R.id.fragment_container, myObj).commit();
                                            }
                                        });
                                    } else {
                                        Bundle bundle = new Bundle();
                                        bundle.putSerializable("questions", Questions);

                                        Fragment_maps_class myObj = new Fragment_maps_class();
                                        myObj.setArguments(bundle);
                                        getFragmentManager().beginTransaction().replace(R.id.fragment_container, myObj).commit();
                                    }
                                }
                            });


                        } else {
                            //Franci Dialog
                            final Dialog d = new Dialog(getContext());
                            d.setTitle("Help");
                            d.setContentView(R.layout.lossdialog);
                            d.show();
                            d.setCancelable(false);

                            TextView next = d.findViewById(R.id.tryAgain);
                            next.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    d.hide();
                                    Bundle bundle = new Bundle();
                                    bundle.putSerializable("questions", Questions);

                                    Fragment_maps_class myObj = new Fragment_maps_class();
                                    myObj.setArguments(bundle);
                                    getFragmentManager().beginTransaction().replace(R.id.fragment_container, myObj).commit();
                                }
                            });

                        }
                    }
                });

            }
        } catch (Exception ex) {
            Toast.makeText(getContext(), getString(R.string.ex), Toast.LENGTH_SHORT).show();
        }
        return myFragmentView;
    }

    /**
     * Methode/Event, dass nur 1 Antwort ausgewählt werden kann
     */
    CompoundButton.OnCheckedChangeListener changeChecker = new CompoundButton.OnCheckedChangeListener() {

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                if (buttonView == one) {
                    AnswerSelected = one.getText().toString();
                    two.setChecked(false);
                    three.setChecked(false);
                    four.setChecked(false);
                }
                if (buttonView == two) {
                    AnswerSelected = two.getText().toString();
                    one.setChecked(false);
                    three.setChecked(false);
                    four.setChecked(false);
                }
                if (buttonView == three) {
                    AnswerSelected = three.getText().toString();
                    two.setChecked(false);
                    one.setChecked(false);
                    four.setChecked(false);
                }
                if (buttonView == four) {
                    AnswerSelected = four.getText().toString();
                    two.setChecked(false);
                    three.setChecked(false);
                    one.setChecked(false);
                }
            }
        }
    };


    /**
     * OnClick Event für die Antwort-Toggle Buttons
     *
     * @param view
     */
    @Override
    public void onClick(View view) {
        try {
            switch (view.getId()) {

                case R.id.Answ1:
                    if (one.isChecked()) {
                        AnswerSelected = one.getText().toString();
                        two.setChecked(false);
                        three.setChecked(false);
                        four.setChecked(false);
                    }
                    break;

                case R.id.Answ2:
                    if (two.isChecked()) {
                        AnswerSelected = two.getText().toString();
                        one.setChecked(false);
                        three.setChecked(false);
                        four.setChecked(false);
                    }
                    break;

                case R.id.Answ3:
                    if (three.isChecked()) {
                        AnswerSelected = three.getText().toString();
                        two.setChecked(false);
                        one.setChecked(false);
                        four.setChecked(false);
                    }
                    break;

                case R.id.Answ4:
                    if (four.isChecked()) {
                        AnswerSelected = four.getText().toString();
                        two.setChecked(false);
                        three.setChecked(false);
                        one.setChecked(false);
                    }
                    break;

                default:
                    break;
            }
        } catch (Exception ex) {
            Toast.makeText(getContext(), getString(R.string.ex), Toast.LENGTH_SHORT).show();
        }
    }


}
