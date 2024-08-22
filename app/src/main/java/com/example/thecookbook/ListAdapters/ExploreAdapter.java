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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thecookbook.Classes.RecipeClass;
import com.example.thecookbook.DBClass.DBHelper;
import com.example.thecookbook.R;
import com.example.thecookbook.UserUI.Home.RecipeDetailsActivity;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.List;

import jp.wasabeef.blurry.Blurry;

public class ExploreAdapter extends RecyclerView.Adapter<ExploreAdapter.ViewHolder>{
    private List<RecipeClass> recipelist;
    private Context context;
    int UserID;
    private DBHelper dbHelper;
    String SubStatus;

    public ExploreAdapter(Context context, List<RecipeClass> recipelist, int userID, DBHelper dbHelper,String subStatus) {
        this.context = context;
        this.recipelist = recipelist;
        UserID = userID;
        this.dbHelper = dbHelper;
        SubStatus = subStatus;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.exploreitem,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RecipeClass recipe = recipelist.get(position);
        holder.name.setText(recipe.getRecipeName());
        holder.desciption.setText(recipe.getDescription());
        holder.infName.setText("by "+dbHelper.getInfName(recipe.getInfID()));
        holder.likecount.setText(String.valueOf(recipe.getLikeCount()));
        holder.category.setText(recipe.getCategory());
        holder.date.setText(recipe.getDate());
        holder.recipeimg.setImageBitmap(recipe.getRecipeImage());
        holder.premiumlogo.setVisibility(recipe.getSubType().equals("Premium") ? View.VISIBLE : View.GONE);
//        holder.recipeimg.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
//                if (holder.recipeimg.getDrawable() != null) {
//                    Blurry.with(context)
//                            .radius(1)
//                            .sampling(2)
//                            .capture(holder.recipeimg)
//                            .into(holder.recipeimg);
//                } else {
//                    Log.e("Blur Error", "Background image drawable is null");
//                }
//            }
//        });


        holder.recipeimg.post(()->{
            if (holder.recipeimg.getDrawable() != null) {
                Blurry.with(context)
                        .radius(10)
                        .sampling(2)
                        .capture(holder.recipeimg)
                        .into(holder.recipeimg);
            } else {
                Log.e("Blur Error", "Background image drawable is null");
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(recipe.getSubType().equals("Premium")){
                    if(SubStatus.equals("Premium")){
                        Intent intent = new Intent(context, RecipeDetailsActivity.class);
                        intent.putExtra("UserID",UserID);
                        intent.putExtra("RecipeID", recipe.getRecipeID());
                        context.startActivity(intent);
                    }else{
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
                }else{
                    Intent intent = new Intent(context, RecipeDetailsActivity.class);
                    intent.putExtra("UserID",UserID);
                    intent.putExtra("RecipeID", recipe.getRecipeID());
                    context.startActivity(intent);
                }


            }
        });
    }

    @Override
    public int getItemCount() {
        return recipelist.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView name,desciption, infName,likecount,category,date;
        private ShapeableImageView recipeimg,premiumlogo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name=(TextView)itemView.findViewById(R.id.Recipenamecustomitem);
            desciption=(TextView)itemView.findViewById(R.id.descriptioncustomitem);
            infName=(TextView)itemView.findViewById(R.id.Infnameexploreitem);
            recipeimg=(ShapeableImageView)itemView.findViewById(R.id.RecipeImgcustomitem);
            likecount=(TextView)itemView.findViewById(R.id.likecountexploreitem);
            category=(TextView)itemView.findViewById(R.id.categoryexploreitem);
            date=(TextView)itemView.findViewById(R.id.Recipedateexploreitem);
            premiumlogo=(ShapeableImageView)itemView.findViewById(R.id.premiumimg);


        }
    }
}
