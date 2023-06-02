package com.example.mainfeddback;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
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
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Scanner extends AppCompatActivity {
    Button btn_scn;
    private EditText name;
    private  EditText phone;
    private SharedPreferences sharedPreferences;
    private RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);
        getSupportActionBar().setTitle("Scanner");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);

        //shared storage
        sharedPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        btn_scn = (Button)findViewById(R.id.btn_scn);


        btn_scn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scanCode();
            }

        });
    }
    private void scanCode()
    {
        ScanOptions options = new ScanOptions();
        options.setPrompt("Volume up to flash on");
        options.setBeepEnabled(true);
        options.setOrientationLocked(true);
        options.setCaptureActivity(CaptureAct.class);
        barLauncher.launch(options);
    }

    ActivityResultLauncher<ScanOptions> barLauncher = registerForActivityResult(new ScanContract(), result -> {
        if(result.getContents() != null){
            AlertDialog.Builder builder = new AlertDialog.Builder(Scanner.this);
            builder.setTitle("Result");
            builder.setMessage(result.getContents());
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    requestQueue = Volley.newRequestQueue(Scanner.this);
                    String url = "https://android-shivshakya.vercel.app/guestPay";
                    StringRequest request = new StringRequest(Request.Method.POST, url,
                            response -> {
                                        // Handle the response here
                                },
                                    error -> {
                                        // Handle errors here
                                        Toast.makeText(Scanner.this, "Erorr in the code", Toast.LENGTH_SHORT).show();
                                        Log.e("POST Error", error.toString());
                                    }) {
                                @Override
                                public byte[] getBody() {
                                    // Shared perference storage
                                    String Name = name.getText().toString();
                                    String Phone = phone.getText().toString();
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("guestName", Name);
                                    editor.putString("guestPhone", Phone);
                                    editor.apply();
                                    String messId = sharedPreferences.getString("name", "");
                                    String requestBody = "{\"messId\": \"" + messId + "\", \"name\": \"" + Name + "\", \"phone\": \"" + Phone + "\", \"payment\": \"" + result.getContents() + "\"}";
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
                    Toast.makeText(Scanner.this, "Successfully Payment Done", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(Scanner.this, UserProfile.class);
                    startActivity(intent);

                    dialogInterface.dismiss();
                }
            }).show();
        }
    });
}