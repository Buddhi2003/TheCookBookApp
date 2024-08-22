package com.example.thecookbook.UserUI.Home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Toast;

import com.example.thecookbook.ListAdapters.ExploreAdapter;
import com.example.thecookbook.Classes.RecipeClass;
import com.example.thecookbook.DBClass.DBHelper;
import com.example.thecookbook.R;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.blurry.Blurry;


public class ExploreFragment extends Fragment {
    private RecyclerView recyclerView;
    private ExploreAdapter adapter;
    ShapeableImageView bgview;
    private int UserID;
    private List<RecipeClass> Recipelist;
    private DBHelper dbHelper;
    private String SubStatus;
    private ViewTreeObserver.OnGlobalLayoutListener layoutListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_explore, container, false);
        if(getArguments()!=null){
            UserID = getArguments().getInt("UserID");
        }
        bgview = view.findViewById(R.id.backgroundImageViewexplore);
        recyclerView = view.findViewById(R.id.explorerecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        dbHelper= new DBHelper(getContext());
        dbHelper.OpenDB();
        SubStatus = dbHelper.checkpremiumsubUser(UserID);
        if(SubStatus.equals("Pending")){
            SubStatus = "Basic";
        }
        layoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (bgview.getDrawable() != null) {
                    Blurry.with( getContext())
                            .radius(25)
                            .sampling(2)
                            .capture(bgview)
                            .into(bgview);
                } else {
                    Log.e("Blur Error", "Background image drawable is null");
                }
                bgview.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        };
        bgview.getViewTreeObserver().addOnGlobalLayoutListener(layoutListener);
//        bgview.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
//                if (bgview.getDrawable() != null) {
//                    Blurry.with( getContext())
//                            .radius(25)
//                            .sampling(2)
//                            .capture(bgview)
//                            .into(bgview);
//                } else {
//                    Log.e("Blur Error", "Background image drawable is null");
//                }
//            }
//        });
        SetupRecipes();
        return view;
    }

    private void SetupRecipes() {
        Recipelist = new ArrayList<>();
        Recipelist = dbHelper.getRecipes();
        Log.d("ExploreFragment", "SetupRecipes: "+Recipelist.size());
        if(!Recipelist.isEmpty()){
            adapter = new ExploreAdapter(getContext(), Recipelist,UserID,dbHelper,SubStatus);
            recyclerView.setAdapter(adapter);
        }else{
            Toast.makeText(getContext(), "No recipes found", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (bgview != null) {
            bgview.getViewTreeObserver().removeOnGlobalLayoutListener(layoutListener);
        }
    }
}