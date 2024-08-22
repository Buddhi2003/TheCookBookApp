package com.example.thecookbook.UserUI.Profile.PopupSettings;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.VideoView;

import com.example.thecookbook.R;

public class AboutFragment extends DialogFragment {
    VideoView videobg;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about, container, false);
        videobg= view.findViewById(R.id.bgvidabout);
        loadAndPlayVideo();
        return view;

    }

    private void loadAndPlayVideo() {
        try{
            String videoPath = "android.resource://" + getActivity().getPackageName() + "/" + R.raw.bgvidabout;
            Uri videoUri = Uri.parse(videoPath);
            videobg.setVideoURI(videoUri);
            videobg.start();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Window window = getDialog().getWindow();
        if (window != null) {
            window.setBackgroundDrawableResource(android.R.color.transparent);
        }
    }
}