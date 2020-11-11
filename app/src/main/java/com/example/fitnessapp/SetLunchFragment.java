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

import static com.example.fitnessapp.Lunch.*;
import static com.example.fitnessapp.UserInfo.getUsername;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SetLunchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SetLunchFragment extends Fragment {
    TextView txtLCalery;
    TextView txtLDescription;

    static boolean isLunchavail = false;
    private String lCal;
    private String lDes;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SetLunchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SetLunchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SetLunchFragment newInstance(String param1, String param2) {
        SetLunchFragment fragment = new SetLunchFragment();
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

        View v =  inflater.inflate(R.layout.fragment_set_lunch,container,false);


        txtLCalery = (TextView) v.findViewById(R.id.txtLCalorie);

        txtLDescription = (TextView) v.findViewById(R.id.txtLDescription);
        displayLunch();

        Button btnSaveL = (Button) v.findViewById(R.id.btnSaveL);

        btnSaveL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(!validateLCal() | !validateLDes()) {
                    return;

                }else {

                    lCal = txtLCalery.getText().toString();
                    lDes = txtLDescription.getText().toString();

                    writeLunch(lCal, lDes);
                    Toast.makeText(getContext(), "Data has been save", Toast.LENGTH_LONG).show();
                    displayLunch();

                }


            }
        });



        Button btnbackL = (Button) v.findViewById(R.id.btnBackL);

        btnbackL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){

                SetCalorieFragment fragment = new SetCalorieFragment();
                getFragmentManager().beginTransaction().replace(R.id.container ,fragment).commit();
            }
        });

        return v;
    }


    private void displayLunch() {

        DatabaseReference reff = FirebaseDatabase.getInstance().getReference("/Calori/" + getUsername() + "/" + getCurrentDate());

        reff.orderByChild(getCurrentDate()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                if (snapshot.exists()) {
                   // String tr =snapshot.child(getCurrentDate()).toString();
                    if(((snapshot.getKey().equals(getCurrentDate())))) {
                        isLunchavail = true;
                        String dbLC = snapshot.child("LunchCal").getValue(String.class);
                        String dbLD = snapshot.child("LunchDesc").getValue(String.class);


                        Lunch.setLunchCalorie(dbLC);
                        Lunch.setLunchdescription(dbLD);
                        txtLCalery.setText(getLunchCalorie());
                        txtLDescription.setText(Lunch.getLunchdescription());
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

    private void writeLunch(String lCal, String lDes) {

        DatabaseReference reff = FirebaseDatabase.getInstance().
                getReference("/Calori");

        Lunch.setLunchdescription(lDes);
        Lunch.setLunchCalorie(lCal);
        //Writing Hashmap
        Map<String, Object> mHashmap = new HashMap<>();

        mHashmap.put("/"+getUsername()+"/"+getCurrentDate()+"/LunchCal", getLunchCalorie());
        mHashmap.put("/"+getUsername()+"/"+getCurrentDate()+"/LunchDesc", Lunch.getLunchdescription());
        reff.updateChildren(mHashmap);





    }


    private void showCustomeDialog() {
        lCal = txtLCalery.getText().toString();
        lDes = txtLDescription.getText().toString();
        setLunchCalorie(lCal);
        setLunchdescription(lDes);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        builder.setMessage("You have already inserted you breakfast details, Do you want to update them");
        builder.setCancelable(true);
        builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                writeLunch(getLunchCalorie(),getLunchdescription());

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


    private Boolean validateLCal() {

        String val = txtLCalery.getText().toString().trim();

        if (val.isEmpty()) {
            txtLCalery.setError("Field cannot be empty");
            return false;
        } else {
            txtLCalery.setError(null);

            return true;
        }

    }

    private Boolean validateLDes() {

        String val = txtLDescription.getText().toString().trim();

        if (val.isEmpty()) {
            txtLDescription.setError("Field cannot be empty");
            return false;
        } else {
            txtLDescription.setError(null);

            return true;
        }

    }



}