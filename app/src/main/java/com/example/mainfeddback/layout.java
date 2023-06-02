package com.example.mainfeddback;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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

import java.util.ArrayList;
import java.util.List;

public class layout extends AppCompatActivity {

    private ListView listView;
    private RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout);
        listView = findViewById(R.id.dialog_list);
        List<String> namesList = new ArrayList<>();

        requestQueue = Volley.newRequestQueue(this);
        String url = "https://mess-shivshakya.vercel.app/getMess";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                try {
                    Toast.makeText(layout.this, "successfully get the response", Toast.LENGTH_SHORT).show();
                    Log.d("API", response.toString());

                    for(int i = 0 ; i < response.length() ; i++){
                        JSONObject jsonObject = response.getJSONObject(i);
                        //String id  = jsonObject.getString("id");
                        String name = jsonObject.getString("name");
                        namesList.add(name);
                    }
                }catch(JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(layout.this, "error is there", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonArrayRequest);
        // id.setText(user_id);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, namesList);
        listView.setAdapter(adapter);

    }
}