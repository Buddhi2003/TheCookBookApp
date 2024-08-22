package com.example.thecookbook.InflUI;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import com.example.thecookbook.Classes.RecipeClass;
import com.example.thecookbook.DBClass.DBHelper;
import com.example.thecookbook.R;
import com.example.thecookbook.UIAdapters.RecipeHistoryAdapter;

import java.util.List;

public class RecipeHistoryFragment extends DialogFragment {
    private RecipeHistoryAdapter adapter;
    private List<RecipeClass> recipes;
    private int InfID;
    private DBHelper dbHelper;
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_history, container, false);
        if(getArguments()!=null){
            InfID = getArguments().getInt("InfID");
        }
        dbHelper = new DBHelper(getContext());
        dbHelper.OpenDB();
        recyclerView = view.findViewById(R.id.RecipeHistoryRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        setuprecycler();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Window window = getDialog().getWindow();
        if (window != null) {
            window.setBackgroundDrawableResource(android.R.color.transparent);
        }
    }

    private void setuprecycler() {
        Log.d("InfID", InfID + "");
        recipes = dbHelper.getRecipesforInfHistory(InfID);
        if (!recipes.isEmpty()) {
            adapter = new RecipeHistoryAdapter(recipes);
            recyclerView.setAdapter(adapter);
        }else{
            Toast.makeText(getContext(), "No Recipes Found", Toast.LENGTH_SHORT).show();
        }

    }

}