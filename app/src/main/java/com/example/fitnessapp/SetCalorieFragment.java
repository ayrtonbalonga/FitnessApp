package com.example.fitnessapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SetCalorieFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SetCalorieFragment extends Fragment {


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SetCalorieFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SetCalorieFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SetCalorieFragment newInstance(String param1, String param2) {
        SetCalorieFragment fragment = new SetCalorieFragment();
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
        View v =  inflater.inflate(R.layout.fragment_set_calorie,container,false);


        Button  btnSetBreakfast = (Button) v.findViewById(R.id.btnSetBreakfast);

        btnSetBreakfast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){

                setBreakfastFragment fragment = new setBreakfastFragment();

                getFragmentManager().beginTransaction().replace(R.id.container ,fragment).commit();
            }
        });


        Button  btnSetLunch = (Button) v.findViewById(R.id.btnSetLunch);

        btnSetLunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){

                SetLunchFragment fragment = new SetLunchFragment();

                getFragmentManager().beginTransaction().replace(R.id.container ,fragment).commit();
            }
        });


        Button  btnSetDinner = (Button) v.findViewById(R.id.btnSetDinner);

        btnSetDinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){

                SetDinnerFragment fragment = new SetDinnerFragment();

                getFragmentManager().beginTransaction().replace(R.id.container ,fragment).commit();
            }
        });



        Button btnback = (Button) v.findViewById(R.id.btnBack);

        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){

                GoalsFragment fragment = new GoalsFragment();
                getFragmentManager().beginTransaction().replace(R.id.container ,fragment).commit();
            }
        });

        return v;
    }
}