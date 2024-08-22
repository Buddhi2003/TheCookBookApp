package com.example.thecookbook.AdminUI.Home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.thecookbook.Classes.UserClass;
import com.example.thecookbook.DBClass.DBHelper;
import com.example.thecookbook.ListAdapters.PendingRequestAdapter;
import com.example.thecookbook.R;

import java.util.List;


public class PendingRequestFragment extends Fragment {
    RecyclerView recyclerView;
    PendingRequestAdapter adapter;
    List<UserClass> Userlist;
    DBHelper dbHelper;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pending_request, container, false);
        recyclerView = view.findViewById(R.id.pendingrequestrecycler);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        dbHelper= new DBHelper(getContext());
        dbHelper.OpenDB();
        Setuprequests();
        return view;
    }

    private void Setuprequests() {
        Userlist = dbHelper.getPendingUsers();
        adapter = new PendingRequestAdapter(Userlist, getContext(), dbHelper);
        recyclerView.setAdapter(adapter);
    }
}