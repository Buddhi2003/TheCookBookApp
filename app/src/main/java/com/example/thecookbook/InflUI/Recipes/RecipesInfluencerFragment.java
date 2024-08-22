package com.example.thecookbook.InflUI.Recipes;

import android.content.Intent;
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
import com.example.thecookbook.R;
import com.example.thecookbook.UIAdapters.InfluencerViewRecipeAdapter;
import com.example.thecookbook.testRecipeAdd;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


public class RecipesInfluencerFragment extends Fragment {
    private InfluencerViewRecipeAdapter adapter;
    private int InfID;
    private List<RecipeClass> Recipelist;
    private DBHelper dbHelper;
    private RecyclerView recyclerView;
    private FloatingActionButton fab_expand,fab_addrecipe,fab_addupdate;
    boolean isallfabsvisible=false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipes_influencer, container, false);
        dbHelper= new DBHelper(getContext());
        dbHelper.OpenDB();
        if(getArguments()!=null){
            InfID = getArguments().getInt("InfID");
        }
        fab_expand = view.findViewById(R.id.fab_expand);
        fab_addrecipe = view.findViewById(R.id.fab_addrecipe);
        fab_addupdate = view.findViewById(R.id.fab_addupdate);
        fab_addrecipe.setVisibility(View.GONE);
        fab_addupdate.setVisibility(View.GONE);
        recyclerView = view.findViewById(R.id.recipelistinfluencer);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        SetupRecipes();
        fab_expand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isallfabsvisible){
                    fab_addrecipe.setVisibility(View.VISIBLE);
                    fab_addupdate.setVisibility(View.VISIBLE);
                    isallfabsvisible=true;
                }else{
                    fab_addrecipe.setVisibility(View.GONE);
                    fab_addupdate.setVisibility(View.GONE);
                    isallfabsvisible=false;
                }
            }
        });
        fab_addrecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), testRecipeAdd.class);
                intent.putExtra("InfID",InfID);
                startActivity(intent);
            }
        });
        fab_addupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AddUpdateActivity.class);
                intent.putExtra("InfID",InfID);
                startActivity(intent);
            }
        });
        return view;
    }

    private void SetupRecipes(){
        Recipelist = dbHelper.getRecipesforinfluencer(InfID);
        if(Recipelist.size()>0){
            adapter = new InfluencerViewRecipeAdapter(Recipelist,getContext(),InfID,dbHelper);
            recyclerView.setAdapter(adapter);
        }else {
            Toast.makeText(getContext(), "No recipes found", Toast.LENGTH_SHORT).show();
        }
    }
}