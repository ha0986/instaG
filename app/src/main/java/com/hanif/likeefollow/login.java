package com.hanif.likeefollow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.airbnb.lottie.LottieAnimationView;

import java.util.Objects;

public class login extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button done = findViewById(R.id.done);
        Button gues = findViewById(R.id.gp);
        EditText text = findViewById(R.id.editTextTextPersonName3);
        String check = getIntent().getStringExtra("change");




        done.setOnClickListener(v -> {
            String inputs = text.getText().toString();
            if(inputs.isEmpty()){
                autoLoad.alart(login.this, "Please enter your Url");
            }else{
                if (Objects.equals(check, "true")) {
                    autoLoad.removedata(autoLoad.userName);
                }
                if (inputs.startsWith("https://l.likee")){
                    String[] spli = inputs.split("https://l.likee.video/");
                    autoLoad.savedata(spli[1].replace("/","*"));
                    save(spli[1].replace("/","*"));
                }else {
                    autoLoad.alart(login.this, "Url not correct");
                }

            }

        });


        gues.setOnClickListener(v -> save("@hanif"));
    }


    public void save(String userName){
        autoLoad.userName = userName;
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("name", userName);
        editor.apply();
        changeScene();
    }

    public void changeScene(){
        Intent myIntent = new Intent(login.this, bonus.class);
        startActivity(myIntent);
    }

    public void onBackPressed() {
        if(Objects.equals(autoLoad.userName, "")){
            save("@hanif");
        }
        Intent myIntent = new Intent(login.this, bonus.class);
        startActivity(myIntent);
    }
}