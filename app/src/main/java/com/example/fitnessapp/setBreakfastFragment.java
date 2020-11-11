package com.example.fitnessapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.solver.widgets.Snapshot;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


import static com.example.fitnessapp.Breakfast.*;
import static com.example.fitnessapp.UserInfo.getUsername;

public class setBreakfastFragment extends Fragment {

    TextView txtBCalery;
    TextView txtBDescription;
    static boolean isbreakavail = false;
    private String bCal;
    private String bDes;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public setBreakfastFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static setBreakfastFragment newInstance(String param1, String param2) {
        setBreakfastFragment fragment = new setBreakfastFragment();
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
        View v = inflater.inflate(R.layout.fragment_set_breakfast, container, false);

        txtBCalery = (TextView) v.findViewById(R.id.txtBCalorie);

        txtBDescription = (TextView) v.findViewById(R.id.txtBDescription);
        displayBreak();
        Button btnSaveB = (Button) v.findViewById(R.id.btnSaveB);

        btnSaveB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(!validateBCal() | !validateBDes()) {
                    return;

                }else {

                    if (isbreakavail!=true) {
                        bCal = txtBCalery.getText().toString();
                        bDes = txtBDescription.getText().toString();

                        writeBreakfast(Double.parseDouble(bCal), bDes);
                        displayBreak();
                    }else {

                        showCustomeDialog();
                    }

                }

            }
        });

        Button btnbackB = (Button) v.findViewById(R.id.btnBackB);

        btnbackB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SetCalorieFragment fragment = new SetCalorieFragment();
                getFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
            }
        });

        return v;
    }


    private void displayBreak() {

        DatabaseReference reff = FirebaseDatabase.getInstance().getReference("/Calori/" + getUsername() + "/" + getCurrentDate());

        reff.orderByChild(getCurrentDate()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                if (snapshot.exists()) {

                    if((snapshot.getKey().equals(getCurrentDate()))){
                        isbreakavail = true;
                        double dbBC = snapshot.child("BreakfastCal").getValue(double.class);
                        String dbBD = snapshot.child("BreakfastDesc").getValue(String.class);


                        setBreakfastCalorie(dbBC);
                        setBreakfastdescription(dbBD);
                        txtBCalery.setText(String.valueOf(getBreakfastCalorie()));
                        txtBDescription.setText(getBreakfastdescription());
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

    private void writeBreakfast(double bCal, String bDes) {

        DatabaseReference reff = FirebaseDatabase.getInstance().
                getReference("/Calori");

        setBreakfastdescription(bDes);
        setBreakfastCalorie(bCal);
        //Writing Hashmap
        Map<String, Object> mHashmap = new HashMap<>();

        mHashmap.put("/"+getUsername()+"/"+getCurrentDate()+"/BreakfastCal", getBreakfastCalorie());
        mHashmap.put("/"+getUsername()+"/"+getCurrentDate()+"/BreakfastDesc", getBreakfastdescription());
        reff.updateChildren(mHashmap);

        Toast.makeText(getContext(), "Data has been saved", Toast.LENGTH_LONG).show();



    }


    private void showCustomeDialog() {
        bCal = txtBCalery.getText().toString();
        bDes = txtBDescription.getText().toString();
        setBreakfastCalorie(Double.parseDouble(bCal));
        setBreakfastdescription(bDes);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        builder.setMessage("You have already inserted you breakfast details, Do you want to update them");
        builder.setCancelable(true);
        builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                writeBreakfast(getBreakfastCalorie(),getBreakfastdescription());
                Toast.makeText(getContext(),"update",Toast.LENGTH_SHORT).show();


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

    private Boolean validateBCal() {

        String val = txtBCalery.getText().toString();

        if (val.isEmpty()) {
            txtBCalery.setError("Field cannot be empty");
            return false;
        } else {
            txtBCalery.setError(null);

            return true;
        }

    }

    private Boolean validateBDes() {

        String val = txtBDescription.getText().toString().trim();

        if (val.isEmpty()) {
            txtBDescription.setError("Field cannot be empty");
            return false;
        } else {
            txtBDescription.setError(null);

            return true;
        }

    }


}


