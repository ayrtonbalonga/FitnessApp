package com.example.fitnessapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.UUID;

public class signIn extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private EditText txtFullName, txtUsername, txtEmail, txtPhone, txtPassword;

    private String fname, username, email, phone, password;

    private DatabaseReference reff;
    Users users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        txtFullName = (EditText) findViewById(R.id.txtFullName);
        txtUsername = (EditText) findViewById(R.id.txtUsername);
        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtPhone = (EditText) findViewById(R.id.txtPhone);
        txtPassword = (EditText) findViewById(R.id.txtPassword);


        Button btnSignIn = (Button) findViewById(R.id.btnSignIn);
        users = new Users();

        btnSignIn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                loginUser(view);

            }
        });
    }

    public void loginUser(View view) {
        if (!validateFullname() | !validatePassword() | !validateEmail() | !validateUsername() | !validatePhone()) {
            return;
        } else {

            isUser();
        }

    }

    private void isUser() {

        final String tUsername = txtUsername.getText().toString();


        final DatabaseReference reff = FirebaseDatabase.getInstance().getReference("Users");


        Query checkUser = reff.orderByChild("username").equalTo(tUsername);

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {

                    String dbUsername = snapshot.child(txtUsername.getText().toString()).getValue(String.class);

                    if (dbUsername.equals(txtUsername.getText().toString())) {


                        txtUsername.setError(null);


                        fname = txtFullName.getText().toString().trim();
                        username = txtUsername.getText().toString().trim();
                        email = txtEmail.getText().toString().trim();
                        phone = txtPhone.getText().toString().trim();
                        password = txtPassword.getText().toString();


                        users = new Users(fname, username, email, phone, password);

                        reff.child(username).setValue(users);

                        Toast.makeText(getApplicationContext(), "You have been register", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(signIn.this, MainActivity.class);
                        startActivity(intent);


                    }

                } else {
                    txtUsername.setError("User already used ");
                    txtUsername.requestFocus();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private Boolean validateFullname() {

        String val = txtFullName.getText().toString().trim();

        if (val.isEmpty()) {
            txtFullName.setError("Field cannot be empty");
            return false;
        } else {
            txtFullName.setError(null);

            return true;
        }

    }

    private Boolean validateUsername() {

        String val = txtUsername.getText().toString().trim();

        if (val.isEmpty()) {
            txtUsername.setError("Field cannot be empty");
            return false;
        } else {
            txtUsername.setError(null);

            return true;
        }

    }

    private Boolean validateEmail() {

        String val = txtEmail.getText().toString().trim();

        if (val.isEmpty()) {
            txtEmail.setError("Field cannot be empty");
            return false;
        } else {
            txtEmail.setError(null);

            return true;
        }

    }

    private Boolean validatePhone() {

        String val = txtPhone.getText().toString().trim();

        if (val.isEmpty() || (val.length() != 10)) {
            txtPhone.setError("Phone must be 10 digit");
            return false;
        } else {
            txtPhone.setError(null);
            return true;
        }


    }

    private Boolean validatePassword() {

        String val = txtPassword.getText().toString().trim();

        if (val.isEmpty()) {
            txtPassword.setError("Field cannot be empty");
            return false;
        } else {
            txtPassword.setError(null);

            return true;
        }

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();

    }


}
