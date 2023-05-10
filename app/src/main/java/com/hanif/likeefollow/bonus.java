package com.hanif.likeefollow;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;

import java.util.Date;

import java.util.Locale;
import java.util.Objects;


public class bonus extends AppCompatActivity implements View.OnClickListener {
    public static String tag;
    public String claimedDate;
    public Integer next;
    public String date;
    public Button claimedButton;
    public String btnText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bonus);


        Button button1 = findViewById(R.id.button1);
        Button button2 = findViewById(R.id.button2);
        Button button3 = findViewById(R.id.button3);
        Button button4 = findViewById(R.id.button4);
        Button button5 = findViewById(R.id.button5);
        Button button6 = findViewById(R.id.button6);
        Button button7 = findViewById(R.id.button7);
        Button button8 = findViewById(R.id.button8);
        Button back = findViewById(R.id.backs);

        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
        button6.setOnClickListener(this);
        button7.setOnClickListener(this);
        button8.setOnClickListener(this);




        getDatas();

        AdView mAdView = findViewById(R.id.adView3);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);










        back.setOnClickListener(v -> {
            autoLoad.showInter(this);
            Intent myIntent = new Intent(bonus.this,doTask.class);
            startActivity(myIntent);
        });
    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.button1:
                tag = "1";
                claimedButton = findViewById(R.id.button1);
                break;
            case R.id.button2:
                tag = "2";
                claimedButton = findViewById(R.id.button2);
                break;
            case R.id.button3:
                tag = "3";
                claimedButton = findViewById(R.id.button3);
                break;
            case R.id.button4:
                tag = "4";
                claimedButton = findViewById(R.id.button4);
                break;
            case R.id.button5:
                tag = "5";
                claimedButton = findViewById(R.id.button5);
                break;
            case R.id.button6:
                tag = "6";
                claimedButton = findViewById(R.id.button6);
                break;
            case R.id.button7:
                tag = "7";
                claimedButton = findViewById(R.id.button7);
                break;
            case R.id.button8:
                tag = "8";
                claimedButton = findViewById(R.id.button8);
                break;
        }
        btnText = (String) claimedButton.getText();
        check();
    }


    @SuppressLint({"ResourceAsColor", "SetTextI18n"})
    public void getDatas() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        next = Integer.valueOf(pref.getString("next", "1"));
        claimedDate = pref.getString("date", "1");

        int[] BUTTON_IDS = {
                R.id.button1,
                R.id.button2,
                R.id.button3,
                R.id.button4,
                R.id.button5,
                R.id.button6,
                R.id.button7,
                R.id.button8,
        };
        for (int i = 0; i < next - 1; i++) {
            Button button = findViewById(BUTTON_IDS[i]);
            button.setText("Claimed");
            button.setBackgroundColor(R.color.teal_200);

        }

    }


    public void check() {
        date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        if (Objects.equals(btnText, "Claimed")) {
            autoLoad.alart(this, "You have already Claimed This offer");
        } else if (Objects.equals(btnText, "Claim") && (!Objects.equals(tag, next.toString()))) {
            autoLoad.alart(this, "You are not able to claim this offer");
            autoLoad.showInter(this);
        } else if (Objects.equals(btnText, "Claim") && Objects.equals(tag, next.toString()) && !Objects.equals(claimedDate, date)) {
            loadAdd();
        }

    }

    public void loadAdd() {
        new android.app.AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Likeer")
                .setMessage("Watch add to claim this offer")
                .setPositiveButton("OK", (dialog, which) ->{
                    autoLoad.loadReward(this, bonus.this,autoLoad.bonusAdd, "bonus");
                    new Handler().postDelayed(() -> claim(), 20000);
                })
                .setNegativeButton("No", null)
                .show();


    }


    @SuppressLint({"SetTextI18n", "ResourceAsColor"})
    public void claim() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Days");
        myRef.child(tag).child(autoLoad.userName).setValue(0);


        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        SharedPreferences.Editor editor = pref.edit();
        next = Integer.parseInt(tag) + 1;

        editor.putString("date", date);
        editor.putString("next", String.valueOf(next));
        claimedDate = date;
        editor.apply();


        claimedButton.setText("claimed");
        claimedButton.setBackgroundColor(R.color.claimed);

        Intent myIntent = new Intent(bonus.this,doTask.class);
        startActivity(myIntent);
    }


    public void onBackPressed() {
        autoLoad.showInter(this);
        Intent myIntent = new Intent(bonus.this,doTask.class);
        startActivity(myIntent);
    }




}