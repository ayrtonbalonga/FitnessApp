package com.example.fitnessapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.example.fitnessapp.Breakfast.getBreakfastCalorie;
import static com.example.fitnessapp.Breakfast.getBreakfastdescription;
import static com.example.fitnessapp.Breakfast.setBreakfastCalorie;
import static com.example.fitnessapp.Breakfast.setBreakfastdescription;
import static com.example.fitnessapp.UserInfo.getIsCm;
import static com.example.fitnessapp.UserInfo.getIsKg;
import static com.example.fitnessapp.UserInfo.getUsername;
import static com.example.fitnessapp.UserInfo.setIsCm;
import static com.example.fitnessapp.UserInfo.setIsKg;
import static com.example.fitnessapp.UserInfo.setUsername;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AccountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccountFragment extends Fragment {
    Switch sHeight;
    Switch sWeight;
    boolean isProfileAvai;
    Spinner spSex;
    TextView txtDOB;
    TextView txtHeight;
    TextView txtWeight;
    TextView tvheight;
    TextView tvweight;
    boolean isKg;
    boolean isCm;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AccountFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AccountFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AccountFragment newInstance(String param1, String param2) {
        AccountFragment fragment = new AccountFragment();
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
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        txtWeight = (TextView) view.findViewById(R.id.txtWeight);
        txtHeight = (TextView) view.findViewById(R.id.txtHeight);
        txtDOB = (TextView) view.findViewById(R.id.txtDate);
        tvheight = (TextView) view.findViewById(R.id.tvHeight);
        tvweight = (TextView) view.findViewById(R.id.tvWeight);

        displayData();
        sHeight = (Switch) view.findViewById(R.id.sHeight);
        sHeight.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                //   if (isProfileAvai) {

                if (isChecked == true) {

                    String txth = txtHeight.getText().toString();
                    if (!txth.equals("")) {
                        tvheight.setText("Cm");
                        Double h = Double.parseDouble(txtHeight.getText().toString());
                        txtHeight.setText(new DecimalFormat("##.##").format(convHeightCm(h)));
                        Profile.setHeight(Double.parseDouble(txtHeight.getText().toString()));
                        setIsCm(true);
                    }

                } else {
                    String txth = txtHeight.getText().toString();
                    if (!txth.equals("")) {
                        tvheight.setText("Inch");
                        double height = Double.parseDouble(txtHeight.getText().toString());
                        double convHeight = convHeightInCh(height);
                        txtHeight.setText(new DecimalFormat("##.##").format(convHeight));
                        Profile.setHeight(convHeight);
                        setIsCm(false);
                    }
                }
            }
        });
        sWeight = (Switch) view.findViewById(R.id.sWeight);
        sWeight.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                // if (isProfileAvai) {

                if (isChecked == true) {
                    String txtw = txtWeight.getText().toString();
                    if (!txtw.equals("")) {

                        tvweight.setText("Kg");
                        double intWeight = Double.parseDouble(txtWeight.getText().toString());
                        txtWeight.setText(((convWeightKg(intWeight))));
                        Profile.setWeight(Double.parseDouble(txtWeight.getText().toString()));
                        setIsKg(true);
                    }
                }else {
                    String txtw = txtWeight.getText().toString();
                    if(!txtw.equals("")) {

                        tvweight.setText("lbs");
                        double intWeight = Double.parseDouble(txtWeight.getText().toString());
                        double convWeight = convWeightPound(intWeight);
                        txtWeight.setText(new DecimalFormat("##.##").format(convWeight));
                        Profile.setWeight(Double.parseDouble(txtWeight.getText().toString()));
                        setIsKg(false);
                    }

                }
            }
        });

        Button btnSavA = (Button) view.findViewById(R.id.btnSaveA);
        btnSavA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String dob = txtDOB.getText().toString();
                double height = Double.parseDouble(txtHeight.getText().toString());
                String sex = spSex.getSelectedItem().toString();
                double weight = Double.parseDouble(txtWeight.getText().toString());

               // String dob_var=(tx.getText().toString());
                DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd"); // Make sure user insert date into edittext in this format.
                String date = new String();
                Date dateObject;
                try {
                    dateObject = formatter.parse(dob);
                     date = new SimpleDateFormat("dd/MM/yyyy").format(dateObject);
                } catch (ParseException e) {
                    e.printStackTrace();
                    Log.i("E11111111111", e.toString());
                }



                Profile.setDOB(date);
                Profile.setHeight(height);
                Profile.setSex(sex);
                Profile.setWeight(weight);

                if(isProfileAvai) {
                    showCustomeDialog();
                }else{
                    writeProfile();
                    Toast.makeText(getContext(), "Data has been saved", Toast.LENGTH_LONG).show();
                }

            }
        });

        spSex = (Spinner) view.findViewById(R.id.spSex);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getActivity(),
                R.array.Sex_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spSex.setAdapter(adapter);

        return view;
    }

    public void writeProfile() {
        DatabaseReference reff = FirebaseDatabase.getInstance().
                getReference("/Profile");
        //Writing Hashmap
        Map<String, Object> mHashmap = new HashMap<>();
        mHashmap.put("/" + getUsername() + "/Height", Profile.getDOB());
        mHashmap.put("/" + getUsername() + "/Height", Profile.getHeight());
        mHashmap.put("/" + getUsername() + "/Weight", Profile.getWeight());
        mHashmap.put("/" + getUsername() + "/Sex", Profile.getSex());
        mHashmap.put("/" + getUsername() + "/isKg", getIsKg());
        mHashmap.put("/" + getUsername() + "/isCm", getIsCm());
        reff.updateChildren(mHashmap);
    }


    private void displayData() {

        DatabaseReference reff = FirebaseDatabase.getInstance().getReference("/Profile/" + getUsername());

        reff.orderByChild(getUsername()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                if (snapshot.exists()) {

                    if ((snapshot.getKey().equals(getUsername()))) {
                        isProfileAvai = true;
                        String dob = snapshot.child("DOB").getValue(String.class);
                        double height = snapshot.child("Height").getValue(double.class);
                        double weight = snapshot.child("Weight").getValue(double.class);
                        boolean kg =   snapshot.child("isKg").getValue(boolean.class);
                        boolean cm =  snapshot.child("isCm").getValue(boolean.class);
                        String sex = snapshot.child("Sex").getValue(String.class);

                        Profile.setDOB(dob);
                        Profile.setHeight(height);
                        Profile.setWeight(weight);
                        Profile.setSex(sex);
                        UserInfo.setIsKg(kg);
                        UserInfo.setIsCm(cm);

                        // sHeight.
                        //  Switch sWeight;
                        if (Profile.getSex() == "Male") {

                            spSex.setSelection(0);
                        } else {
                            spSex.setSelection(1);
                        }


                        if (UserInfo.getIsCm() == true) {
                            sHeight.setChecked(true);
                            tvheight.setText("Cm");
                            //  int
                            txtHeight.setText(Integer.toString((int) Profile.getHeight()));

                        } else {
                            sHeight.setChecked(false);
                            tvheight.setText("Inch");
                            // Double intheight = Double.parseDouble(Profile.getHeight());
                            // double convHeight = convHeightInCh(intheight);
                            //int h =(double) Profile.getHeight();
                            txtHeight.setText(Integer.toString((int) Math.round(Profile.getHeight())));

                        }

                        if (UserInfo.getIsKg() == true) {
                            sWeight.setChecked(true);
                            tvweight.setText("Kg");

                            txtWeight.setText(Integer.toString((int) Profile.getWeight()));
                        } else {
                            sWeight.setChecked(false);
                            tvweight.setText("lbs");
                            // int intweight = Integer.parseInt(Profile.getWeight());
                            //double convWeight = convWeightPound(intweight);
                            txtWeight.setText(Integer.toString((int) Math.round(Profile.getWeight())));

                        }
                        txtDOB.setText(Profile.getDOB());

                    } else {
                        isProfileAvai = false;
                    }


                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }


        });
    }

    public double convHeightInCh(double currHeight) {

        Double convHeight = (currHeight / 2.54);

        //  String convHeight = Double.toString(height);
        return convHeight;
    }

    public String convWeightKg(double currWeight) {

        Double conv = (currWeight / 2.20);
        String w = new DecimalFormat("##.##").format(conv);
        return w;
    }

    public double convHeightCm(double currHeight) {

        double conv = (currHeight * 2.54);

      //  String h = new DecimalFormat("##.##").format(conv);
        return conv;
    }

    public double convWeightPound(double currWeight) {

        Double convWeight = (currWeight * 2.20);

        // St convWeight = Double.toString(weight);
        return convWeight;
    }

    private Boolean isWeightEmpty(String w) {

        String val = txtWeight.getText().toString();

        if (val.isEmpty()) {
            txtWeight.setError("Field cannot be empty");
            return true;
        } else {
            txtWeight.setError(null);

            return false;
        }

    }
    private Boolean isHeightEmpty(String h) {

        String val = txtHeight.getText().toString();

        if (val.isEmpty()) {
            txtHeight.setError("Field cannot be empty");
            return true;
        } else {
            txtHeight.setError(null);

            return false;
        }

    }


    private void showCustomeDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        builder.setMessage("You have already inserted you breakfast details, Do you want to update them");
        builder.setCancelable(true);
        builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                writeProfile();
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


}
