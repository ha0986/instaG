package com.hanif.likeefollow;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class profile extends AppCompatActivity implements View.OnClickListener {

    public Intent myIntent;
    public TextView points;

    public static int dollar;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Button rate = findViewById(R.id.rate);
        Button edit = findViewById(R.id.edit);
        Button task = findViewById(R.id.task);
        Button reward = findViewById(R.id.reward);
        Button more = findViewById(R.id.more);
        Button exit = findViewById(R.id.exit);
        points = findViewById(R.id.points);
        TextView username = findViewById(R.id.userName);
        Button jokesBtn = findViewById(R.id.jokesbtn);


        rate.setOnClickListener(this);
        edit.setOnClickListener(this);
        task.setOnClickListener(this);
        reward.setOnClickListener(this);
        more.setOnClickListener(this);
        exit.setOnClickListener(this);

        username.setText(autoLoad.userName);
        points.setText(autoLoad.points);


        jokesBtn.setOnClickListener(v -> {
            myIntent = new Intent(profile.this, jokes.class);
            startActivity(myIntent);
        });

    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.rate:
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id="+ getPackageName()));
                startActivity(browserIntent);
                break;
            case R.id.edit:
                Intent i = new Intent(profile.this, login.class);
                i.putExtra("change","true");
                startActivity(i);
                break;
            case R.id.task:
                myIntent = new Intent(profile.this, doTask.class);
                startActivity(myIntent);
                break;
            case R.id.reward:
                autoLoad.loadReward(this,profile.this, "ca-app-pub-9422110628550448/5693330404", "profile");
                break;
            case R.id.exit:
                exit();
                break;
            case R.id.more:
                myIntent = new Intent(profile.this, more.class);
                startActivity(myIntent);
                break;
        }

    }


    public void exit(){
        new AlertDialog.Builder(profile.this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Likee Likes")
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

    public static int getDollar() {
        return dollar;

    }
}