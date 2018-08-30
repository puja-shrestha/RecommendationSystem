package com.example.puza.friendrecommendation.fragments;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.puza.friendrecommendation.R;
import com.example.puza.friendrecommendation.adapter.HomeAdapter;
import com.example.puza.friendrecommendation.model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    /*---------------featured items----------------------*/
    RecyclerView userRecyclerView;
    private RecyclerView.LayoutManager uLayoutManager;
    HomeAdapter homeAdapter;
    List<User> userItems;
    SharedPreferences pref;
    private String url = "http://192.168.1.76/puja/public/index.php/api/users";
    /*---------------------------------------------------*/


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        pref = getContext().getSharedPreferences("MyPref", 0);
        String device_ime = pref.getString("device_ime", "");
        getUserData(url+"?device_imei=aaff");

        /*------------------User Items--------------------------*/
        userRecyclerView = (RecyclerView) view.findViewById(R.id.featuredRecycler);

//        userItems = getUserItems();

        userRecyclerView.setHasFixedSize(true);

        uLayoutManager = new LinearLayoutManager(
                getContext(),
                LinearLayoutManager.VERTICAL,
                false
        );
        userItems = new ArrayList<User>();
        userRecyclerView.setLayoutManager(uLayoutManager);
        homeAdapter = new HomeAdapter(getActivity(),userItems);
//        homeAdapter = new HomeAdapter(getActivity(), userItems);
        userRecyclerView.setAdapter(homeAdapter);
        homeAdapter.notifyDataSetChanged();

        /*------------------------------------------------------------*/

        return view;
    }


    public void getUserData(String url){

        Log.v("url", url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.v("response", response);

                try {
                    JSONObject reader = new JSONObject(response);
                    Boolean status = reader.getBoolean("status");
                    String message = reader.getString("message");
                    JSONArray data = reader.getJSONArray("data");
                    String id, first_name, last_name, email, mobile, address, dob, interests, description;
                    for(int i=0; i < data.length(); i++){
                        JSONObject list = data.getJSONObject(i);
                        id = list.getString("id");
                        first_name = list.getString("first_name");
                        last_name = list.getString("last_name");
                        email = list.getString("email");
                        mobile = list.getString("mobile");
                        address = list.getString("address");
                        dob = list.getString("dob");
                        interests = list.getString("interests");
                        description = list.getString("description");

                        userItems.add(new User(id, first_name, last_name, email,  mobile, address,dob, interests, description));
                    }
                    homeAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "Error Occured", Toast.LENGTH_SHORT).show();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getContext(), "Error Occured", Toast.LENGTH_SHORT).show();

            }
        });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue queuee = Volley.newRequestQueue(getActivity());
        queuee.add(stringRequest);
    }
}
