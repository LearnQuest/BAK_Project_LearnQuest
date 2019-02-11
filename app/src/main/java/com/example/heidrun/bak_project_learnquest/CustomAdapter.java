package com.example.heidrun.bak_project_learnquest;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> implements View.OnClickListener {

    private ArrayList<Subjects> dataSet;
    Context mContext;
    ImageButton lastSelected;

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
                Snackbar.make(view, dataSet.get(position).getSubjectName() +" wurde ausgewählt.", Snackbar.LENGTH_SHORT)
                        .setAction("No action", null).show();
                break;

            case R.id.InfoButton:
                Snackbar.make(view, "Dieser Kurs enthält X Fragen", Snackbar.LENGTH_LONG)
                        .setAction("No action", null).show();
                //TODO:
                //Dialogfenster für informationen erstellen & öffnen

                break;
        }
    }

    private int lastPosition = -1;

}
