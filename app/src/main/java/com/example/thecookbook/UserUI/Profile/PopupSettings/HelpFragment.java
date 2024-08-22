package com.example.thecookbook.UserUI.Profile.PopupSettings;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.VideoView;

import com.example.thecookbook.R;

public class HelpFragment extends DialogFragment {
    VideoView videobg;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_help, container, false);
        videobg = view.findViewById(R.id.videobghelp);
        loadAndPlayVideo();
        return view;
    }

    private void loadAndPlayVideo() {
        try{
            String videoPath = "android.resource://" + getActivity().getPackageName() + "/" + R.raw.bgvidhelp;
            Uri videoUri = Uri.parse(videoPath);
            videobg.setVideoURI(videoUri);
            videobg.start();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Window window = getDialog().getWindow();
        if (window != null) {
            window.setBackgroundDrawableResource(android.R.color.transparent);
        }
    }
}