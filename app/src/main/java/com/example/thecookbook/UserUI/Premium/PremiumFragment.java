package com.example.thecookbook.UserUI.Premium;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.thecookbook.DBClass.DBHelper;
import com.example.thecookbook.R;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.imageview.ShapeableImageView;


public class PremiumFragment extends Fragment {
    private String sub_status;
    Button purchasepremium;
    MaterialCardView pendingtext;
    private DBHelper dbhelper;
    int UserID;
    private VideoView videobg;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_premium, container, false);
        if (getArguments()!=null) {
            UserID = getArguments().getInt("UserID");
        }
        dbhelper = new DBHelper(getContext());
        dbhelper.OpenDB();
        videobg = view.findViewById(R.id.videobg);
        purchasepremium = view.findViewById(R.id.btnpurchase);
        pendingtext = view.findViewById(R.id.pendingtextcard);
        sub_status=dbhelper.checkpremiumsubUser(UserID);
        if(sub_status.equals("Premium")){
            purchasepremium.setText("Already Purchased");
            pendingtext.setVisibility(View.GONE);
        }else if(sub_status.equals("Pending")){
            pendingtext.setVisibility(View.VISIBLE);
            purchasepremium.setVisibility(View.GONE);
        }else{
            pendingtext.setVisibility(View.GONE);
        }
        loadAndPlayVideo();
        purchasepremium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sub_status.equals("Premium")){
                    Toast.makeText(getContext(), "Already Premium", Toast.LENGTH_SHORT).show();
                }else{
                    if(dbhelper.setPendingstatus(UserID)){
                        Toast.makeText(getContext(), "Your Subscription request is now pending", Toast.LENGTH_SHORT).show();
                        pendingtext.setVisibility(View.VISIBLE);
                        purchasepremium.setVisibility(View.GONE);
                    }else {
                        Toast.makeText(getContext(), "Request adding failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        return view;
    }

    private void loadAndPlayVideo() {
        try{
            String videoPath = "android.resource://" + getActivity().getPackageName() + "/" + R.raw.bgvidpremium;
            Uri videoUri = Uri.parse(videoPath);
            videobg.setVideoURI(videoUri);
            videobg.start();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}