package com.example.mainfeddback;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.VideoView;

public class FirstPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page);
        getSupportActionBar().setTitle("Mess Management System");

        VideoView videoView = findViewById(R.id.videoView4);
        Button admin = findViewById(R.id.admin);
        Button guest = findViewById(R.id.button2);
        TextView createMess = findViewById(R.id.createMess);

        Uri uri = Uri.parse("android.resource://"+getPackageName()+"/" + R.raw.aamchimess);
        videoView.setVideoURI(uri);
        videoView.start();

        float startY = videoView.getTranslationY();
        float endY = startY - 500f;

        ObjectAnimator anim = ObjectAnimator.ofFloat(videoView,"translationY",startY,endY);
        anim.setDuration(2400);
        //member.setVisibility(TextView.INVISIBLE);
        guest.setVisibility(TextView.INVISIBLE);
        admin.setVisibility(TextView.INVISIBLE);
        //\createMess.setVisibility(TextView.INVISIBLE);


        anim.addListener(new AnimatorListenerAdapter() {
            // @Override
            public void onAnimationEnd(Animator animation) {
               // member.setVisibility(TextView.VISIBLE);
                guest.setVisibility(TextView.VISIBLE);
                admin.setVisibility(TextView.VISIBLE);
               // createMess.setVisibility(TextView.VISIBLE);
            }
        });
        anim.start();
       /*
        member.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertMess alertMess = new AlertMess(MainActivity.class);
                alertMess.show(getSupportFragmentManager(), "dialog");
            }
        });
*/
        guest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertMess alertMess = new AlertMess(FirstPage.this,MainActivity.class);
                alertMess.show(getSupportFragmentManager(), "dialog");
            }
        });

        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FirstPage.this,AdminPage.class);
                startActivity(intent);
                //ExampleDialog exampleDialog = new ExampleDialog(R.layout.activity_mess_secure_id);
                //exampleDialog.show(getSupportFragmentManager(), "dialog2");
            }
        });

        createMess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FirstPage.this, createMess.class);
                startActivity(intent);
            }
        });

    }
}