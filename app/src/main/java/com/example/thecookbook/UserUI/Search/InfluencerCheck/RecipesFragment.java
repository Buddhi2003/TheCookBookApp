package com.example.thecookbook.UserUI.Search.InfluencerCheck;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.thecookbook.ListAdapters.InfluencerRecipiesAdapter;
import com.example.thecookbook.Classes.RecipeClass;
import com.example.thecookbook.DBClass.DBHelper;
import com.example.thecookbook.R;

import java.util.ArrayList;
import java.util.List;


public class RecipesFragment extends Fragment {
    private RecyclerView recyclerView;
    private InfluencerRecipiesAdapter adapter;
    private boolean AdminEntry = false;
    private boolean InfluencerEntry = false;
    private int UserID=0;
    private int InfID;
    private List<RecipeClass> Recipelist;
    private DBHelper dbHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipes, container, false);
        if(getArguments()!=null){
            if(getArguments().containsKey("UserID")){
                UserID = getArguments().getInt("UserID");
            }
            if(getArguments().containsKey("AdminEntry")){
                AdminEntry = true;
            }
            if(getArguments().containsKey("InfluencerEntry")){
                Log.d("RecipesFragment", "This is recipes fragment arguementcheck "+getArguments().getBoolean("InfluencerEntry"));
                InfluencerEntry = getArguments().getBoolean("InfluencerEntry");
            }
            InfID = getArguments().getInt("InfID");
        }

        recyclerView = view.findViewById(R.id.recipelist_influencerdetails);
        //GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2, GridLayoutManager.HORIZONTAL, false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setNestedScrollingEnabled(true);
        dbHelper= new DBHelper(getContext());
        dbHelper.OpenDB();
        SetupRecipes();
        return view;
    }
    private void SetupRecipes(){
        Recipelist = dbHelper.getRecipesfromInfluencer(InfID);
        Log.d("RecipesFragment", "This is recipes fragment"+InfluencerEntry);
        if(Recipelist.size()>0){
            adapter = new InfluencerRecipiesAdapter(Recipelist, getContext(),UserID, AdminEntry, dbHelper,InfluencerEntry);
            recyclerView.setAdapter(adapter);
        }else {
            Toast.makeText(getContext(), "No Recipies", Toast.LENGTH_SHORT).show();
        }

    }
}