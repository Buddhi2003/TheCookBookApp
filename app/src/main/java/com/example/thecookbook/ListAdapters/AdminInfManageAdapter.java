package com.example.thecookbook.ListAdapters;

import static java.security.AccessController.getContext;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thecookbook.Classes.InfluencerClass;
import com.example.thecookbook.InflUI.Profile.ProfileInfFragment;
import com.example.thecookbook.R;
import com.example.thecookbook.UserUI.Search.InfluencerCheck.InfluencerDetailsActivity;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.List;

public class AdminInfManageAdapter extends RecyclerView.Adapter<AdminInfManageAdapter.ViewHolder> {
    private List<InfluencerClass> influencerlist;
    Context context;
    Boolean adminEntry=false;
    int UserID;

    public AdminInfManageAdapter(List<InfluencerClass> influencerlist, Context context, Boolean adminEntry) {
        this.influencerlist = influencerlist;
        this.context = context;
        this.adminEntry = adminEntry;
    }

    public AdminInfManageAdapter(List<InfluencerClass> influencerlist,Context context,int userID) {
        this.influencerlist = influencerlist;
        this.context = context;
        this.UserID = userID;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_manage_inf_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        InfluencerClass influencer = influencerlist.get(position);
        holder.name.setText(influencer.getInfName());
        holder.description.setText(influencer.getBio());
        holder.img.setImageBitmap(influencer.getInfprofImage());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(adminEntry){
                    Intent intent = new Intent(context, InfluencerDetailsActivity.class);
                    intent.putExtra("InfID", influencer.getInfID());
                    intent.putExtra("AdminEntry", true);
                    context.startActivity(intent);
                }else{
                    Intent intent = new Intent(context, InfluencerDetailsActivity.class);
                    intent.putExtra("InfID", influencer.getInfID());
                    intent.putExtra("UserID",UserID);
                    context.startActivity(intent);
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return influencerlist.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name,description;
        ShapeableImageView img;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name=(TextView)itemView.findViewById(R.id.InfluencerNameAdminItem);
            description=(TextView)itemView.findViewById(R.id.descriptionInfluencerAdminItem);
            img=(ShapeableImageView)itemView.findViewById(R.id.InfImgAdminitem);

        }
    }
}