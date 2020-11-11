package com.example.fitnessapp;

import android.app.Application;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;

public  class UserInfo extends Application {

    private static String FullName;
    private static String Username;
    private static String Email;
    private static String Phone;
    private static String Password;
    private static boolean IsKg;
    private static  boolean IsCm;
    private  static double l;
    private  static double b;
    private  static double d;

    public UserInfo() {
    }

    public UserInfo(String fname, String username, String email, String phone, String password) {
        this.FullName = fname;
        this.Username = username;
        this.Email = email;
        this.Phone = phone;
        this.Password = password;
    }


    public static String getFullName() {
        return FullName;
    }

    public static void setFullName(String fullName) {
        FullName = fullName;
    }

    public static String getUsername() {
        return Username;
    }

    public static void setUsername(String username) {
        Username = username;
    }

    public static String getEmail() {
        return Email;
    }

    public static void setEmail(String email) {
        Email = email;
    }

    public static String getPhone() {
        return Phone;
    }

    public static void setPhone(String phone) {
        Phone = phone;
    }

    public static String getPassword() {
        return Password;
    }

    public static void setPassword(String password) {
        Password = password;
    }


    public static boolean getIsKg() {
        return IsKg;
    }

    public static void setIsKg(boolean isKg) {
        IsKg = isKg;
    }

    public static boolean getIsCm() {
        return IsCm;
    }

    public static void setIsCm(boolean isCm) {
        IsCm = isCm;
    }

    public static double getL() {
        return l;
    }

    public static void setL(double l) {
        UserInfo.l = l;
    }

    public static double getB() {
        return b;
    }

    public static void setB(double b) {
        UserInfo.b = b;
    }

    public static double getD() {
        return d;
    }

    public static void setD(double d) {
        UserInfo.d = d;
    }
}