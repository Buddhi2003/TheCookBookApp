package com.example.thecookbook.MainUi;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.thecookbook.Classes.InfluencerClass;
import com.example.thecookbook.Classes.UserClass;
import com.example.thecookbook.DBClass.DBHelper;
import com.example.thecookbook.R;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;

public class MainRegisterActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 98;
    ShapeableImageView pfp;
    EditText Name,Password,Bio,ConfirmPassword;
    Spinner UserType;
    TextInputLayout biobox;
    Button Registerbtn;
    private DBHelper dbHelper;
    private Uri imagepath;
    private Bitmap imagetostore;
    MaterialTextView logintext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_register);

        pfp =(ShapeableImageView) findViewById(R.id.regpfp);
        Name = (EditText) findViewById(R.id.NameReg);
        UserType = (Spinner) findViewById(R.id.Usertypespinner);
        logintext = (MaterialTextView) findViewById(R.id.loginheretext);
        Password = (EditText) findViewById(R.id.PasswordReg);
        ConfirmPassword = (EditText) findViewById(R.id.confirmPasswordReg);
        Bio = (EditText) findViewById(R.id.BioReg);
        Registerbtn = (Button) findViewById(R.id.btnregister);
        biobox = (TextInputLayout) findViewById(R.id.bioboxreg);
        dbHelper = new DBHelper(this);
        dbHelper.OpenDB();

        pfp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChooseImage();
            }
        });
        Registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StoreDetails();
            }
        });
        String[] userTypes = {"User","Influencer"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, userTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        UserType.setAdapter(adapter);
        UserType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                if (selectedItem.equals("User")){
                    Bio.setVisibility(View.GONE);
                }else if(selectedItem.equals("Influencer")){
                    Bio.setVisibility(View.VISIBLE);
                }else {
                    Bio.setVisibility(View.GONE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        UserType.setSelection(0);

        logintext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainRegisterActivity.this,UserLoginActivity.class);
                startActivity(intent);
            }
        });

    }

    private void ChooseImage() {
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
                pfp.setImageBitmap(imagetostore);
            }
        }catch(Exception e){
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    private void StoreDetails(){
        if(Name.getText().toString().isEmpty()||Password.getText().toString().isEmpty()||imagetostore==null){
            Toast.makeText(this, "Please add all details", Toast.LENGTH_SHORT).show();
        }else if(!Password.getText().toString().equals(ConfirmPassword.getText().toString())){
            Toast.makeText(this, "Password does not match", Toast.LENGTH_SHORT).show();
        }
        else{
            if(UserType.getSelectedItem().toString().equals("Influencer")){
                InfluencerClass influencer= new InfluencerClass(Name.getText().toString(),Password.getText().toString(),Bio.getText().toString(),imagetostore);
                if(dbHelper.checkinfexist(Name.getText().toString(),Password.getText().toString())){
                    Toast.makeText(this, "User already exists", Toast.LENGTH_SHORT).show();
                }else{
                    if(dbHelper.CreateNewInfluencer(influencer)){
                        Name.setText("");
                        Password.setText("");
                        ConfirmPassword.setText("");
                        Bio.setText("");
                        pfp.setImageResource(R.drawable.baseline_camera_alt_24);
                        pfp.setScaleType(ImageView.ScaleType.CENTER);
                        Toast.makeText(this, "Registration Successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainRegisterActivity.this,UserLoginActivity.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(this, "Registration Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }else{
                if(dbHelper.checkuserexist(Name.getText().toString(),Password.getText().toString())){
                    Toast.makeText(this, "User already exists", Toast.LENGTH_SHORT).show();
                }else{
                    UserClass userClass = new UserClass(Name.getText().toString(),Password.getText().toString(),imagetostore);
                    if(dbHelper.CreateNewUser(userClass)){
                        Name.setText("");
                        Password.setText("");
                        ConfirmPassword.setText("");
                        Bio.setText("");
                        pfp.setImageResource(R.drawable.baseline_camera_alt_24);
                        pfp.setScaleType(ImageView.ScaleType.CENTER);
                        Toast.makeText(getApplicationContext(), "Detail added Successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainRegisterActivity.this,UserLoginActivity.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(getApplicationContext(), "Detail adding failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }

    }
}