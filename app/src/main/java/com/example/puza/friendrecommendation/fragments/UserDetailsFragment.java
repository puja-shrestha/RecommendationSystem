package com.example.puza.friendrecommendation.fragments;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.puza.friendrecommendation.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserDetailsFragment extends Fragment {
    SharedPreferences pref;

    String name, email, mobile, address, dob, description;
    TextView _name, _email, _mobile, _address, _dob, _description;

    public UserDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_details, container, false);
        pref = getContext().getSharedPreferences("MyPref", 0);
        _name = (TextView)view.findViewById(R.id.first_name);
        _email = (TextView)view.findViewById(R.id.email);
        _mobile = (TextView)view.findViewById(R.id.mobile);
        _address = (TextView)view.findViewById(R.id.address);
        _dob = (TextView)view.findViewById(R.id.dob);
        _description = (TextView)view.findViewById(R.id.description);

        name = pref.getString("name", "");
        email = pref.getString("email", "");
        mobile = pref.getString("mobile", "");
        address = pref.getString("address", "");
        dob = pref.getString("dob", "");
        description = pref.getString("description", "");

        _name.setText(name);
        _email.setText(email);
        _mobile.setText(mobile);
        _address.setText(address);
        _dob.setText(dob);
        _description.setText(description);


        Log.v("name", name);


        return view;
    }

}
