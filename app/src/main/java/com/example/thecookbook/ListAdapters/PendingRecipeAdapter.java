package com.example.thecookbook.ListAdapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.example.thecookbook.R;
import com.example.thecookbook.UserUI.Home.RecipeDetailsActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.List;

public class PendingRecipeAdapter extends RecyclerView.Adapter<PendingRecipeAdapter.ViewHolder> {
    List<RecipeClass> pendingrecipelist;
    Context context;
    DBHelper dbHelper;

    public PendingRecipeAdapter(List<RecipeClass> pendingrecipelist, Context context, DBHelper dbHelper) {
        this.pendingrecipelist = pendingrecipelist;
        this.context = context;
        this.dbHelper = dbHelper;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pendingrecipeitem,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RecipeClass recipe = pendingrecipelist.get(position);
        holder.RecipeName.setText(recipe.getRecipeName());
        holder.InfluencerName.setText(dbHelper.getInfName(recipe.getInfID()));
        holder.RecipeImage.setImageBitmap(recipe.getRecipeImage());
        holder.ConfirmRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dbHelper.confirmRecipe(recipe.getRecipeID())){
                    Toast.makeText(context, "Recipe Confirmed", Toast.LENGTH_SHORT).show();
                    setPendingRecipes();
                }else{
                    Toast.makeText(context, "Recipe Confirming failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
        holder.RecipeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, RecipeDetailsActivity.class);
                intent.putExtra("RecipeID", recipe.getRecipeID());
                intent.putExtra("AdminEntry", true);
                context.startActivity(intent);
            }
        });
        holder.DeclineRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                alertDialog.setTitle("Decline Request");
                alertDialog.setMessage("Are you sure you want to decline this recipe?");
                alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(dbHelper.declineRecipe(recipe.getRecipeID())){
                            setPendingRecipes();
                            Toast.makeText(context, "Decline successfuly", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(context, "Failed to decline", Toast.LENGTH_SHORT).show();
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
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, RecipeDetailsActivity.class);
                intent.putExtra("RecipeID", recipe.getRecipeID());
                intent.putExtra("AdminEntry", true);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return pendingrecipelist.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView RecipeName,InfluencerName;
        MaterialButton ConfirmRecipe,DeclineRecipe;
        ShapeableImageView RecipeImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            RecipeName=(TextView)itemView.findViewById(R.id.Recipenamependingitem);
            InfluencerName=(TextView)itemView.findViewById(R.id.Infnamependingitem);
            RecipeImage=(ShapeableImageView)itemView.findViewById(R.id.RecipeImgpendingitem);
            ConfirmRecipe=(MaterialButton)itemView.findViewById(R.id.confirmrecipeitem);
            DeclineRecipe=(MaterialButton)itemView.findViewById(R.id.declinerecipeitem);
        }
    }
    public void setPendingRecipes(){
        this.pendingrecipelist = dbHelper.getPendingRecipes();
        notifyDataSetChanged();
    }

}
