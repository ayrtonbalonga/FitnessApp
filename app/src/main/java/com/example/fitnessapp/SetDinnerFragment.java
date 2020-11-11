package com.example.fitnessapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.example.fitnessapp.Dinner.*;


import static com.example.fitnessapp.UserInfo.getUsername;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SetDinnerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SetDinnerFragment extends Fragment {

    TextView txtDCalery;
    TextView txtDDescription;
    TextView tvTes;
    static boolean isDinneravail;
    private String DCal;
    private String DDes;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SetDinnerFragment() {
        // Required empty public cons
        //tructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SetDinnerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SetDinnerFragment newInstance(String param1, String param2) {
        SetDinnerFragment fragment = new SetDinnerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_set_dinner,container,false);

        txtDCalery = (TextView) v.findViewById(R.id.txtDCalorie);

        txtDDescription = (TextView) v.findViewById(R.id.txtDDescription);
        displayDinner();

        Button btnSaveD = (Button) v.findViewById(R.id.btnSaveD);

        btnSaveD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(!validateDCal() | !validateDDes()) {
                    return;

                }else {


                    DCal = txtDCalery.getText().toString();
                    DDes = txtDDescription.getText().toString();

                    writeDinner(DCal, DDes);
                    Toast.makeText(getContext(), "Data has been save", Toast.LENGTH_LONG).show();
                    displayDinner();

                }


            }
        });

        Button btnback = (Button) v.findViewById(R.id.btnBackD);

        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){

                SetCalorieFragment fragment = new SetCalorieFragment();
                getFragmentManager().beginTransaction().replace(R.id.container ,fragment).commit();
            }
        });

        return v;
    }


    private void displayDinner() {

        DatabaseReference reff = FirebaseDatabase.getInstance().getReference("/Calori/" + getUsername() + "/" + getCurrentDate());

        reff.orderByChild(getCurrentDate()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                if (snapshot.exists()) {
                    //String tr =snapshot.getKey();
                    if((snapshot.getKey().equals(getCurrentDate()))) {
                        isDinneravail = true;

                        String dbLC = snapshot.child("DinnerCal").getValue(String.class);
                        String dbLD = snapshot.child("DinnerDesc").getValue(String.class);


                        setDinnerCalorie(dbLC);
                        setDinnerdescription(dbLD);
                        txtDCalery.setText(getDinnerCalorie());
                        txtDDescription.setText(getDinnerdescription());
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }


        });
    }


    public String getCurrentDate() {
        String date;

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        date = simpleDateFormat.format(new Date());

        return date;
    }

    private void writeDinner(String dCal, String dDes) {

        DatabaseReference reff = FirebaseDatabase.getInstance().
                getReference("/Calori");
           // Dinner dinner = new Dinner(dCal,dDes);

            //reff.child(getUsername()).child(getCurrentDate()).setValue(dinner);
        setDinnerdescription(dDes);
        setDinnerCalorie(dCal);
        //Writing Hashmap

        Map<String, Object> mHashmap = new HashMap<>();

        mHashmap.put("/"+getUsername()+"/"+getCurrentDate()+"/DinnerCal", getDinnerCalorie());
        mHashmap.put("/"+getUsername()+"/"+getCurrentDate()+"/DinnerDesc", getDinnerdescription());
        reff.updateChildren(mHashmap);

    }


    private void showCustomeDialog() {

        DCal = txtDCalery.getText().toString();
        DDes = txtDDescription.getText().toString();

        setDinnerCalorie(DCal);
        setDinnerdescription(DDes);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        builder.setMessage("You have already inserted you breakfast details, Do you want to update them");
        builder.setCancelable(true);
        builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

    
                Toast.makeText(getContext(),"update",Toast.LENGTH_SHORT).show();

                writeDinner(getDinnerCalorie(),getDinnerdescription());
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getContext(),"Canceled",Toast.LENGTH_SHORT).show();
            }
        });
        builder.show();

    }

/*
    public void updateDinner() {

        if (isDCalChanged() || isDDesChanged()) {
            Toast.makeText(getContext(), "Data has been updated", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(getContext(), "Data is the same and not been uodated", Toast.LENGTH_LONG).show();
        }
    }

    private boolean isDDesChanged() {
        DDes=txtDDescription.getText().toString();
        DatabaseReference reff = FirebaseDatabase.getInstance().getReference("/Calori/"+getUsername()+"/"+getCurrentDate());
        reff.child(getUsername()).child(getCurrentDate());
        if (!DDes.equals(getDinnerdescription())) {

            setDinnerdescription(DDes);
            reff.child("LunchDesc").setValue(getDinnerdescription());

            return true;
        } else {
            return false;
        }
    }

    private boolean isDCalChanged() {
        DCal= txtDCalery.getText().toString();
        DatabaseReference reff = FirebaseDatabase.getInstance().getReference("/Calori/"+getUsername()+"/"+getCurrentDate());
        reff.child(getUsername()).child(getCurrentDate());
        if (!DCal.equals(getDinnerCalorie())) {
            setDinnerCalorie(DCal);
            reff.child("DinnerCal").setValue(getDinnerCalorie());

            return true;
        } else {
            return false;
        }
    }


 */
    private Boolean validateDCal() {

        String val = txtDCalery.getText().toString().trim();

        if (val.isEmpty()) {
            txtDCalery.setError("Field cannot be empty");
            return false;
        } else {
            txtDCalery.setError(null);

            return true;
        }

    }

    private Boolean validateDDes() {

        String val = txtDDescription.getText().toString().trim();

        if (val.isEmpty()) {
            txtDDescription.setError("Field cannot be empty");
            return false;
        } else {
            txtDDescription.setError(null);

            return true;
        }

    }




}