package com.hanif.likeefollow;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class floting extends AppCompatActivity {


    public WindowManager.LayoutParams params;
    public WindowManager mWindowManager;

    public View mView;
    public Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.overlay_layout);

        Button exitApp = findViewById(R.id.exitApp);
        LinearLayout move= findViewById(R.id.liniar);
        context= floting.this;





        exitApp.setOnClickListener(v -> {
            LayoutInflater layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);;
            mView = layoutInflater.inflate(R.layout.overlay_layout, null);
            // set onClickListener on the remove button, which removes
            // the view from the window
            mView.findViewById(R.id.exitApp).setOnClickListener(view -> close());
        });










    }




    public void close() {

        try {
            // remove the view from the window
            ((WindowManager)context.getSystemService(WINDOW_SERVICE)).removeView(mView);
            // invalidate the view
            mView.invalidate();
            // remove all views
            ((ViewGroup)mView.getParent()).removeAllViews();

            // the above steps are necessary when you are adding and removing
            // the view simultaneously, it might give some exceptions
        } catch (Exception e) {
            Log.d("Error2",e.toString());
        }
    }
}