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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.example.fitnessapp.Breakfast.getBreakfastCalorie;
import static com.example.fitnessapp.Breakfast.getBreakfastdescription;
import static com.example.fitnessapp.Breakfast.setBreakfastCalorie;
import static com.example.fitnessapp.Breakfast.setBreakfastdescription;
import static com.example.fitnessapp.UserInfo.getUsername;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SetWeightFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SetWeightFragment extends Fragment {
    boolean isWeightAvail= false;
    TextView txtdWeight;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SetWeightFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SetWeightFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SetWeightFragment newInstance(String param1, String param2) {
        SetWeightFragment fragment = new SetWeightFragment();
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
        View v =  inflater.inflate(R.layout.fragment_set_weight,container,false);
    txtdWeight=(TextView) v.findViewById(R.id.txtdWeight);

    displayWeight();

Button btnSetWeight =(Button)v.findViewById(R.id.btnwSetWeight);

btnSetWeight.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        if (isWeightAvail){
            Weight.setdWeight(Double.parseDouble(txtdWeight.getText().toString()));
            showCustomeDialog();
            displayWeight();
        }else{
            Weight.setdWeight(Double.parseDouble(txtdWeight.getText().toString()));
            writeWeight();
            displayWeight();

        }
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


    private void writeWeight() {

        DatabaseReference reff = FirebaseDatabase.getInstance().
                getReference("/Weight");

        Weight.setdWeight(Double.parseDouble(txtdWeight.getText().toString()));

        //Writing Hashmap
        Map<String, Object> mHashmap = new HashMap<>();

        mHashmap.put("/"+getUsername()+"/"+getCurrentDate()+"/CurrentWeight", Weight.getdWeight());
        reff.updateChildren(mHashmap);

        Toast.makeText(getContext(), "Data has been saved", Toast.LENGTH_LONG).show();



    }


    private void displayWeight() {

        DatabaseReference reff = FirebaseDatabase.getInstance().getReference("/Weight/" + getUsername() + "/" + getCurrentDate());

        reff.orderByChild(getCurrentDate()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                if (snapshot.exists()) {

                    if((snapshot.getKey().equals(getCurrentDate()))){
                        isWeightAvail = true;
                        double w = snapshot.child("CurrentWeight").getValue(double.class);

                      Weight.setdWeight(w);

                        txtdWeight.setText(String.valueOf(Weight.getdWeight()));

                    }


                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }


        });
    }
    private void showCustomeDialog() {
        // weight = Double.parseDouble(txtdWeight.getText().toString());
        //Weight.setdWeight(weight);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        builder.setMessage("You have already inserted your weight, Do you want to update them");
        builder.setCancelable(true);
        builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                writeWeight();
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
    public String getCurrentDate() {
        String date;

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        date = simpleDateFormat.format(new Date());

        return date;
    }

}