package com.example.thecookbook.ListAdapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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

public class FollowingAdapter extends RecyclerView.Adapter<FollowingAdapter.ViewHolder> {
    private List<RecipeClass> recipelist;
    private Context context;
    int UserID;
    DBHelper dbHelper;
    String SubStatus;

    public FollowingAdapter(List<RecipeClass> recipelist, Context context, int userID, DBHelper dbHelper, String subStatus) {
        this.recipelist = recipelist;
        this.context = context;
        UserID = userID;
        this.dbHelper = dbHelper;
        SubStatus = subStatus;

    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.exploreitem,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RecipeClass recipeitem = recipelist.get(position);
        holder.name.setText(recipeitem.getRecipeName());
        holder.desciption.setText(recipeitem.getDescription());
        holder.infname.setText(dbHelper.getInfName(recipeitem.getInfID()));
        holder.img.setImageBitmap(recipeitem.getRecipeImage());
        holder.category.setText(recipeitem.getCategory());
        holder.likecount.setText(String.valueOf(recipeitem.getLikeCount()));
        Animation animation = AnimationUtils.loadAnimation(holder.itemView.getContext(), android.R.anim.slide_in_left);
        holder.itemView.startAnimation(animation);

        holder.img.post(()->{
            if (holder.img.getDrawable() != null) {
                Blurry.with(context)
                        .radius(4)
                        .sampling(4)
                        .capture(holder.img)
                        .into(holder.img);
            } else {
                Log.e("Blur Error", "Background image drawable is null");
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(recipeitem.getSubType().equals("Premium")){
                    if(SubStatus.equals("Premium")){
                        Intent intent = new Intent(context, RecipeDetailsActivity.class);
                        intent.putExtra("UserID",UserID);
                        intent.putExtra("RecipeID", recipeitem.getRecipeID());
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
                    intent.putExtra("RecipeID", recipeitem.getRecipeID());
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
        private TextView name;
        private TextView desciption;
        private TextView infname;
        private TextView category;
        private TextView likecount;
        private ShapeableImageView img;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.Recipenamecustomitem);
            desciption = itemView.findViewById(R.id.descriptioncustomitem);
            infname = itemView.findViewById(R.id.Infnameexploreitem);
            img = itemView.findViewById(R.id.RecipeImgcustomitem);
            category = itemView.findViewById(R.id.categoryexploreitem);
            likecount = itemView.findViewById(R.id.likecountexploreitem);
        }
    }
}
