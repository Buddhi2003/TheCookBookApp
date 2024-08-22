package com.example.thecookbook.AdminUI.ManageInf;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.thecookbook.Classes.InfluencerClass;
import com.example.thecookbook.DBClass.DBHelper;
import com.example.thecookbook.ListAdapters.AdminInfManageAdapter;
import com.example.thecookbook.R;

import java.util.List;


public class AdminManageInfFragment extends Fragment {
    RecyclerView recyclerView;
    List<InfluencerClass> influencerlist;
    AdminInfManageAdapter adapter;
    private DBHelper dbHelper;
    private boolean AdminEntry=true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_manage_inf, container, false);
        recyclerView = view.findViewById(R.id.infmanageadminlist);
        dbHelper = new DBHelper(getContext());
        dbHelper.OpenDB();
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        setupInfluencers();
        return view;
    }

    private void setupInfluencers() {
        influencerlist = dbHelper.getInfluencers();
        adapter = new AdminInfManageAdapter(influencerlist,getContext(),AdminEntry);
        recyclerView.setAdapter(adapter);
    }
}