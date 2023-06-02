package com.example.mainfeddback;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
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

import java.util.Random;

public class UserProfile extends AppCompatActivity {
    private TextView name;
    private  TextView phone;
    private HorizontalScrollView scroll;
    private RequestQueue requestQueue;
    private LinearLayout linearLayout;
    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);
        scroll = findViewById(R.id.scrollView3);
        linearLayout = findViewById(R.id.linear);

        sharedPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);

        requestQueue = Volley.newRequestQueue(UserProfile.this);
        String url = "https://android-shivshakya.vercel.app/guestDetails";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                try {
                    Toast.makeText(UserProfile.this, "successfully get the response", Toast.LENGTH_SHORT).show();
                    Log.d("API", response.toString());
                    Random random = new Random();
                    for(int i = 0 ; i < response.length() ; i++){
                        JSONObject jsonObject = response.getJSONObject(i);
                        String id  = jsonObject.getString("messId");
                        String name = jsonObject.getString("name");
                        String phone = jsonObject.getString("phone");
                        String payment = jsonObject.getString("payment");
                        String time = jsonObject.getString("time");
                        //shared data
                        String messId = sharedPreferences.getString("name", "");
                        String guestName = sharedPreferences.getString("guestName","");
                        String guestPhone = sharedPreferences.getString("guestPhone","");

                        if (messId.equals(id) && guestName.equals(name) && guestPhone.equals(phone) ) {
                            TextView nameTextView = new TextView(UserProfile.this);
                            nameTextView.setText("Name: " + name + "\n" + "Phone: " + phone + "\n" + "Payment:" + payment + "\n" + "Time: " + time + "\n");
                            nameTextView.setTextSize(18);
                            nameTextView.setTextColor(0xFFFFFFFF);

                            //for random colors
                            int red = random.nextInt(256);
                            int green = random.nextInt(256);
                            int blue = random.nextInt(256);
                            int color = Color.rgb(red, green, blue);
                            nameTextView.setBackgroundColor(color);

                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.MATCH_PARENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT
                            );
                            params.setMargins(20, 50, 20, 0);
                            nameTextView.setLayoutParams(params);
                            nameTextView.setWidth(600);
                            nameTextView.setHeight(400);
                            nameTextView.setPadding(20,20,40,0);
                            linearLayout.addView(nameTextView);
                        }
                    }
                }catch(JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(UserProfile.this, "error is there", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonArrayRequest);




    }
}