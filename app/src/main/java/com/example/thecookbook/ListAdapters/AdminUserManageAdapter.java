package com.example.thecookbook.ListAdapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thecookbook.Classes.UserClass;
import com.example.thecookbook.R;
import com.example.thecookbook.UserUI.Profile.UserProfileDetailActivity;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.List;

public class AdminUserManageAdapter extends RecyclerView.Adapter<AdminUserManageAdapter.ViewHolder> {
    List<UserClass> userlist;
    Context context;

    public AdminUserManageAdapter(List<UserClass> userlist, Context context) {
        this.userlist = userlist;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_manage_user_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UserClass user = userlist.get(position);
        holder.Name.setText(user.getUserName());
        holder.img.setImageBitmap(user.getProfileImage());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UserProfileDetailActivity.class);
                intent.putExtra("UserID", user.getUserID());
                intent.putExtra("AdminEntry", true);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return userlist.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView Name;
        ShapeableImageView img;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Name=(TextView)itemView.findViewById(R.id.UserNameAdminItem);
            img=(ShapeableImageView)itemView.findViewById(R.id.UserImgAdminitem);
        }
    }
}
