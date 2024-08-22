package com.example.thecookbook.UserUI.Search.InfluencerCheck;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.thecookbook.Classes.UpdateClass;
import com.example.thecookbook.DBClass.DBHelper;
import com.example.thecookbook.ListAdapters.InfluencerUpdatesAdapter;
import com.example.thecookbook.R;

import java.util.List;

public class UpdatesFragment extends Fragment {
    InfluencerUpdatesAdapter adapter;
    private int InfID;
    private DBHelper dbHelper;
    private RecyclerView recyclerView;
    List<UpdateClass> updateslist;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_updates, container, false);
        dbHelper= new DBHelper(getContext());
        dbHelper.OpenDB();
        if(getArguments()!=null){
            InfID = getArguments().getInt("InfID");
        }
        recyclerView = view.findViewById(R.id.updatelist_influencerdetails);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        setupupdates();
        return view;
    }
    private void setupupdates(){
        updateslist = dbHelper.getUpdates(InfID);
        if(updateslist.size()>0){
            InfluencerUpdatesAdapter adapter = new InfluencerUpdatesAdapter(updateslist,InfID,dbHelper,getContext());
            recyclerView.setAdapter(adapter);
        }else {
            Toast.makeText(getContext(), "No Updates", Toast.LENGTH_SHORT).show();
        }
    }
}