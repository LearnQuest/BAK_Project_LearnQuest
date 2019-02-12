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

/**
 * Klasse, des SubjectFragments
 */
public class subjectFragment extends Fragment {

    private RecyclerView mRecyclerView;
    ArrayList<Subjects> dataModels;
    private static CustomAdapter adapter;

    /**
     * tritt ein, wenn Fragment erzeugt/geladen wird, Adapter des RecyclerView wird mithilfe von CustomAdapter-Objekt befüllt
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.subject_fragment, container, false);
        try {
            mRecyclerView = (RecyclerView) view.findViewById(R.id.idRecyclerView);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

            DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getActivity().getApplicationContext());
            databaseAccess.open();

            dataModels = new ArrayList<Subjects>();
            for (String s : databaseAccess.getSubjects()) {
                if (s != null) {
                    dataModels.add(new Subjects(s));
                }
            }

            adapter = new CustomAdapter(dataModels, getContext());

            mRecyclerView.setAdapter(adapter);
        } catch (Exception ex) {
            Toast.makeText(getContext(), getString(R.string.ex), Toast.LENGTH_SHORT).show();
        }

        return view;
    }


}
