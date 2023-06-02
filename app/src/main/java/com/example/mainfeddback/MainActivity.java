package com.example.mainfeddback;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.mainfeddback.databinding.ActivityMainBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private ActivityMainBinding binding2;
    private SharedPreferences sharedPreferences;
    private  TextView messName;
    private RequestQueue requestQueue;
    private TextView messDesc;
    private TextView messAdd;
    private CheckBox attendance;
    private Button guest;
    private Button member;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("HomePage");


        //shared perference
        messName = findViewById(R.id.messName);
        messDesc = findViewById(R.id.description);
        messAdd = findViewById(R.id.messAdd);
        attendance = findViewById(R.id.checkBox);
        guest = findViewById(R.id.guest);
        member = findViewById(R.id.member);


        requestQueue = Volley.newRequestQueue(MainActivity.this);
        String url = "https://mess-shivshakya.vercel.app/getMess";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                try {
                    Toast.makeText(MainActivity.this, "successfully get the response", Toast.LENGTH_SHORT).show();
                    Log.d("API", response.toString());
                    sharedPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
                    String storedName = sharedPreferences.getString("name", "");
                    for(int i = 0 ; i < response.length() ; i++){
                        JSONObject jsonObject = response.getJSONObject(i);
                        String name = jsonObject.getString("name");
                        if(storedName.equals(name)) {
                            String address = jsonObject.getString("address");
                            String description = jsonObject.getString("description");
                            messName.setText(name);
                            messDesc.setText(description);
                            messAdd.setText("Address:"+address);
                        }
                    }

                }catch(JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "error is there", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonArrayRequest);






        // feendback button
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Feedback page", Toast.LENGTH_SHORT).show();
                ExampleDialog exampleDialog = new ExampleDialog(R.layout.feedback);
                exampleDialog.show(getSupportFragmentManager(), "dialog1");
            }
        });
        //menu button
        FloatingActionButton menuButton = (FloatingActionButton) findViewById(R.id.menu);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Menu Page", Toast.LENGTH_SHORT).show();
                ExampleDialog exampleDialog = new ExampleDialog(R.layout.menu);
                exampleDialog.show(getSupportFragmentManager(), "dialog2");
            }
        });


        guest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                  Intent intent = new Intent(MainActivity.this,Scanner.class);
                  startActivity(intent);
            }
        });

        member.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,Login.class);
                startActivity(intent);
            }
        });


        ExampleDialog exampleDialog = new ExampleDialog(R.layout.menu);
        exampleDialog.show(getSupportFragmentManager(), "dialog2");
    }

}
