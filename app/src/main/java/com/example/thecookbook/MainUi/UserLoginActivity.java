package com.example.thecookbook.MainUi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.thecookbook.AdminUI.AdminAllActivity;
import com.example.thecookbook.DBClass.DBHelper;
import com.example.thecookbook.InflUI.Influencer_All_Activity;
import com.example.thecookbook.R;
import com.example.thecookbook.UserUI.UserAllActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserLoginActivity extends AppCompatActivity {
    TextView registertext;
    EditText UserName,Password;
    Button loginbtn;
    int UserID;
    private DBHelper dbHelper;
    Spinner UserType;
    private int InfID;
    private ArrayAdapter<String> adapter;
    private List<String> userTypes;
    private boolean isFirstSelection = true;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        UserName=(EditText) findViewById(R.id.UserNameLogin);
        Password = (EditText) findViewById(R.id.PasswordLogin);
        loginbtn = (Button) findViewById(R.id.loginbtn);
        registertext = (TextView) findViewById(R.id.registertextuser);
        UserType = (Spinner) findViewById(R.id.Usertypespinnerlogin);
        dbHelper = new DBHelper(this);
        dbHelper.OpenDB();
        userTypes = new ArrayList<>(Arrays.asList("User type", "User", "Influencer"));
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, userTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        UserType.setAdapter(adapter);
        UserType.setSelection(0);
        UserType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            private boolean isFirstSelection = true;
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!isFirstSelection) {
                    if (userTypes.get(0).equals("User type")) {
                        String selectedItem = UserType.getSelectedItem().toString();
                        userTypes.remove(0);
                        adapter.notifyDataSetChanged();
                        if (!selectedItem.equals("User type")) {
                            UserType.setSelection(userTypes.indexOf(selectedItem));

                        }
                    }
                } else {
                    isFirstSelection = false;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(UserName.getText().toString().isEmpty()&&Password.getText().toString().isEmpty()){
                    Toast.makeText(UserLoginActivity.this, "Please Fill both fields", Toast.LENGTH_SHORT).show();
                } else if (UserName.getText().toString().equals("Admin")&&Password.getText().toString().equals("12345")) {
                    Toast.makeText(UserLoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(UserLoginActivity.this, AdminAllActivity.class);
                    startActivity(intent);



                }else if(UserType.getSelectedItem().toString().equals("User type")){
                    Toast.makeText(UserLoginActivity.this, "Please select user type", Toast.LENGTH_SHORT).show();

                }else if (UserType.getSelectedItem().toString().equals("Influencer")) {
                    InfID = dbHelper.CheckLoginInfluencer(UserName.getText().toString(), Password.getText().toString());
                    if(InfID != 0){
                        Toast.makeText(UserLoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(UserLoginActivity.this, Influencer_All_Activity.class);
                        intent.putExtra("InfID", InfID);
                        intent.putExtra("InfName", UserName.getText().toString());
                        startActivity(intent);
                    }else {
                        Toast.makeText(UserLoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                    }

                } else{
                    UserID = dbHelper.CheckLoginUser(UserName.getText().toString(),Password.getText().toString());
                    if(UserID==0){
                        Toast.makeText(UserLoginActivity.this, "Invalid Login details", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(UserLoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(UserLoginActivity.this, UserAllActivity.class);
                        intent.putExtra("UserID",UserID);
                        startActivity(intent);
                    }

                }
            }
        });
        registertext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentreg =new Intent(UserLoginActivity.this, MainRegisterActivity.class);
                startActivity(intentreg);
            }
        });


    }
}