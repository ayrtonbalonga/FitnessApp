package com.example.fitnessapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GoalsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GoalsFragment extends Fragment {



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public GoalsFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static GoalsFragment newInstance(String param1, String param2) {
        GoalsFragment fragment = new GoalsFragment();
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

        View v =  inflater.inflate(R.layout.fragment_goals,container,false);

        Button btnSetCalorie = (Button) v.findViewById(R.id.btnSetCalorie);

        btnSetCalorie.setOnClickListener(new View.OnClickListener() {
        @Override
            public void onClick(View v){


            SetCalorieFragment fragment1 = new  SetCalorieFragment();
            getFragmentManager().beginTransaction().replace(R.id.container ,fragment1).commit();
             }
        });


        Button btnSetWeight = (Button) v.findViewById(R.id.btnSetWeight);

        btnSetWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){

                SetWeightFragment fragment = new SetWeightFragment();
                getFragmentManager().beginTransaction().replace(R.id.container ,fragment).commit();
            }
        });

        return v;
    }


}