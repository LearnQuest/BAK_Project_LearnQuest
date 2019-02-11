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

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> implements View.OnClickListener {

    private ArrayList<Subjects> dataSet;
    Context mContext;
    ImageButton lastSelected;
    boolean test = false;
    int positiongedrückt = -1;
    SharedPreferences pref;


    private final static String MY_PREFS_NAME = "LearnQuest_Pref_Subject";

    public CustomAdapter(ArrayList<Subjects> data, Context context) {
       // super(context, R.layout.listview_row, data);
        this.dataSet = data;
        this.mContext=context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflate the layout file
        View groceryProductView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_row, parent, false);
        ViewHolder gvh = new ViewHolder(groceryProductView);



        return gvh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
       // holder.info.setImageResource(dataSet.get(position).getSubjectName());
        holder.subName.setText(dataSet.get(position).getSubjectName());
        holder.selButton.setOnClickListener(this);
        holder.selButton.setTag(position);
        holder.infoButton.setOnClickListener(this);
        holder.infoButton.setTag(position);

        SharedPreferences prefs = mContext.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        int pageNumber = prefs.getInt("PositionSelected", -1);
        if(pageNumber != -1 && pageNumber == position){
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
            //            intent.putExtra("quantity",Integer.parseInt(quantity.getText().toString()));
            intent.putExtras(b);
            LocalBroadcastManager.getInstance(this.mContext).sendBroadcast(intent);
        }

    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    // View lookup cache
    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView subName;
        ImageButton selButton;
        ImageButton infoButton;

        public ViewHolder(View view) {
            super(view);
            subName=view.findViewById(R.id.SubjectTitle);
            selButton=(ImageButton)view.findViewById(R.id.SelectButton);
            infoButton =(ImageButton)view.findViewById(R.id.InfoButton);
        }
    }

    @Override
    public void onClick(View view) {
        int position=(Integer) view.getTag();
       // Object object= getItem(position);
       // Subjects dataModel=(Subjects) object;

        switch (view.getId())
        {
            case R.id.SelectButton:
                if(lastSelected!= null){
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
                //            intent.putExtra("quantity",Integer.parseInt(quantity.getText().toString()));
                intent.putExtras(b);
                LocalBroadcastManager.getInstance(this.mContext).sendBroadcast(intent);

                Snackbar.make(view, dataSet.get(position).getSubjectName() +" wurde ausgewählt.", Snackbar.LENGTH_SHORT)
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
                databaseAccess.writeToTrophie(4,v );
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
                //TODO:
                //Dialogfenster für informationen erstellen & öffnen

                break;
        }
    }

    private int lastPosition = -1;

    /*@Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Subjects dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.listview_row, parent, false);
            viewHolder.subName = (TextView) convertView.findViewById(R.id.SubjectTitle);
            viewHolder.info = (ImageButton) convertView.findViewById(R.id.SelectButton);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

      //  Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        //result.startAnimation(animation);
        lastPosition = position;

        viewHolder.subName.setText(dataModel.getSubjectName());
        viewHolder.info.setOnClickListener(this);
        viewHolder.info.setTag(position);
        // Return the completed view to render on screen
        return convertView;
    }*/


}
