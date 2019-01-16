package com.example.heidrun.bak_project_learnquest;

import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class subjectFragment extends Fragment {

    //ListView listView;
    private RecyclerView mRecyclerView;
    //muss später geändert werden f. DB
    ArrayList<Subjects> dataModels;
    private static CustomAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.subject_fragment, container, false);

        mRecyclerView=(RecyclerView) view.findViewById(R.id.idRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        dataModels= new ArrayList<>();
        dataModels.add(new Subjects("DBS01"));
        dataModels.add(new Subjects("DBS02"));
        dataModels.add(new Subjects("SWE01"));
        dataModels.add(new Subjects("SWE02"));
        dataModels.add(new Subjects("DBS03"));
        dataModels.add(new Subjects("DBS04"));
        dataModels.add(new Subjects("SWE03"));
        dataModels.add(new Subjects("SWE04"));
        dataModels.add(new Subjects("Test"));
        dataModels.add(new Subjects("Test"));
        dataModels.add(new Subjects("TEst"));
        dataModels.add(new Subjects("test"));
        dataModels.add(new Subjects("TEST"));

        adapter= new CustomAdapter(dataModels, getContext());

        mRecyclerView.setAdapter(adapter);

       /* mRecyclerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Subjects dataModel= dataModels.get(position);

                Snackbar.make(view, dataModel.getSubjectName(), Snackbar.LENGTH_LONG)
                        .setAction("No action", null).show();
            }
        });
*/
        return view;
    }
}
