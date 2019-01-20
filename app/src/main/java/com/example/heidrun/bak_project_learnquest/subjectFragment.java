package com.example.heidrun.bak_project_learnquest;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class subjectFragment extends Fragment {

    //ListView listView;
    private RecyclerView mRecyclerView;
    //muss später geändert werden f. DB
    ArrayList<Subject> dataModels;
    private static CustomAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.subject_fragment, container, false);

        mRecyclerView=(RecyclerView) view.findViewById(R.id.idRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        dataModels = DBConnector.getSubjects();
        /*dataModels.add(new Subject("DBS01"));
        dataModels.add(new Subject("DBS02"));
        dataModels.add(new Subject("SWE01"));
        dataModels.add(new Subject("SWE02"));
        dataModels.add(new Subject("DBS03"));
        dataModels.add(new Subject("DBS04"));
        dataModels.add(new Subject("SWE03"));
        dataModels.add(new Subject("SWE04"));
        dataModels.add(new Subject("Test"));
        dataModels.add(new Subject("Test"));
        dataModels.add(new Subject("TEst"));
        dataModels.add(new Subject("test"));
        dataModels.add(new Subject("TEST"));*/

        adapter= new CustomAdapter(dataModels, getContext());

        mRecyclerView.setAdapter(adapter);

       /* mRecyclerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Subject dataModel= dataModels.get(position);

                Snackbar.make(view, dataModel.getSubjectName(), Snackbar.LENGTH_LONG)
                        .setAction("No action", null).show();
            }
        });
*/
        return view;
    }
}
