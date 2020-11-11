package com.example.fitnessapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.example.fitnessapp.UserInfo.getB;
import static com.example.fitnessapp.UserInfo.getD;
import static com.example.fitnessapp.UserInfo.getL;
import static com.example.fitnessapp.UserInfo.getUsername;
import static com.example.fitnessapp.UserInfo.setB;
import static com.example.fitnessapp.UserInfo.setD;
import static com.example.fitnessapp.UserInfo.setL;

public class HomeFragment extends Fragment {

   // boolean isBCalAvai;
    TextView tvDcal;
   // double totalDayCal ;
   // static double bCal;
  //  static double lCal;
    //static double dCal;

    TextView tvhWeight;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        tvDcal = (TextView) view.findViewById(R.id.tvDcal);

tvhWeight = (TextView) view.findViewById(R.id.tvhWeight);

      double totalDayCal = getL() + getD()+ getB();
        //totalDayCal =lCal+bCal+lCal;
       tvDcal.setText(String.valueOf(totalDayCal));

displayWeight();

return view;
    }

    private void displayWeight() {
        //double bCal;
        DatabaseReference reff = FirebaseDatabase.getInstance().getReference("/Weight/"
                + getUsername()+"/"+getCurrentDate());
        reff.orderByChild(getCurrentDate()).addListenerForSingleValueEvent(new ValueEventListener() {


            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {

                    if ((snapshot.getKey().equals(getCurrentDate()))) {

                        double w = snapshot.child("CurrentWeight").getValue(double.class);

                        tvhWeight.setText(String.valueOf(w));

                    }
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        // return bCal;
    }


    public String getCurrentDate() {
        String date;

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        date = simpleDateFormat.format(new Date());

        return date;
    }
}