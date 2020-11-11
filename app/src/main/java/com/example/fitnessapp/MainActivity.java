package com.example.fitnessapp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;


import android.app.Notification;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class MainActivity extends AppCompatActivity {
    EditText Username,Password;
    UserInfo userInfo;




    //private EditText Username;
    // private EditText Password;
    private int counter = 5;
    //private Notification.MessagingStyle.Message Username;
    // private Notification.MessagingStyle.Message Password;
    //private TextView Info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Username = (EditText) findViewById(R.id.txtUsername);
        Password = (EditText) findViewById(R.id.txtPassword);
        Button login = findViewById(R.id.btnLogIn);
        Button signIn = (Button) findViewById(R.id.btnSignIn);

        login.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {

                loginUser(v);

                //Intent intent = new Intent(getApplicationContext(), homePage.class);
                //startActivity(intent);

            }
        });


        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, signIn.class);
                startActivity(intent);
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.N)

    public void onClick(View view) {

        loginUser(view);

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private Boolean validateUsername() {

        String val = Username.getText().toString().trim();

        if (val.isEmpty()) {
            Username.setError("Field cannot be empty");
            return false;
        } else {
            Username.setError(null);

            return true;
        }

    }



    private Boolean validatePassword() {

        String val = Password.getText().toString();

        if (val.isEmpty()) {

            Password.setError("Field connot be empty");
            return false;
        } else {
            Password.setError(null);
            return true;
        }


    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void loginUser(View view) {
        if (!validateUsername() | !validatePassword()) {
            return;
        } else {
            isUser();
        }

    }

    private void isUser() {

        final String tUsername = Username.getText().toString();
        final String tPasword = Password.getText().toString();

        DatabaseReference reff = FirebaseDatabase.getInstance().getReference("Users");

        Query checkUser = reff.orderByChild("username").equalTo(tUsername);

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {

                    Username.setError(null);

                    String dbPassword = snapshot.child(tUsername).child("password").getValue(String.class);

                    if (dbPassword.equals(tPasword)) {

                        Username.setError(null);

                        String dbfullname = snapshot.child(tUsername).child("fullname").getValue(String.class);
                        String dbphone = snapshot.child(tUsername).child("phone").getValue(String.class);
                        String dbemail = snapshot.child(tUsername).child("email").getValue(String.class);
                        String dbusername = snapshot.child(tUsername).child("username").getValue(String.class);

                        userInfo = new UserInfo(dbfullname,dbusername,dbemail,dbphone,dbPassword);


                        Intent intent = new Intent(getApplicationContext(), homePage.class);
                        startActivity(intent);

                    } else {
                        Password.setError("wrong Password");
                        Password.requestFocus();
                    }
                } else {
                    Username.setError("No such User");
                    Username.requestFocus();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}
