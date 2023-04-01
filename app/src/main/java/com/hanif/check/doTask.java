package com.hanif.check;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.tasks.Task;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.UpdateAvailability;

import java.util.ArrayList;
import java.util.Arrays;


public class doTask extends AppCompatActivity{
    private AdView mAdView;
    public static TextView userpoints;
    public String minusUser;
    public Integer minusPoint=500;
    public static Integer plusPoints=500;
    public Integer click=0;
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



        //test      ca-app-pub-3940256099942544/5224354917
        //  main    ca-app-pub-9422110628550448/1122651035
        reward.setOnClickListener(v->  autoLoad.loadReward(this, doTask.this, "ca-app-pub-9422110628550448/1122651035", "doTask"));
        back.setOnClickListener(v-> startActivity(new Intent(doTask.this, profile.class)));
        jokes.setOnClickListener(v-> startActivity(new Intent(doTask.this, jokes.class)));
        follow.setOnClickListener(v->{
            startTask();
            click+=1;
        });
        rate.setOnClickListener(v->startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id="+ getPackageName()))));
        proof.setOnClickListener(v->startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://youtube.com/shorts/Ya74m-RaCZM?feature=share"))));
        view.setOnClickListener(v-> startActivity(new Intent(doTask.this, bonus.class)));

        autoLoad.getDatas();
        autoLoad.checkNetwork(this);



        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        appUpdateManager = AppUpdateManagerFactory.create(getApplicationContext());
        checkUpdate();



    }




    @SuppressLint("SetTextI18n")
    public void startTask(){
        String[] toSplit = autoLoad.nameList.get(click).split("=");
        if (toSplit.length==2){
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
            if (minusUser.contains("*")){
                minusUser = minusUser.replace("*",".");
            }else if (minusUser.contains("`")){
                minusUser = minusUser.replace("`","/");
            }else if (minusUser.contains("~")){
                minusUser = minusUser.replace("~","$");
            }
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://instagram.com/"+ minusUser)));
        }

    }




    public void clicked(){
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        SharedPreferences.Editor editor = pref.edit();
        autoLoad.followed = autoLoad.followed+ ","+ minusUser;
        editor.putString("done", autoLoad.followed);
        editor.apply();
    }

    public void onBackPressed() {
        startActivity(new Intent(doTask.this, profile.class));
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



    public static void updatePoint(){
        userpoints.setText(autoLoad.points);
    }

}