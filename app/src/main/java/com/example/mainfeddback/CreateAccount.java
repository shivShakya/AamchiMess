package com.example.mainfeddback;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreateAccount extends AppCompatActivity {
    EditText username ;
    EditText email ;
    EditText phone ;
    EditText password ;
    private RequestQueue requestQueue;
    private SharedPreferences sharedPreferences;
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Create Account");

        btn = findViewById(R.id.sub);
        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        phone  = findViewById(R.id.phone);
        password = findViewById(R.id.pass);
        sharedPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 scanCode();
            }
        });

    }

    private void scanCode() {
        ScanOptions options = new ScanOptions();
        options.setPrompt("Volume up to flash on");
        options.setBeepEnabled(true);
        options.setOrientationLocked(true);
        options.setCaptureActivity(CaptureAct.class);
        barLauncher.launch(options);
    }

    ActivityResultLauncher<ScanOptions> barLauncher = registerForActivityResult(new ScanContract(), result -> {
        if(result.getContents() != null){
            AlertDialog.Builder builder = new AlertDialog.Builder(CreateAccount.this);
            builder.setTitle("Result");
            builder.setMessage(result.getContents());
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    requestQueue = Volley.newRequestQueue(CreateAccount.this);
                    String url = "https://memebr-shivshakya.vercel.app/createMember";
                    StringRequest request = new StringRequest(Request.Method.POST, url,
                            response -> {

                                try {
                                    JSONObject jsonResponse = new JSONObject(response);
                                    String memberId = jsonResponse.optString("id");

                                    // Store the member ID in shared preferences
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("memberId", memberId);
                                    editor.apply();

                                    Log.d("POST Response", response);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                Log.d("P", response);
                            },
                            error -> {
                                // Handle errors here
                                Toast.makeText(CreateAccount.this, "Erorr in the code", Toast.LENGTH_SHORT).show();
                                Log.e("POST Error", error.toString());
                            }) {
                        @Override
                        public byte[] getBody() {
                            // Shared perference storage
                            String uname = username.getText().toString();
                            String eml = email.getText().toString();
                            String phn = phone.getText().toString();
                            String pass = password.getText().toString();
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("userName", uname);
                            editor.putString("email", eml);
                            editor.putString("phone", phn);
                            editor.putString("password",pass);
                            editor.apply();
                            String messId = sharedPreferences.getString("name", "");
                            String requestBody = "{\"messId\": \"" + messId + "\", \"username\": \"" + uname + "\", \"password\": \"" + pass + "\", \"email\": \"" + eml + "\", \"phone\": \"" + phn + "\", \"payment\": \"" + result.getContents() + "\"}";
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
                    Toast.makeText(CreateAccount.this, "Successfully Payment Done", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(CreateAccount.this, MemberProfile.class);
                    startActivity(intent);

                    dialogInterface.dismiss();
                }
            }).show();
        }
    });

}
