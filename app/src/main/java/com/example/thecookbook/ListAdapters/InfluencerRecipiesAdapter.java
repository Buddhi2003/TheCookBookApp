package com.example.thecookbook.ListAdapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thecookbook.Classes.RecipeClass;
import com.example.thecookbook.DBClass.DBHelper;
import com.example.thecookbook.R;
import com.example.thecookbook.UserUI.Home.RecipeDetailsActivity;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.List;

public class InfluencerRecipiesAdapter extends RecyclerView.Adapter<InfluencerRecipiesAdapter.ViewHolder> {
    private List<RecipeClass> recipes;
    private Context context;
    int UserID=0;
    private DBHelper dbHelper;
    private boolean AdminEntry = false;
    private boolean InfluencerEntry = false;

    public InfluencerRecipiesAdapter(List<RecipeClass> recipes, Context context, int userID,boolean adminEntry, DBHelper dbHelper, boolean influencerEntry) {
        this.recipes = recipes;
        this.context = context;
        UserID = userID;
        AdminEntry = adminEntry;
        this.dbHelper = dbHelper;
        InfluencerEntry = influencerEntry;
        Log.d("InfluencerRecipiesAdapter", "This is recipes adapter"+InfluencerEntry);

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.influencer_detail_recipe_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RecipeClass recipe = recipes.get(position);
        holder.RecipeName.setText(recipe.getRecipeName());
        holder.Description.setText(recipe.getDescription());
        holder.RecipeImage.setImageBitmap(recipe.getRecipeImage());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(AdminEntry){
                    Intent intent = new Intent(context, RecipeDetailsActivity.class);
                    intent.putExtra("RecipeID", recipe.getRecipeID());
                    intent.putExtra("AdminEntry", true);
                    context.startActivity(intent);
                } else if (InfluencerEntry) {
                    Intent intent = new Intent(context, RecipeDetailsActivity.class);
                    intent.putExtra("RecipeID", recipe.getRecipeID());
                    intent.putExtra("InfluencerEntry", true);
                    context.startActivity(intent);
                } else{
                    if(UserID!=0){
                        if(recipe.getSubType().equals("Basic")){
                            Intent intent = new Intent(context, RecipeDetailsActivity.class);
                            intent.putExtra("RecipeID", recipe.getRecipeID());
                            intent.putExtra("UserID", UserID);
                            context.startActivity(intent);
                        }else{
                            if(dbHelper.checkpremiumsubUser(UserID).equals("Premium")){
                                Intent intent = new Intent(context, RecipeDetailsActivity.class);
                                intent.putExtra("RecipeID", recipe.getRecipeID());
                                intent.putExtra("UserID", UserID);
                                context.startActivity(intent);
                            }
                            else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                builder.setTitle("Access Denied");
                                builder.setMessage("Only premium users can access this recipe. Please upgrade to premium to view this recipe.");
                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                                AlertDialog alertDialog = builder.create();
                                alertDialog.show();
                            }
                        }
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView RecipeName,Description;
        private ShapeableImageView RecipeImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            RecipeName = itemView.findViewById(R.id.RecipenameInfluencerItem);
            Description = itemView.findViewById(R.id.descriptionInfluencerItem);
            RecipeImage = itemView.findViewById(R.id.RecipeImginfluenceritem);
        }
    }
}
