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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class createMess extends AppCompatActivity {
    private EditText name;
    private EditText address;
    private EditText desc;
    private Button btn;
    private RequestQueue requestQueue;
    private  SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_mess);
        getSupportActionBar().setTitle("Create Your Mess");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        name = findViewById(R.id.name);
        address = findViewById(R.id.address);
        desc = findViewById(R.id.desc);
        btn = findViewById(R.id.btn);

        sharedPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        requestQueue = Volley.newRequestQueue(this);
        String url = "https://mess-shivshakya.vercel.app/addMess";
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String messName = name.getText().toString();
                String messAdd = address.getText().toString();
                String messDesc = desc.getText().toString();

                StringRequest request = new StringRequest(Request.Method.POST, url,
                        response -> {
                            // Handle the response here

                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                String insertedId = jsonObject.optString("inserted_id");
                                // shared perferance
                                editor.putString("id", insertedId);
                                editor.apply();
                            }catch(JSONException e){
                                e.printStackTrace();
                            }
                            Log.d("POST Response", response);
                        },
                        error -> {
                            // Handle errors here
                            Toast.makeText(createMess.this, "Erorr in the code", Toast.LENGTH_SHORT).show();
                            Log.e("POST Error", error.toString());
                        }) {
                    @Override
                    public byte[] getBody() {
                        String requestBody = "{\"name\": \"" + messName + "\", \"address\": \"" + messAdd + "\", \"description\": \"" + messDesc + "\"}";

                        Intent intent = new Intent(createMess.this, FirstPage.class);
                        startActivity(intent);
                        return requestBody.getBytes();
                    }
                    @Override
                    public Map<String, String> getHeaders() {
                        Map<String, String> headers = new HashMap<>();
                        headers.put("Content-Type", "application/json");
                        return headers;
                    }
                };
                requestQueue.add(request);
                Toast.makeText(createMess.this, " Your mess has been created !", Toast.LENGTH_SHORT).show();
            }
        });

    }
}