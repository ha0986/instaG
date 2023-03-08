package com.hanif.likeefollow;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.google.android.gms.tasks.Task;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.UpdateAvailability;

import java.util.ArrayList;
import java.util.Arrays;


public class doTask extends AppCompatActivity implements View.OnClickListener {
    public Intent myIntent;
    private AdView mAdView;
    public static TextView userpoints;
    public String minusUser;
    public Integer minusPoint=500;
    public static Integer plusPoints=500;
    public Integer click=1;
    public ArrayList<Integer> showInter= new ArrayList<>(Arrays.asList(3,7,10,13));
    private AppUpdateManager appUpdateManager;
    private static final int IMMEDIATE_APP_UPDATE_REQ_CODE = 124;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        Button reward = findViewById(R.id.reward200);
        Button back = findViewById(R.id.back);
        Button jokes = findViewById(R.id.jokes);
        Button follow = findViewById(R.id.follow);
        Button rate = findViewById(R.id.rates);
        Button proof = findViewById(R.id.proof2);
        userpoints = findViewById(R.id.taskpoint);
        LottieAnimationView view = findViewById(R.id.animationView);


        userpoints.setText(autoLoad.points);
        reward.setOnClickListener(this);
        back.setOnClickListener(this);
        jokes.setOnClickListener(this);
        follow.setOnClickListener(this);
        rate.setOnClickListener(this);
        proof.setOnClickListener(this);
        view.setOnClickListener(this);

        autoLoad.getDatas();
        autoLoad.checkNetwork(this);



//        autoLoad.loadInter(this);
//        mAdView = findViewById(R.id.adView);
//        AdRequest adRequest = new AdRequest.Builder().build();
//        mAdView.loadAd(adRequest);


        appUpdateManager = AppUpdateManagerFactory.create(getApplicationContext());
        checkUpdate();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.reward200:

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (!Settings.canDrawOverlays(this)) {
                        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" +getPackageName()));
                        startActivity(intent);
                    }else{
                        startBot();
                    }
                }
                break;
            case  R.id.jokes:
                myIntent = new Intent(doTask.this, jokes.class);
                startActivity(myIntent);
                break;
            case  R.id.animationView:
                myIntent = new Intent(doTask.this, bonus.class);
                startActivity(myIntent);
                break;
            case R.id.back:
                myIntent = new Intent(doTask.this, profile.class);
                startActivity(myIntent);
                break;
            case R.id.proof2:
                myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://youtube.com/shorts/Ya74m-RaCZM?feature=share"));
                startActivity(myIntent);
                break;
            case R.id.rates:
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id="+ getPackageName()));
                startActivity(browserIntent);
                break;
            case R.id.follow:
                startTask();
                click+=1;
                break;
        }
    }




    @SuppressLint("SetTextI18n")
    public void startTask(){
        String[] toSplit = autoLoad.nameList.get(click).split("=");
        minusUser = toSplit[0];
        plusPoints = plusPoints+100;
        minusPoint =Integer.parseInt(toSplit[1])-100;
        autoLoad.storePlusMinus(plusPoints,minusUser, minusPoint);
        clicked();
        autoLoad.points = String.valueOf(plusPoints);
        userpoints.setText(plusPoints.toString());
        if(showInter.contains(click)){
            autoLoad.showInter(this);
        }
        minusUser=minusUser.trim();
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://l.likee.video/"+ minusUser.replace("*","/")));
        startActivity(intent);
    }



    public void clicked(){
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        SharedPreferences.Editor editor = pref.edit();
        autoLoad.followed = autoLoad.followed+ ","+ minusUser;
        editor.putString("done", autoLoad.followed);
        editor.apply();
    }

    public void onBackPressed() {
        Intent myIntent = new Intent(doTask.this, profile.class);
        startActivity(myIntent);
    }










    private void checkUpdate() {

        Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();

        appUpdateInfoTask.addOnSuccessListener(appUpdateInfo -> {
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                    && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {
                startUpdateFlow(appUpdateInfo);
            } else if  (appUpdateInfo.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS){
                startUpdateFlow(appUpdateInfo);
            }
        });
    }

    private void startUpdateFlow(AppUpdateInfo appUpdateInfo) {
        try {

            appUpdateManager.startUpdateFlowForResult(appUpdateInfo, AppUpdateType.IMMEDIATE, this, IMMEDIATE_APP_UPDATE_REQ_CODE);
        } catch (IntentSender.SendIntentException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMMEDIATE_APP_UPDATE_REQ_CODE) {
            if (resultCode == RESULT_CANCELED) {
                Toast.makeText(getApplicationContext(), "Update canceled by user! Result Code: " + resultCode, Toast.LENGTH_LONG).show();
            } else if (resultCode == RESULT_OK) {
                Toast.makeText(getApplicationContext(), "Update success! Result Code: " + resultCode, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "Update Failed! Result Code: " + resultCode, Toast.LENGTH_LONG).show();
                checkUpdate();
            }
        }
    }

    public void startBot(){
        startService(new Intent(doTask.this, FloatingViewService.class));
    }


}