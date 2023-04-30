package com.hanif.likeefollow;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_Tikfollow);
        setContentView(R.layout.activity_splash);

        move();

    }

    public void move(){
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        String username = pref.getString("name", "@hanif");
        autoLoad.followed = pref.getString("done", "@hanif, @jakir");
        if (Objects.equals(username, "@hanif")){
            Intent myIntent = new Intent(splash.this, login.class);
            startActivity(myIntent);
            finish();
        }else {
            autoLoad.loadAdd(this);
            autoLoad.userName = username;
            autoLoad.getdata();
            Intent myIntent = new Intent(splash.this, doTask.class);
            startActivity(myIntent);
            finish();
        }
    }

}