package com.example.thecookbook.UserUI.Favourites;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.thecookbook.Classes.RecipeClass;
import com.example.thecookbook.DBClass.DBHelper;
import com.example.thecookbook.R;
import com.example.thecookbook.UIAdapters.FavouriteAdapter;

import java.util.List;


public class FavouriteFragment extends Fragment {
    RecyclerView recyclerView;
    DBHelper dbHelper;
    int UserID;
    List <RecipeClass> FavouriteList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favourite, container, false);
        dbHelper = new DBHelper(getContext());
        dbHelper.OpenDB();
        if(getArguments()!=null){
            if(getArguments().containsKey("UserID")){
                UserID = getArguments().getInt("UserID");
            }
        }

        recyclerView = view.findViewById(R.id.favouriterecycler);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        SetupRecipes();
        return view;
    }

    private void SetupRecipes() {
        FavouriteList = dbHelper.getFavRecipes(UserID);
        if (!FavouriteList.isEmpty()) {
            FavouriteAdapter adapter = new FavouriteAdapter(FavouriteList, getContext(), UserID);
            recyclerView.setAdapter(adapter);
        }
    }
}