package com.example.puza.friendrecommendation.adapter;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import static android.content.Context.MODE_PRIVATE;

public class InterestHelperClass {
    private Context context;
    private SharedPreferences.Editor editor;

    public InterestHelperClass(Context  context) {
        this.context =context;

    }
    public void storeInterest(String interest){
        editor = context.getSharedPreferences("interest", MODE_PRIVATE).edit();
        JsonObject jsonObject = new JsonObject();
        JsonArray jsonArray = new JsonArray();
        jsonArray.add(new JsonObject("interest",interest));
        jsonObject.add("interests",jsonArray);
    }
}
