package com.example.mainfeddback;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class AdminPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_page);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Admin Page");

        Button pay = findViewById(R.id.pay);
        Button menu = findViewById(R.id.menu);
        Button feed = findViewById(R.id.feed);
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Do something when the button is clicked
                Toast.makeText(AdminPage.this, "Admin page", Toast.LENGTH_SHORT).show();
                ExampleDialog exampleDialog = new ExampleDialog(R.layout.paymentcollect);
                exampleDialog.show(getSupportFragmentManager(), "dialog2");
            }
        });

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Do something when the button is clicked
                Toast.makeText(AdminPage.this, "Admin page", Toast.LENGTH_SHORT).show();
                ExampleDialog exampleDialog = new ExampleDialog(R.layout.adminmenu);
                exampleDialog.show(getSupportFragmentManager(), "dialog2");
            }
        });

        feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Do something when the button is clicked
                Toast.makeText(AdminPage.this, "Admin page", Toast.LENGTH_SHORT).show();
                ExampleDialog exampleDialog = new ExampleDialog(R.layout.feed);
                exampleDialog.show(getSupportFragmentManager(), "dialog2");
            }
        });


    }
}