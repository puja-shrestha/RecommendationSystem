package com.example.puza.friendrecommendation.adapter;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.puza.friendrecommendation.R;
import com.example.puza.friendrecommendation.model.Interest;
import com.example.puza.friendrecommendation.model.InterestsDAO.Datum;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class InterestAdapter extends RecyclerView.Adapter<InterestAdapter.MyViewHolder> {

    private List<Interest> item;
    Activity context;
    Interest items;
    private ArrayList<String> ids;
    SharedPreferences pref; // 0 - for private mode
    SharedPreferences.Editor editor;

    public InterestAdapter(List itemList, Activity context) {
        this.item = itemList;
        this.context = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private CheckBox checkBox;

        public MyViewHolder(View view) {
            super(view);
            checkBox = (CheckBox) view.findViewById(R.id.checkbox);
        }
    }

    @Override
    public InterestAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.interest_item, parent, false);
        pref= context.getSharedPreferences("MyPref", 0);
        editor = pref.edit();
        return new InterestAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final InterestAdapter.MyViewHolder holder, final int position) {

        items = item.get(position);
        holder.checkBox.setText(items.getName());
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String id = "";
                if(isChecked){

//                    item.get(position).setChecked(true);
                    id= item.get(position).getId();
                    Set<String> set = new HashSet<String>(); //global
                    set.add(id);
                    editor.putStringSet("ids",set);
                    editor.commit();
                }//ekaichin hai
            }
        });
        if(items.getChecked()){
            holder.checkBox.setChecked(true);
        }
        else{
            holder.checkBox.setChecked(false);
        }
        holder.checkBox.setTag(position);
//
    }

    @Override
    public int getItemCount() {
        return item.size();
    }

    public List<String> getCheckBox(){
        return ids;
    }

}

