package com.example.fitnessapp;

import android.widget.Toast;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Users {

    private String FullName;
    private String Username;
    private String Email;
    private String Phone;
    private  String Password;



    public Users() {
    }

    public Users (String fname,String username,String email,String phone,String password){
      this.FullName = fname;
      this.Username = username;
      this.Email = email;
      this.Phone = phone;
      this.Password = password;
    }


    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}





