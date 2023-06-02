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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MemberProfile extends AppCompatActivity {
    private EditText username ;
    private EditText email;
    private EditText phone;
    private EditText password;
    private Button update;
    private Button delete;
    RequestQueue requestQueue ;
    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_profile);

        username = findViewById(R.id.upname);
        email = findViewById(R.id.upemail);
        phone = findViewById(R.id.upphone);
        password = findViewById(R.id.uppass);
        update = findViewById(R.id.update);
        delete = findViewById(R.id.del);

        sharedPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);


        //set text to update
        String userName = sharedPreferences.getString("userName","");
        username.setText(userName);

        String email_1 = sharedPreferences.getString("email","");
        email.setText(email_1);

        String phon = sharedPreferences.getString("phone","");
        phone.setText(phon);

        String pswd = sharedPreferences.getString("password","");
        password.setText(pswd);

     //update button
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                   updateMember();
            }
        });



      // delete button
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                   deleteMember();
            }
        });
    }

    private void deleteMember() {
        requestQueue = Volley.newRequestQueue(MemberProfile.this);
        String memberId = sharedPreferences.getString("memberId","");
        String url = "https://memebr-shivshakya.vercel.app/deleteMember/" + memberId;

        StringRequest request = new StringRequest(Request.Method.DELETE, url,
                response -> {
                    // Handle the response here
                    Log.d("DELETE Response", response);
                    Toast.makeText(MemberProfile.this, "Your Account Deleted Successfully", Toast.LENGTH_SHORT).show();
                },
                error -> {
                    // Handle errors here
                    Toast.makeText(MemberProfile.this, "there is a error", Toast.LENGTH_SHORT).show();
                    Log.e("DELETE Error", error.toString());
                }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                // Add any headers required for the API request
                return headers;
            }
        };

        requestQueue.add(request);
        Intent intent = new Intent(MemberProfile.this, CreateAccount.class);
        startActivity(intent);
    }


    private void updateMember() {
        requestQueue = Volley.newRequestQueue(MemberProfile.this);
        String memberId = sharedPreferences.getString("memberId", "");
        String url = "https://memebr-shivshakya.vercel.app/updateMember/" + memberId;

        StringRequest request = new StringRequest(Request.Method.PUT, url,
                response -> {
                    // Handle the response here
                    Log.d("PUT Response", response);
                    Toast.makeText(MemberProfile.this, "Your Account Updated Successfully", Toast.LENGTH_SHORT).show();
                },
                error -> {
                    // Handle errors here
                    Toast.makeText(MemberProfile.this, "There is an error", Toast.LENGTH_SHORT).show();
                    Log.e("PUT Error", error.toString());
                }) {
            @Override
            public byte[] getBody() {
                // Retrieve the updated values from EditText fields
                String updatedUsername = username.getText().toString();
                String updatedEmail = email.getText().toString();
                String updatedPhone = phone.getText().toString();
                String updatedPassword = password.getText().toString();

                // Create a JSON object with the updated data
                JSONObject requestBody = new JSONObject();
                try {
                    requestBody.put("username", updatedUsername);
                    requestBody.put("email", updatedEmail);
                    requestBody.put("phone", updatedPhone);
                    requestBody.put("password", updatedPassword);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return requestBody.toString().getBytes();
            }

            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                // Add any headers required for the API request
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };

        requestQueue.add(request);
    }
}