package com.example.thecookbook.AdminUI.Home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.thecookbook.Classes.RecipeClass;
import com.example.thecookbook.DBClass.DBHelper;

import com.example.thecookbook.ListAdapters.PendingRecipeAdapter;
import com.example.thecookbook.R;

import java.util.ArrayList;
import java.util.List;


public class PendingRecipeFragment extends Fragment {
    private RecyclerView recyclerView;
    private PendingRecipeAdapter adapter;
    private List<RecipeClass> Recipelist;
    private DBHelper dbHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_pending_recipe, container, false);
        dbHelper = new DBHelper(getContext());
        dbHelper.OpenDB();
        recyclerView = view.findViewById(R.id.pendingreciperecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        dbHelper= new DBHelper(getContext());
        dbHelper.OpenDB();
        SetupRecipes();
        return view;
    }

    private void SetupRecipes() {
        Recipelist = new ArrayList<>();
        Recipelist = dbHelper.getPendingRecipes();
        if (Recipelist.size() > 0) {
            adapter = new PendingRecipeAdapter(Recipelist, getContext(), dbHelper);
            recyclerView.setAdapter(adapter);
        }else{
            Toast.makeText(getContext(), "No recipes found", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        SetupRecipes();
    }
}