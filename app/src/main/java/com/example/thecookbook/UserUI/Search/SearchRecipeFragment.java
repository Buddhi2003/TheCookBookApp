package com.example.thecookbook.UserUI.Search;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thecookbook.ListAdapters.SearchRecipeAdapter;
import com.example.thecookbook.Classes.RecipeClass;
import com.example.thecookbook.DBClass.DBHelper;
import com.example.thecookbook.R;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.blurry.Blurry;

public class SearchRecipeFragment extends Fragment {
    private RecyclerView recipelistrecyclerView;
    private SearchRecipeAdapter adapter;
    private List<RecipeClass> Recipelist;
    private DBHelper dbHelper;
    private int UserID;
    private SearchView searchbar;
    private ChipGroup chipGroup;
    String nowfilter="";
    private String SubStatus;
    private TextView trendingtag;
    private ViewTreeObserver.OnGlobalLayoutListener layoutListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_search, container, false);
        if(getArguments()!=null){
            UserID = getArguments().getInt("UserID");
        }
        dbHelper= new DBHelper(getContext());
        dbHelper.OpenDB();
        SubStatus = dbHelper.checkpremiumsubUser(UserID);
        if(SubStatus.equals("Pending")){
            SubStatus = "Basic";
        }

        searchbar = view.findViewById(R.id.search_recipebar);
        chipGroup = view.findViewById(R.id.chipgroup_search_recipe);
        trendingtag = view.findViewById(R.id.trendingtagrecipe);

        recipelistrecyclerView = view.findViewById(R.id.search_recipe_list);
        recipelistrecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        SetupRecipes();
        chipGroup.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(@NonNull ChipGroup group, int checkedId) {
                if(checkedId == R.id.vegchip){
                    Toast.makeText(getContext(), "Veg clicked", Toast.LENGTH_SHORT).show();
                    nowfilter = "veg";
                    trendingtag.setText("Based on your search");
                }else if(checkedId == R.id.nonvegchip){
                    Toast.makeText(getContext(), "Non-Veg clicked", Toast.LENGTH_SHORT).show();
                    nowfilter = "non-veg";
                    trendingtag.setText("Based on your search");
                }else{
                    nowfilter = "";
                    trendingtag.setText("Trending Recipes");
                }
                FilterRecipe(searchbar.getQuery().toString());
            }
        });
        searchbar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.isEmpty()){
                    trendingtag.setText("Trending Recipes");
                }else{
                    trendingtag.setText("Based on your search");
                }
                FilterRecipe(newText);
                return true;
            }
        });
        return view;
    }

    private void FilterRecipe(String Name) {
        List<RecipeClass> filteredList = new ArrayList<>();
        for(RecipeClass recipeitem : Recipelist){
            if(nowfilter.isEmpty()|| recipeitem.getCategory().toLowerCase().equals(nowfilter)){
                Log.d("Filtertype check 1",""+nowfilter );
                Log.d("FilterRecipe", "Filtered: " + recipeitem.getRecipeName()+"type"+nowfilter);
                if(Name.isEmpty()||recipeitem.getRecipeName().toLowerCase().contains(Name.toLowerCase())){
                    filteredList.add(recipeitem);
                }
            }

        }
        if(filteredList.isEmpty()){
            Log.d("FilterRecipe", "No recipes found");
            Toast.makeText(getContext(), "No recipes found", Toast.LENGTH_SHORT).show();
        }else{
            adapter.setsearchedRecipeList(filteredList);
            adapter.notifyDataSetChanged();
        }
    }

    private void SetupRecipes() {
        Recipelist = dbHelper.getRecipes();
        if(Recipelist.size()>0){
            adapter = new SearchRecipeAdapter(getContext(), Recipelist,UserID,dbHelper,SubStatus);
            recipelistrecyclerView.setAdapter(adapter);
        }else{
            Toast.makeText(getContext(), "No recipes found", Toast.LENGTH_SHORT).show();
        }

    }


}