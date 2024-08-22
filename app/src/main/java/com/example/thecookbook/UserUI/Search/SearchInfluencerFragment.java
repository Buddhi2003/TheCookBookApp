package com.example.thecookbook.UserUI.Search;

import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thecookbook.ListAdapters.SearchInfluencerAdapter;
import com.example.thecookbook.Classes.InfluencerClass;
import com.example.thecookbook.DBClass.DBHelper;
import com.example.thecookbook.R;

import java.util.ArrayList;
import java.util.List;

public class SearchInfluencerFragment extends Fragment {
    private RecyclerView influencerlistrecyclerView;
    private SearchInfluencerAdapter adapter;
    private List<InfluencerClass> InfluencerList;
    private DBHelper dbHelper;
    private int UserID;
    private SearchView searchbar;
    private TextView trendingtaginf;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_influencer_search, container, false);
        influencerlistrecyclerView = view.findViewById(R.id.search_influencer_list);
        trendingtaginf = view.findViewById(R.id.trendingtaginf);
        influencerlistrecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        searchbar = view.findViewById(R.id.search_influencerbar);
        if(getArguments()!=null){
            UserID = getArguments().getInt("UserID");
        }
        dbHelper= new DBHelper(getContext());
        dbHelper.OpenDB();
        SetupInfluencers();
        searchbar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.isEmpty()) {
                    trendingtaginf.setText("Trending Influencers");
                } else {
                    trendingtaginf.setText("Based on your search");
                }
                FilterInfluencer(newText);
                return true;
            }
        });
        return view;
    }

    private void FilterInfluencer(String newText) {
        List<InfluencerClass> filteredList = new ArrayList<>();
        for(InfluencerClass item : InfluencerList) {
            if (item.getInfName().toLowerCase().contains(newText.toLowerCase())) {
                filteredList.add(item);
            }
        }
        if (filteredList.isEmpty()) {
            Toast.makeText(getContext(), "No influencer found", Toast.LENGTH_SHORT).show();
        }
        else {
            adapter.setSearchedInfluencerList(filteredList);
        }
    }

    private void SetupInfluencers() {
        InfluencerList = dbHelper.getInfluencers();
        adapter = new SearchInfluencerAdapter(InfluencerList,getContext(),dbHelper, UserID);
        influencerlistrecyclerView.setAdapter(adapter);
    }
}