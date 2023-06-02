package com.example.mainfeddback;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity {
    private TextView text;
    private EditText username;
    private EditText password;
    private RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Login Page");

        Button logBtn = findViewById(R.id.login);
        TextView reg = findViewById(R.id.register);


        username = findViewById(R.id.logUser);
        password = findViewById(R.id.logPass);

        requestQueue = Volley.newRequestQueue(this);

        // login to account
        logBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                   loginFunction();
            }
        });

        //for registration purpose
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this,CreateAccount.class);
                startActivity(intent);
            }
        });
    }

    private void loginFunction() {
        String url = "https://memebr-shivshakya.vercel.app/getMember";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                try {
                    Toast.makeText(Login.this, "successfully get the response", Toast.LENGTH_SHORT).show();
                    Log.d("API", response.toString());
                    String user = username.getText().toString();
                    String pass = password.getText().toString();

                    for(int i = 0 ; i < response.length() ; i++){
                        JSONObject jsonObject = response.getJSONObject(i);
                        String userName = jsonObject.getString("username");
                        String password = jsonObject.getString("password");
                        if (user.equals(userName) && pass.equals(password)){
                            Toast.makeText(Login.this, "You have successfully logged in", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Login.this, MemberProfile.class);
                            startActivity(intent);
                        }
                    }

                }catch(JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Login.this, "error is there", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

}