package com.example.thecookbook.ListAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thecookbook.Classes.UpdateClass;
import com.example.thecookbook.DBClass.DBHelper;
import com.example.thecookbook.R;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.List;

public class InfluencerUpdatesAdapter extends RecyclerView.Adapter<InfluencerUpdatesAdapter.ViewHolder> {

    List<UpdateClass> updates;
    int InfID;
    private DBHelper dbHelper;
    private Context context;

    public InfluencerUpdatesAdapter(List<UpdateClass> updates, int infID, DBHelper dbHelper, Context context) {
        this.updates = updates;
        InfID = infID;
        this.dbHelper = dbHelper;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.updateslistitem,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UpdateClass update = updates.get(position);
        holder.updatedetail.setText(update.getUpdateStatus());
        holder.date.setText(update.getDate());
        holder.updateimage.setImageBitmap(update.getUpdateImage());
    }

    @Override
    public int getItemCount() {
        return updates.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView updatedetail,date;
        private ShapeableImageView updateimage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            updatedetail = itemView.findViewById(R.id.updatedetailitem);
            date = itemView.findViewById(R.id.dateupdateitem);
            updateimage = itemView.findViewById(R.id.updateimageitem);
        }
    }
}
