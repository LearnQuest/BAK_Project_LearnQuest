package com.example.heidrun.bak_project_learnquest;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

/**
 * Klasse, zum Befüllen des Subjectfragments mit Daten aus der Datenbank
 */
public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> implements View.OnClickListener {

    private ArrayList<Subjects> dataSet;
    private Context mContext;
    private ImageButton lastSelected;
    boolean test = false;
    int positiongedrückt = -1;
    private SharedPreferences pref;
    private ArrayList<String> sub = new ArrayList<String>();
    private final static String MY_PREFS_NAME = "LearnQuest_Pref_Subject";

    /**
     * Konstruktor dieser Klasse
     *
     * @param data    enthält alle Subjects aus der DB
     * @param context Context fron dem Subjectfragment übergeben
     */
    public CustomAdapter(ArrayList<Subjects> data, Context context) {
        this.dataSet = data;
        this.mContext = context;
    }

    /**
     * erstellt einen Viewholder für Recyclerview
     *
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflate the layout file
        View groceryProductView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_row, parent, false);
        ViewHolder gvh = new ViewHolder(groceryProductView);

        return gvh;
    }

    /**
     * aktualisiert die Elemente im Reclyclerview mit den richtigen Werten
     * Shared Prefs werden verwendet, um ausgewähltes Fach beibehalten zu können
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        try {
            holder.subName.setText(dataSet.get(position).getSubjectName());
            holder.selButton.setOnClickListener(this);
            holder.selButton.setTag(position);
            holder.infoButton.setOnClickListener(this);
            holder.infoButton.setTag(position);

            SharedPreferences prefs = mContext.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
            int pageNumber = prefs.getInt("PositionSelected", -1);
            if (pageNumber != -1 && pageNumber == position) {
                //gewähltes Fach wieder aktivieren!
                holder.selButton.setImageResource(R.drawable.ic_play_circle_filled_green_36dp);
                lastSelected = holder.selButton;

                DatabaseAccess databaseAccess = DatabaseAccess.getInstance(mContext);
                databaseAccess.open();

                String f = dataSet.get(position).getSubjectName();
                ArrayList<Question> arrQuestion = databaseAccess.getQuestion(f);
                Bundle b = new Bundle();
                b.putSerializable("questions", (Serializable) arrQuestion);
                Intent intent = new Intent("custom-message");
                intent.putExtras(b);
                LocalBroadcastManager.getInstance(this.mContext).sendBroadcast(intent);
                databaseAccess.close();
            }
        } catch (Exception ex) {
            Toast.makeText(mContext, mContext.getString(R.string.ex), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * gibt die Größe der ArrayList zurück, in der sich alle Subjects von der DB befinden
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    /**
     * Items in RecyclerView tatsächlich befüllen
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView subName;
        ImageButton selButton;
        ImageButton infoButton;

        public ViewHolder(View view) {
            super(view);
            subName = view.findViewById(R.id.SubjectTitle);
            selButton = (ImageButton) view.findViewById(R.id.SelectButton);
            infoButton = (ImageButton) view.findViewById(R.id.InfoButton);
        }
    }

    /**
     * OnClick event für jedes einzelne Recyclerview-Item
     * wird geprüft, ob Fach auswählen oder Informationen-Button selektiert wurde
     *
     * @param view
     */
    @Override
    public void onClick(View view) {
        try {
            int position = (Integer) view.getTag(); //positions des Items, welches angeklickt wurde

            switch (view.getId()) {
                case R.id.SelectButton:
                    if (lastSelected != null) {
                        lastSelected.setImageResource(R.drawable.ic_play_circle_outline_gray_36dp);
                    }
                    ImageButton bt = view.findViewById(view.getId());
                    bt.setImageResource(R.drawable.ic_play_circle_filled_green_36dp);
                    lastSelected = bt;

                    DatabaseAccess databaseAccess = DatabaseAccess.getInstance(view.getContext());
                    databaseAccess.open();

                    String ff = dataSet.get(position).getSubjectName();
                    ArrayList<Question> arrQuestion = databaseAccess.getQuestion(ff);
                    Bundle b = new Bundle();
                    b.putSerializable("questions", (Serializable) arrQuestion);
                    Intent intent = new Intent("custom-message");
                    intent.putExtras(b);
                    LocalBroadcastManager.getInstance(this.mContext).sendBroadcast(intent);

                    Snackbar.make(view, dataSet.get(position).getSubjectName() + " wurde ausgewählt.", Snackbar.LENGTH_SHORT)
                            .setAction("No action", null).show();

                    pref = this.mContext.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    //editor.putString("name", "Elena");
                    editor.putInt("PositionSelected", position);
                    editor.putString("CourseName", ff);
                    editor.apply();

                    //update trophieentry in DB
                    SharedPreferences prefs = mContext.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
                    String v = prefs.getString("Email", "");
                    databaseAccess.writeToTrophie(4, v);

                    databaseAccess.close();
                    break;

                case R.id.InfoButton:

                    String f = dataSet.get(position).getSubjectName();

                    DatabaseAccess databaseAccess1 = DatabaseAccess.getInstance(view.getContext());
                    databaseAccess1.open();

                    int counter = databaseAccess1.getQuestionCounter(f);

                    Snackbar.make(view, "Dieser Kurs enthält " + counter + " Fragen!", Snackbar.LENGTH_LONG)
                            .setAction("No action", null).show();

                    databaseAccess1.close();

                    break;
            }
        } catch (Exception ex) {
            Toast.makeText(mContext, mContext.getString(R.string.ex), Toast.LENGTH_SHORT).show();
        }
    }


}
