package com.hanif.likeefollow;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
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
                if (inputs.startsWith("https://instagram.com/")){
                    String[] spli = inputs.split("https://instagram.com/");
                    String[] spl = spli[1].split("igshid=");



                    if (spl[0].contains(".")){
                        spl[0] = spl[0].replace(".","*");
                    }else if (spl[0].contains("/")){
                        spl[0] = spl[0].replace("/","`");
                    }else if (spl[0].contains("$")){
                        spl[0] = spl[0].replace("$","~");
                    }
                    autoLoad.savedata(spl[0]);
                    save(spl[0]);
                }else {
                    autoLoad.alart(login.this, "Url not correct");
                }

            }

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