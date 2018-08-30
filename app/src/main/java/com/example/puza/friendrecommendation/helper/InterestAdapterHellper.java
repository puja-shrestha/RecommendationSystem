package com.example.puza.friendrecommendation.helper;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/*In this helper class , the datas are stored in shared preference on network response
* on marks fillup the datas are changed from recycler adapter to the exact same json place
* at last the datas are taken and then sent to network*/


public class InterestAdapterHellper {
    private Context context;
    SharedPreferences sharedPreferences ;
    SharedPreferences.Editor editor ;
    public InterestAdapterHellper(Context context){

        sharedPreferences = context.getSharedPreferences("Interest", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        this.context = context;

    }
    public void setDatas(String interest){ //this will store json array to sharepreference
        JSONArray jsonArray = getJsonArray();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("interest",interest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        jsonArray.put(jsonObject);
        editor.putString("interests",jsonArray.toString());
        editor.commit();
    }
    public JSONArray getJsonArray(){ //to get json array stored in pref

        try {
            String s = sharedPreferences.getString("interests","");
            if(s.equals("")||s.equals(null)){
                return new JSONArray();
            }
            JSONArray jsonArray= new JSONArray(sharedPreferences.getString("interests",""));


            return jsonArray;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  null;
    }
    public void clearDatas(){
        editor.clear().commit();

    }



}
