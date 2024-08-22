package com.example.thecookbook.ListAdapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thecookbook.Classes.RecipeClass;
import com.example.thecookbook.DBClass.DBHelper;
import com.example.thecookbook.R;
import com.example.thecookbook.UserUI.Home.RecipeDetailsActivity;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.List;

public class SearchRecipeAdapter extends RecyclerView.Adapter<SearchRecipeAdapter.ViewHolder> {

    private List<RecipeClass> recipeList;
    private Context context;
    int UserID;
    private DBHelper dbHelper;
    String SubStatus;

    public SearchRecipeAdapter(Context context, List<RecipeClass> recipeList, int userID, DBHelper dbHelper, String subStatus) {
        this.context = context;
        this.recipeList = recipeList;
        UserID = userID;
        this.dbHelper = dbHelper;
        SubStatus = subStatus;
    }
    public void setsearchedRecipeList(List<RecipeClass> recipeList) {
        this.recipeList = recipeList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipesearchitem, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RecipeClass recipe = recipeList.get(position);
        holder.recipeName.setText(recipe.getRecipeName());
        holder.recipeDescription.setText(recipe.getDescription());
        holder.infName.setText(dbHelper.getInfName(recipe.getInfID()));
        holder.recipeImage.setImageBitmap(recipe.getRecipeImage());
        holder.likecount.setText(String.valueOf(recipe.getLikeCount()));
        holder.category.setText(recipe.getCategory());
        holder.premiumlogo.setVisibility(recipe.getSubType().equals("Premium") ? View.VISIBLE : View.GONE);
        Animation animation = AnimationUtils.loadAnimation(holder.itemView.getContext(), android.R.anim.slide_in_left);
        holder.itemView.startAnimation(animation);

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
        return recipeList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView recipeName, recipeDescription, infName,category;
        private ShapeableImageView recipeImage,premiumlogo;
        private TextView likecount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            recipeName=(TextView) itemView.findViewById(R.id.Rnameitemsearch);
            recipeDescription=(TextView) itemView.findViewById(R.id.descriptionitemsearch);
            infName=(TextView) itemView.findViewById(R.id.Infnameitemsearch);
            recipeImage=(ShapeableImageView) itemView.findViewById(R.id.RecipeImgitemsearch);
            likecount=(TextView) itemView.findViewById(R.id.likecountsearchitem);
            category=(TextView) itemView.findViewById(R.id.Categorysearch);
            premiumlogo=(ShapeableImageView) itemView.findViewById(R.id.premiumimgsearch);
        }
    }
}
