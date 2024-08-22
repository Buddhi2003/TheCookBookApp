package com.example.thecookbook.UIAdapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thecookbook.Classes.RecipeClass;
import com.example.thecookbook.R;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.List;

public class RecipeHistoryAdapter extends RecyclerView.Adapter<RecipeHistoryAdapter.ViewHolder> {
    List<RecipeClass> recipes;

    public RecipeHistoryAdapter(List<RecipeClass> recipes) {
        this.recipes = recipes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_history_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RecipeClass recipe = recipes.get(position);
        holder.RecipeName.setText(recipe.getRecipeName());
        holder.Date.setText(recipe.getDate());
        holder.Status.setText(recipe.getStatus());
        holder.RecipeImage.setImageBitmap(recipe.getRecipeImage());
        holder.category.setText(recipe.getCategory());
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView RecipeName,Date,Status,category;
        ShapeableImageView RecipeImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            RecipeName = itemView.findViewById(R.id.historyname);
            category = itemView.findViewById(R.id.historycategory);
            Date = itemView.findViewById(R.id.datehistory);
            Status = itemView.findViewById(R.id.historystatus);
            RecipeImage = itemView.findViewById(R.id.RecipeImghistoryitem);
        }
    }
}
