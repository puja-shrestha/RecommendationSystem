package com.example.puza.friendrecommendation;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;

import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.puza.friendrecommendation.Interface.ApiInterface;
import com.example.puza.friendrecommendation.Manager.ApiClient;
import com.example.puza.friendrecommendation.adapter.HomeAdapter;
import com.example.puza.friendrecommendation.adapter.InterestAdapter;
import com.example.puza.friendrecommendation.model.Interest;
import com.example.puza.friendrecommendation.model.InterestsDAO.Datum;
import com.example.puza.friendrecommendation.model.InterestsDAO.Interests;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;


public class RegisterActivity extends AppCompatActivity {

    private EditText first_name, last_name, email, password, confirm_password, mobile
            , address, description;

    private TextView dob,interests;
    private CheckBox cJava, cAndroid, cPhp, cPython, cJavaScript, cRuby;

    private Button registerBtn;
    /*---------------featured items----------------------*/
    RecyclerView checkRecycler;
    private RecyclerView.LayoutManager cLayoutManager;
    InterestAdapter checkAdapter;
    List<Interest> checkItems;
    String device_ime;
    List<String> checkbox;
    /*---------------------------------------------------*/

    private TextView selectDate;
    Context context;
    private String urls = "http://192.168.1.76/puja/public/index.php/api/signup";
    private String checkUrls = "http://192.168.1.76/puja/public/index.php/api/interests";
    private RequestQueue queue;
    SharedPreferences prefs;
    Set<String> ids;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);

        device_ime = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        Log.v("device_ime", device_ime);
        prefs = getSharedPreferences("MyPref", 0);
        ids = prefs.getStringSet("ids", null);
        if (ids!=null){
          Log.v("ids", ids.toString());

        }

        getCheckBoxItems(checkUrls);
        queue = Volley.newRequestQueue(this);
        first_name = (EditText)findViewById(R.id.first_name);
        last_name = (EditText)findViewById(R.id.last_name);
        email = (EditText)findViewById(R.id.email);
        password = (EditText)findViewById(R.id.password);
        mobile = (EditText)findViewById(R.id.mobile);
        address = (EditText)findViewById(R.id.address);
        dob = (TextView) findViewById(R.id.dob);
        description = (EditText) findViewById(R.id.description);

        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);


        selectDate = findViewById(R.id.dob);
        // final TextView date = view.findViewById(R.id.tvSelectedDate);


        /*------------------Check Items--------------------------*/
        checkRecycler = (RecyclerView) findViewById(R.id.checkRecycler);

        checkItems = new ArrayList<Interest>();

        checkRecycler.setHasFixedSize(true);

        cLayoutManager = new LinearLayoutManager(
                RegisterActivity.this,
                LinearLayoutManager.VERTICAL,
                false
        );
        checkRecycler.setLayoutManager(cLayoutManager);
        checkAdapter = new InterestAdapter(checkItems, RegisterActivity.this);
        checkRecycler.setAdapter(checkAdapter);

        /*------------------------------------------------------------*/


        selectDate.setOnClickListener(new View.OnClickListener() {

            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

            @Override
            public void onClick(View view) {

                DatePickerDialog datePickerDialog = new DatePickerDialog(RegisterActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                                selectDate.setText(day + "/" + month + "/" + year);

                            }
                        }, year, month, dayOfMonth);

                datePickerDialog.show();
            }
        });


        registerBtn = (Button) findViewById(R.id.registerBtn);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              postUserData(urls);
            }
        });

    }

   private void postUserData(String url){
        checkbox = checkAdapter.getCheckBox();
        Log.v("checkbox", checkbox.size()+"");
       StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>(){
           @Override
           public void onResponse(String response) {
               Log.v("responseRegsister", response);
               try {
                   JSONObject reader = new JSONObject(response);
                   Boolean status = reader.getBoolean("status");
                   Log.v("status", status.toString());
                   String message = reader.getString("message");
                   if (status){
                       Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                       Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_SHORT).show();
                       first_name.setText("");
                       last_name.setText("");
                       email.setText("");
                       password.setText("");
                       mobile.setText("");
                       address.setText("");
                       dob.setText("");
                       description.setText("");

                       startActivity(intent);
                   }else{
                       Toast.makeText(RegisterActivity.this, "Error Occured!!", Toast.LENGTH_SHORT).show();
                   }
               } catch (JSONException e) {
                   e.printStackTrace();
               }

           }
       }, new com.android.volley.Response.ErrorListener() {
           @Override
           public void onErrorResponse(VolleyError error) {

           }
       }) {
           @Override
           protected Map<String, String> getParams() throws AuthFailureError {
               Map<String, String> params = new HashMap<String, String>();
               params.put("first_name", first_name.getText().toString().trim());
               params.put("last_name", last_name.getText().toString().trim());
               params.put("email", email.getText().toString().trim());
               params.put("password", password.getText().toString().trim());
               params.put("mobile", mobile.getText().toString().trim());
               //I then convert it to an Array List and try to see if I got the values
               List<String> list = new ArrayList<String>(ids);
               for(int i = 0 ; i < list.size() ; i++){
                   Log.d("fetching values", "fetch value " + list.get(i));
                   params.put("interests", list.get(i));
               }

               params.put("address", address.getText().toString().trim());
               params.put("dob", dob.getText().toString().trim());
               params.put("description", description.getText().toString().trim());
               params.put("device_imei", device_ime);

               return params;

           }
       };
            queue.add(stringRequest);
    }


    public void getCheckBoxItems(String url){
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
                    for(int i=0; i < data.length(); i++){
                        JSONObject list = data.getJSONObject(i);
                        String id = list.getString("id");
                        String name = list.getString("name");
                        Log.v("name", name);
                    checkItems.add(new Interest(id, name));
//
                    }
                    checkAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(context, "Error Occured", Toast.LENGTH_SHORT).show();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(context, "Error Occured", Toast.LENGTH_SHORT).show();

            }
        });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue queuee =Volley.newRequestQueue(this);
        queuee.add(stringRequest);
    }
}
