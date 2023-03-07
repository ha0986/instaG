package com.hanif.likeefollow;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.Arrays;

public class jokes extends AppCompatActivity {
    private AdView mAdView;
    int count = 0;
    int size = 1;
    public ArrayList<Integer> showInter= new ArrayList<>(Arrays.asList(5,10,15,20,25,30,35));
    String[] joke;
    TextView jokes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jokes);

        autoLoad.loadInter(this);
        jokes = findViewById(R.id.textView2);

        Button next = findViewById(R.id.next);
        Button prev  = findViewById(R.id.prev);
        Button copy  = findViewById(R.id.Copy);



        getJokes();
//        mAdView = findViewById(R.id.adView);
//        AdRequest adRequest = new AdRequest.Builder().build();
//        mAdView.loadAd(adRequest);

        next.setOnClickListener(v -> {
            if(count<size-1){
                count +=1;
                setJokes();
            }else {
                count = 0;
                setJokes();
            }

        });

        prev.setOnClickListener(v -> {
            if(count<2){
                count = size-1;
                setJokes();
            }else {
                count-=1;
                setJokes();
            }
        });

        copy.setOnClickListener(v -> {
            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            if (joke != null){
                ClipData clip = ClipData.newPlainText("jokes",joke[count]);
                clipboard.setPrimaryClip(clip);
            }


            autoLoad.alart(jokes.this,"Copied");
        });

    }


    public void setJokes(){
        if (joke!= null){
            jokes.setText(joke[count]);
        }

        if(showInter.contains(count)){
            autoLoad.showInter(this);
        }
    }

    public void getJokes(){
        String url = "https://jukes-86dd2-default-rtdb.firebaseio.com/Bangla/jokes.json";
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response ->{
                        joke= response.split("&&");
                        setJokes();
                        size= joke.length;
                        },
                error -> autoLoad.alart(this, "Check data connection"));
        queue.add(stringRequest);

    }




    
    public void onBackPressed() {
        Intent myIntent = new Intent(jokes.this, profile.class);
        startActivity(myIntent);
    }




}