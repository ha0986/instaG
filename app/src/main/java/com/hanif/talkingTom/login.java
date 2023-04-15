package com.hanif.talkingTom;

import static android.text.TextUtils.split;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;
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
                    String inputs = text.getText().toString().trim();
                    if (inputs.isEmpty()) {
                        autoLoad.alart(login.this, "Please enter your Url");
                    } else if (inputs.contains("https://instagram.com")) {
                        Toast.makeText(this, "Only username is supported", Toast.LENGTH_LONG).show();
                    } else {
                        ProgressDialog dialog = ProgressDialog.show(this, "",
                                "Loading. Please wait...", true);
                        if (Objects.equals(check, "true")) {
                            autoLoad.removedata(autoLoad.userName);
                        }

                            if (inputs.contains(".")) {
                                inputs = inputs.replace(".", "*");
                            } else if (inputs.contains("/")) {
                                inputs = inputs.replace("/", "`");
                            } else if (inputs.contains("$")) {
                                inputs = inputs.replace("$", "~");
                            } else if (inputs.contains("@")) {
                                inputs = inputs.replace("@","");
                            }

                            autoLoad.savedata(inputs);
                            save(inputs);
                            dialog.dismiss();
                        }
                    
                });


        gues.setOnClickListener(v -> save("hanif"));
        }


        public void save (String userName){
            autoLoad.userName = userName;
            SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("name", userName);
            editor.apply();
            changeScene();
        }

        public void changeScene () {
            autoLoad.loadAdd(this);
            Intent myIntent = new Intent(login.this, bonus.class);
            startActivity(myIntent);
        }

        public void onBackPressed () {
            if (Objects.equals(autoLoad.userName, "")) {
                save("@hanif");
            }
            Intent myIntent = new Intent(login.this, bonus.class);
            startActivity(myIntent);
        }
    }