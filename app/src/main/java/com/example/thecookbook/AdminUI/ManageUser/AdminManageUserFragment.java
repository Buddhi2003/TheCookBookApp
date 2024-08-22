package com.example.thecookbook.AdminUI.ManageUser;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.thecookbook.Classes.InfluencerClass;
import com.example.thecookbook.Classes.UserClass;
import com.example.thecookbook.DBClass.DBHelper;
import com.example.thecookbook.ListAdapters.AdminInfManageAdapter;
import com.example.thecookbook.ListAdapters.AdminUserManageAdapter;
import com.example.thecookbook.R;

import java.util.List;


public class AdminManageUserFragment extends Fragment {
    RecyclerView recyclerView;
    List<UserClass> UserList;
    AdminUserManageAdapter adapter;
    private DBHelper dbHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_manage_user, container, false);
        recyclerView = view.findViewById(R.id.usermanageadminlist);
        dbHelper = new DBHelper(getContext());
        dbHelper.OpenDB();
        dbHelper = new DBHelper(getContext());
        dbHelper.OpenDB();
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 3, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        setupUsers();
        return view;
    }

    private void setupUsers() {
        UserList = dbHelper.getUsers();
        adapter = new AdminUserManageAdapter(UserList,getContext());
        recyclerView.setAdapter(adapter);
    }
}