package com.example.puza.friendrecommendation.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.puza.friendrecommendation.R;
import com.example.puza.friendrecommendation.adapter.InterestAdapter;

public class UserFragment extends Fragment {

    RecyclerView interestsRecyclerView;
    InterestAdapter interestsAdapter;
    String interestsItems;

    Context mContext;

    public UserFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user, container, false);


        return view;
    }

//

}
