package com.example.thecookbook.UIAdapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thecookbook.Classes.RecipeClass;
import com.example.thecookbook.DBClass.DBHelper;
import com.example.thecookbook.InflUI.Recipes.RecipeEditActivity;
import com.example.thecookbook.R;
import com.example.thecookbook.UserUI.Home.RecipeDetailsActivity;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.List;

import jp.wasabeef.blurry.Blurry;

public class InfluencerViewRecipeAdapter extends RecyclerView.Adapter<InfluencerViewRecipeAdapter.ViewHolder> {
    private List<RecipeClass> recipelist;
    private Context context;
    int InfID;
    private DBHelper dbHelper;

    public InfluencerViewRecipeAdapter(List<RecipeClass> recipelist, Context context, int infID, DBHelper dbHelper) {
        this.recipelist = recipelist;
        this.context = context;
        InfID = infID;
        this.dbHelper = dbHelper;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.infrecipeitem,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RecipeClass recipe = recipelist.get(position);
        holder.name.setText(recipe.getRecipeName());
        holder.likecount.setText(String.valueOf(recipe.getLikeCount()));
        holder.recipeimg.setImageBitmap(recipe.getRecipeImage());
        holder.category.setText(recipe.getCategory());

        holder.recipeimg.post(()->{
            if (holder.recipeimg.getDrawable() != null) {
                Blurry.with(context)
                        .radius(4)
                        .sampling(4)
                        .async()
                        .capture(holder.recipeimg)
                        .into(holder.recipeimg);
            } else {
                Log.e("Blur Error", "Background image drawable is null");
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, RecipeDetailsActivity.class);
                intent.putExtra("RecipeID", recipe.getRecipeID());
                intent.putExtra("InfluencerEntry", true);
                context.startActivity(intent);
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                alertDialog.setTitle("Delete Recipe");
                alertDialog.setMessage("Are you sure you want to delete this recipe?");
                alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(dbHelper.deleteRecipe(recipe.getRecipeID(),InfID)){
                            setRecipelist();
                            Toast.makeText(context, "Deleted successfuly", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(context, "Failed to delete", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(context, "Cancelled", Toast.LENGTH_SHORT).show();
                    }
                });
                alertDialog.show();
            }
        });
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, RecipeEditActivity.class);
                intent.putExtra("RecipeID",recipe.getRecipeID());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return recipelist.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView name,likecount,category;
        ShapeableImageView recipeimg;
        Button edit,delete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.Recipenameiteminf);
            category = itemView.findViewById(R.id.categoryinfitem);
            likecount = itemView.findViewById(R.id.totallikesinf);
            edit = itemView.findViewById(R.id.edit_recipe);
            delete = itemView.findViewById(R.id.delete_recipe);
            recipeimg = itemView.findViewById(R.id.RecipeImgiteminf);
        }
    }
    public void setRecipelist(){
        this.recipelist = dbHelper.getRecipesforinfluencer(InfID);
        notifyDataSetChanged();
    }
}
