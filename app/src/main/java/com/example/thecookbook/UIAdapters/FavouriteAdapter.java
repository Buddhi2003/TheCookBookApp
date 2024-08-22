package com.example.thecookbook.UIAdapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thecookbook.Classes.RecipeClass;
import com.example.thecookbook.R;
import com.example.thecookbook.UserUI.Home.RecipeDetailsActivity;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.List;

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.ViewHolder> {
    List<RecipeClass> FavouriteList;
    Context context;
    int UserID;

    public FavouriteAdapter(List<RecipeClass> favouriteList, Context context, int userID) {
        FavouriteList = favouriteList;
        this.context = context;
        UserID = userID;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favouriteitem,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RecipeClass recipe = FavouriteList.get(position);
        holder.RecipeName.setText(recipe.getRecipeName());
        holder.Description.setText(recipe.getDescription());
        holder.RecipeImage.setImageBitmap(recipe.getRecipeImage());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, RecipeDetailsActivity.class);
                intent.putExtra("RecipeID", recipe.getRecipeID());
                intent.putExtra("UserID", UserID);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return FavouriteList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView RecipeName,Description;
        ShapeableImageView RecipeImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            RecipeName = itemView.findViewById(R.id.RecipenameFav);
            Description = itemView.findViewById(R.id.descriptionFav);
            RecipeImage = itemView.findViewById(R.id.RecipeImgFav);
        }
    }
}
