package com.example.fitnessapp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toolbar;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.example.fitnessapp.UserInfo.getUsername;
import static com.example.fitnessapp.UserInfo.setB;
import static com.example.fitnessapp.UserInfo.setD;
import static com.example.fitnessapp.UserInfo.setL;

public class homePage extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        //setContentView(R.layout.);
    BCal();
    LCal();
    DCal();


        bottomNavigationView = findViewById(R.id.bottomNav);
        bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavMethod);
        getSupportFragmentManager().beginTransaction().replace(R.id.container,new HomeFragment()).commit();



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menusetting){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menusetting,menusetting);
        return true;

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item){
       int id =item.getItemId();

       if (id == R.id.settings){
           Intent intent = new Intent(homePage.this,settingsActivity.class);
           startActivity(intent);
           return  true;
       }


       return super.onOptionsItemSelected(item);
    }


    private BottomNavigationView.OnNavigationItemSelectedListener bottomNavMethod =new
            BottomNavigationView.OnNavigationItemSelectedListener(){
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

    Fragment fragment = null;
    switch (menuItem.getItemId()){

        case R.id.home:
            fragment = new HomeFragment();
        break;
        case R.id.goals:
            fragment = new GoalsFragment();
        break;
        case R.id.account:
            fragment = new AccountFragment();
        break;
    }
    getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment).commit();

    return true;
                }

    };

    private void BCal() {
        //double bCal;
        DatabaseReference reff = FirebaseDatabase.getInstance().getReference("/Calori/"
                + getUsername()+"/"+getCurrentDate());
        reff.orderByChild(getCurrentDate()).addListenerForSingleValueEvent(new ValueEventListener() {


            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {

                    if ((snapshot.getKey().equals(getCurrentDate()))) {

                        double bcal = snapshot.child("BreakfastCal").getValue(double.class);

                        setB(bcal);
                    }
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        // return bCal;
    }
    private void LCal() {

        DatabaseReference reff = FirebaseDatabase.getInstance().getReference("/Calori/"
                + getUsername()+"/"+getCurrentDate());

        reff.orderByChild(getCurrentDate()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                if (snapshot.exists()) {

                    if ((snapshot.getKey().equals(getCurrentDate()))) {

                        double lcal = snapshot.child("LunchCal").getValue(double.class);

                        setL(lcal);
                    }


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }


        });
        //   return lCal;
    }
    private void DCal() {

        DatabaseReference reff = FirebaseDatabase.getInstance().getReference("/Calori/"
                + getUsername()+"/"+getCurrentDate());

        reff.orderByChild(getCurrentDate()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                if (snapshot.exists()) {

                    if ((snapshot.getKey().equals(getCurrentDate()))) {

                        double dcal = snapshot.child("DinnerCal").getValue(double.class);
                        setD(dcal);
                    }


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }


        });
        //return dCal;
    }
    public String getCurrentDate() {
        String date;

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        date = simpleDateFormat.format(new Date());

        return date;
    }


}
