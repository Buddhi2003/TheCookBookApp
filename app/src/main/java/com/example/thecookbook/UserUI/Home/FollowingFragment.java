package com.example.thecookbook.UserUI.Home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.thecookbook.ListAdapters.FollowingAdapter;
import com.example.thecookbook.Classes.RecipeClass;
import com.example.thecookbook.DBClass.DBHelper;
import com.example.thecookbook.R;

import java.util.ArrayList;
import java.util.List;


public class FollowingFragment extends Fragment {
    private RecyclerView recyclerView;
    private FollowingAdapter adapter;
    int UserID;
    private List<RecipeClass> Recipelist;
    private DBHelper dbHelper;
    String SubStatus;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_following, container, false);
        recyclerView = view.findViewById(R.id.subscribed_recyclelist);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        dbHelper= new DBHelper(getContext());
        dbHelper.OpenDB();
        if(getArguments()!=null){
            UserID = getArguments().getInt("UserID");
        }
        SubStatus = dbHelper.checkpremiumsubUser(UserID);
        if(SubStatus.equals("Pending")){
            SubStatus = "Basic";
        }
        SetupRecipes();
        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
        SetupRecipes();
    }

    private void SetupRecipes() {
        Recipelist = new ArrayList<>();
        Recipelist= dbHelper.getsubscribedRecipes(UserID);
        try {
            if (Recipelist.isEmpty()) {
                Toast.makeText(getContext(), "No Recipes found", Toast.LENGTH_SHORT).show();
            } else {
                if (adapter == null) {
                    adapter = new FollowingAdapter(Recipelist,getContext(),UserID,dbHelper,SubStatus);
                    recyclerView.setAdapter(adapter);
                }else {
                    adapter.notifyDataSetChanged();
                }
            }
        }catch (Exception e){
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }
}