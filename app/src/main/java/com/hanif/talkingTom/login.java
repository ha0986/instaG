package com.hanif.talkingTom;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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
                if (inputs.startsWith("https://instagram.com/")) {
                    Toast.makeText(this, "url not supported. Put username only", Toast.LENGTH_LONG).show();

                }else if (inputs.contains(".")){
                    inputs = inputs.replace(".","*");
                }else if (inputs.contains("/")){
                    inputs = inputs.replace("/","`");
                }else if (inputs.contains("$")){
                    inputs = inputs.replace("$","~");
                }


                if (inputs.startsWith("@")) {
                    autoLoad.savedata(inputs);
                    save(inputs);
                } else {
                    String name = "@" + inputs;
                    autoLoad.savedata(name);
                    save(name);
                }}




            });


        gues.setOnClickListener(v -> save("@hanif"));
    }


    public void save(String userName){
        autoLoad.userName = userName;
        Log.d("name",autoLoad.userName);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("name", userName);
        editor.apply();
        changeScene();
    }

    public void changeScene(){
        autoLoad.loadAdd(this);
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