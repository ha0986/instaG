package com.hanif.talkingTom;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class profile extends AppCompatActivity{

    public static TextView points;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ImageButton edit = findViewById(R.id.edit);
        ImageButton task = findViewById(R.id.task);
        ImageButton reward = findViewById(R.id.reward);
        ImageButton mores = findViewById(R.id.more);
        ImageButton exit = findViewById(R.id.exit);
        points = findViewById(R.id.points);
        TextView username = findViewById(R.id.userName);
        Button jokesBtn = findViewById(R.id.jokesbtn);


        jokesBtn.setOnClickListener(v -> startActivity(new Intent(profile.this, jokes.class)));
        reward.setOnClickListener(v -> autoLoad.loadReward(this,profile.this, "ca-app-pub-9422110628550448/8994806504", "profile"));
        mores.setOnClickListener(v -> startActivity(new Intent(profile.this, more.class)));
        exit.setOnClickListener(v -> exit());

        username.setText(autoLoad.userName);
        points.setText(autoLoad.points);




        edit.setOnClickListener(v -> {
            Intent i = new Intent(profile.this, login.class);
            i.putExtra("change","true");
            startActivity(i);
        });
    }




    public void exit(){
        new AlertDialog.Builder(profile.this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Instagram Follower")
                .setMessage("Are you sure you want to Exit?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    moveTaskToBack(true);
                    finish();
                })
                .setNegativeButton("No", null)
                .show();


    }

    public void onBackPressed() {
        exit();
    }


    public static void update(){
        points.setText(autoLoad.points);
    }
}