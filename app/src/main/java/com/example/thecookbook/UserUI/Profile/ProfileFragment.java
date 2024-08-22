package com.example.thecookbook.UserUI.Profile;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.thecookbook.Classes.UserClass;
import com.example.thecookbook.DBClass.DBHelper;
import com.example.thecookbook.MainUi.Main_Login_Activity;
import com.example.thecookbook.R;
import com.example.thecookbook.UserUI.Profile.PopupSettings.AboutFragment;
import com.example.thecookbook.UserUI.Profile.PopupSettings.HelpFragment;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.imageview.ShapeableImageView;


public class ProfileFragment extends Fragment {
    int UserID;
    private DBHelper dbHelper;
    private ShapeableImageView Profileimg;
    private MaterialCardView ProfileDetailsCard, SubInfCard, HelpCard, AboutCard, DeleteAccCard, LogoutCard;
    View OverlayView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        if (getArguments()!=null) {
            UserID = getArguments().getInt("UserID");
        }
        dbHelper= new DBHelper(getContext());
        dbHelper.OpenDB();
        OverlayView = view.findViewById(R.id.overlayViewprof);
        Profileimg = view.findViewById(R.id.Profileimg);
        ProfileDetailsCard = view.findViewById(R.id.ProfileDetailsCard);
        SubInfCard = view.findViewById(R.id.SubInfCard);
        HelpCard = view.findViewById(R.id.HelpCard);
        AboutCard = view.findViewById(R.id.AboutCard);
        DeleteAccCard = view.findViewById(R.id.DeleteAccCard);
        LogoutCard = view.findViewById(R.id.LogoutCard);

        UserClass user = dbHelper.getUserdetails(UserID);
        Profileimg.setImageBitmap(user.getProfileImage());

        ProfileDetailsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), UserProfileDetailActivity.class);
                intent.putExtra("UserID", UserID);
                startActivity(intent);
            }
        });
        SubInfCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SubbedInfListActivity.class);
                intent.putExtra("UserID", UserID);
                startActivity(intent);

            }
        });
        HelpCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HelpFragment dialog = new HelpFragment();
                dialog.show(getActivity().getSupportFragmentManager(), "Help");
            }
        });
        AboutCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AboutFragment dialog = new AboutFragment();
                dialog.show(getActivity().getSupportFragmentManager(), "About");
            }
        });
        DeleteAccCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
                alertDialog.setTitle("Delete Account");
                alertDialog.setMessage("Are you sure you want to delete this account?");
                alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(dbHelper.DeleteUser(UserID)){
                            Toast.makeText(getContext(), "Deleted successfuly", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getContext(), Main_Login_Activity.class);
                            startActivity(intent);
                            getActivity().finish();
                        }else {
                            Toast.makeText(getContext(), "Failed to delete", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getContext(), "Cancelled", Toast.LENGTH_SHORT).show();
                    }
                });
                alertDialog.show();
            }
        });
        LogoutCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Logged out successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), Main_Login_Activity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
        return view;
    }
}