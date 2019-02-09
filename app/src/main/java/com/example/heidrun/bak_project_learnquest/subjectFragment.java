package com.example.heidrun.bak_project_learnquest;

import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getActivity().getApplicationContext());
        databaseAccess.open();

        dataModels = new ArrayList<Subjects>();
        for(String s :databaseAccess.getSubjects()){
            if(s!= null){
            dataModels.add(new Subjects(s));}
        }


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
