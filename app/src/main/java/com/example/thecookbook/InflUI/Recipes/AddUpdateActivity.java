package com.example.thecookbook.InflUI.Recipes;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.thecookbook.Classes.UpdateClass;
import com.example.thecookbook.DBClass.DBHelper;
import com.example.thecookbook.R;
import com.google.android.material.imageview.ShapeableImageView;

public class AddUpdateActivity extends AppCompatActivity {
    int InfID;
    private DBHelper dbHelper;
    private TextView updatedetail;
    private ShapeableImageView updateimage;
    private Button updatebutton;
    private Uri imagepath;
    private Bitmap imagetostore;
    private static final int PICK_IMAGE_REQUEST = 98;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_update);
        Intent intent = getIntent();
        InfID = intent.getIntExtra("InfID",0);
        dbHelper = new DBHelper(this);
        dbHelper.OpenDB();
        updatedetail = findViewById(R.id.updatecontext);
        updateimage = findViewById(R.id.updateimage);
        updatebutton = findViewById(R.id.addupdatebtn);

        updateimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickimage();
            }
        });
        updatebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addupdate();
            }
        });

    }
    private void pickimage(){
        try{
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent, PICK_IMAGE_REQUEST);

        }catch(Exception e){
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        try{
            super.onActivityResult(requestCode, resultCode, data);
            if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null){
                imagepath=data.getData();
                imagetostore= MediaStore.Images.Media.getBitmap(getContentResolver(),imagepath);
                updateimage.setImageBitmap(imagetostore);
            }
        }catch(Exception e){
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    private void addupdate(){
        if (updatedetail.getText().toString().isEmpty() || imagetostore==null){
            Toast.makeText(getApplicationContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
        }else{
            UpdateClass update = new UpdateClass(updatedetail.getText().toString(),imagetostore,InfID);
            if (dbHelper.addUpdate(update)){
                Toast.makeText(getApplicationContext(), "Update Added", Toast.LENGTH_SHORT).show();
                updatedetail.setText("");
                updateimage.setImageBitmap(null);
            }else{
                Toast.makeText(getApplicationContext(), "Update not added", Toast.LENGTH_SHORT).show();
            }
        }

    }

}