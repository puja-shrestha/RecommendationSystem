package com.example.puza.friendrecommendation;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {


    private EditText password;
    private EditText email;
    private Button buttonLogin, buttonFacebook, buttonGoogle;

    private ImageView logo;
    private TextView appName, account, or;

    private TextView textViewRegister;

    private RequestQueue queue;
    private String urls = "http://192.168.1.76/puja/public/index.php/api/login";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        queue = Volley.newRequestQueue(this);
        password = (EditText)findViewById(R.id.password);
        email = (EditText)findViewById(R.id.email);
//        logo = (ImageView)findViewById(R.id.logo);
        appName = (TextView)findViewById(R.id.appName);

        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
//        or = (TextView)findViewById(R.id.or);
//        buttonFacebook = (Button)findViewById(R.id.buttonFacebook);
//        buttonGoogle = (Button)findViewById(R.id.buttonGoogle);

        buttonLogin = (Button) findViewById(R.id.buttonLogin);
        buttonLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                getLoginData(urls);
            }
        } );

        textViewRegister = (TextView) findViewById(R.id.textViewRegister);
        textViewRegister.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        } );
    }

    private void getLoginData(String url){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject reader = new JSONObject(response);
                    Boolean status = reader.getBoolean("status");
                    String message = reader.getString("message");
                 if (status){
                     Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                     Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                     startActivity(intent);
                     JSONObject data = reader.getJSONObject("data");
                     String id = data.getString("id");
                     String name = data.getString("first_name");
                     String last_name = data.getString("last_name");

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
                params.put("username", email.getText().toString());
                params.put("password", password.getText().toString());

                return params;

            }
        };
        queue.add(stringRequest);
    }
}
