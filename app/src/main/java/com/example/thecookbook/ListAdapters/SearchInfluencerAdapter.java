package com.example.thecookbook.ListAdapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thecookbook.Classes.InfluencerClass;

import com.example.thecookbook.DBClass.DBHelper;
import com.example.thecookbook.R;
import com.example.thecookbook.UserUI.Search.InfluencerCheck.InfluencerDetailsActivity;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.List;

public class SearchInfluencerAdapter extends RecyclerView.Adapter<SearchInfluencerAdapter.ViewHolder> {
    private List<InfluencerClass> influencerList;
    private Context context;
    int UserID;
    private DBHelper dbHelper;

    public SearchInfluencerAdapter(List<InfluencerClass> influencerList, Context context, DBHelper dbHelper, int userID) {
        this.influencerList = influencerList;
        this.context = context;
        this.dbHelper = dbHelper;
        UserID = userID;
    }
    public void setSearchedInfluencerList(List<InfluencerClass> influencerList){
        this.influencerList = influencerList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.influencersearchitem, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        InfluencerClass influencer = influencerList.get(position);
        holder.Name.setText(influencer.getInfName());
        holder.Description.setText(influencer.getBio());
        holder.ProfileImage.setImageBitmap(influencer.getInfprofImage());
        Animation animation = AnimationUtils.loadAnimation(holder.itemView.getContext(), android.R.anim.slide_in_left);
        holder.itemView.startAnimation(animation);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, InfluencerDetailsActivity.class);
                intent.putExtra("UserID",UserID);
                intent.putExtra("InfID",influencer.getInfID());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return influencerList.size();
    }



    public static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView Name,Description;
        private ShapeableImageView ProfileImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Name = itemView.findViewById(R.id.influencersearchname);
            Description = itemView.findViewById(R.id.influencersearchdescription);
            ProfileImage = itemView.findViewById(R.id.influencersearchpfp);
        }
    }
}
