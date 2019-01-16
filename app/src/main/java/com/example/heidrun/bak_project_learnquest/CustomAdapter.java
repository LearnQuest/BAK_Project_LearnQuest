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
