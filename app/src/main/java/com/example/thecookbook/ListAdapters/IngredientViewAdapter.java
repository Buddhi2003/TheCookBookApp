package com.example.thecookbook.ListAdapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thecookbook.Classes.IngredientClass;
import com.example.thecookbook.R;

import java.util.List;

public class IngredientViewAdapter extends RecyclerView.Adapter<IngredientViewAdapter.ViewHolder> {
    private List<IngredientClass> ingredientList;

    public IngredientViewAdapter(List<IngredientClass> ingredientList) {
        this.ingredientList = ingredientList;
    }


    @NonNull
    @Override
    public IngredientViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ingredient_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientViewAdapter.ViewHolder holder, int position) {
        IngredientClass ingredient = ingredientList.get(position);
        holder.IngredientNo.setText(String.valueOf(ingredient.getIngredientNo()));
        holder.IngredientName.setText(ingredient.getIngredientName());
        holder.IngredientQuantity.setText(ingredient.getIngredientQuantity());
    }

    @Override
    public int getItemCount() {
        return ingredientList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView IngredientNo;
        private TextView IngredientName;
        private TextView IngredientQuantity;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            IngredientNo=itemView.findViewById(R.id.ingredient_no);
            IngredientName=itemView.findViewById(R.id.ingredient_name);
            IngredientQuantity=itemView.findViewById(R.id.ingredient_quantity);

        }
    }




}
