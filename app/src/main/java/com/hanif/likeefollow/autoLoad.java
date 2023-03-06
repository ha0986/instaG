package com.hanif.likeefollow;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;

import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;


public class autoLoad {
    public static String userName = "@hanif";
    private static RewardedAd mRewardedAd;
    private static InterstitialAd mInterstitialAd;
    public static boolean connection = false;
    public static String points = "500"; //userPoints
    public static FirebaseDatabase database = FirebaseDatabase.getInstance();
    public static ArrayList<String> nameList = new ArrayList<>();
    public static ArrayList<String> follow = new ArrayList<>();
    public static String followed = "@hanif, @tikfollow";
    static boolean isLoading;


    // splash screen theke purbe kader follow kora hoiche oi id gula "followed" variable a
    //add kora holo.


    //for https
    static RequestQueue queue;

    static String url = "https://www.google.com/";


    public static void alart(Context context, String text) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Likee Likes");
        builder.setMessage(text);
        AlertDialog alert = builder.create();
        alert.show();
    }


    public static void checkNetwork(Context context) {
        queue = Volley.newRequestQueue(context);
        @SuppressLint("SetTextI18n") StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> connection = true,
                error -> {
                    connection = false;
                    alart(context, "Please turn on your data connection");
                });

        queue.add(stringRequest);
    }


    public static void loadBanner(Context context, String gravity) {
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setGravity(Gravity.TOP);
        AdView adView = new AdView(context);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId("ca-app-pub-9422110628550448/1694359733");
        AdRequest.Builder adRequestBuilder = new AdRequest.Builder();
        layout.addView(adView);
        adView.loadAd(adRequestBuilder.build());
    }


    public static void loadAdd(Context context) {
        MobileAds.initialize(context, initializationStatus -> {
        });
    }

    public static void loadInter(Context context) {

        AdRequest loadInter = new AdRequest.Builder().build();

        InterstitialAd.load(context, "ca-app-pub-9422110628550448/4571820425", loadInter,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        mInterstitialAd = interstitialAd;
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        mInterstitialAd = null;
                    }
                });
    }



    public static void showInter(Activity activity) {
        if (mInterstitialAd != null) {
            mInterstitialAd.show(activity);
        } else {
            Log.d("TAG", "The interstitial ad wasn't ready yet.");
        }
    }




    public static void loadReward(Context context,Activity activity, String id) {
        if (mRewardedAd == null) {
            isLoading = true;
            AdRequest adRequest = new AdRequest.Builder().build();
            RewardedAd.load(context, id, adRequest,
                    new RewardedAdLoadCallback() {
                        @Override
                        public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {

                            mRewardedAd = null;
                            isLoading = false;
                            Toast.makeText(context, "Ads failed to load try again later", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
                            mRewardedAd = rewardedAd;
                            isLoading = false;
                            showReward(context,activity,id);
                        }
                    });
        }else {
            showReward(context,activity, id);
        }
    }






    public static void showReward(Context context,Activity activity, String id) {

        if (mRewardedAd == null) {
            Log.d("TAG", "The rewarded ad wasn't ready yet.");
            return;
        }

        mRewardedAd.setFullScreenContentCallback(
                new FullScreenContentCallback() {
                    @Override
                    public void onAdShowedFullScreenContent() {

                        Toast.makeText(activity, "onAdShowedFullScreenContent", Toast.LENGTH_SHORT)
                                .show();
                    }


                    @Override
                    public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                        mRewardedAd = null;
                        Toast.makeText(activity, "Ads Failed", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onAdDismissedFullScreenContent() {
                        mRewardedAd = null;
                        Toast.makeText(activity, "onAdDismissedFullScreenContent", Toast.LENGTH_SHORT).show();
                        loadReward(context, activity, id);
                    }
                });
        mRewardedAd.show(
                activity,
                rewardItem -> {
                    // Handle the reward.
                    Log.d("TAG", "You got reward.");
                    int rewardAmount = rewardItem.getAmount();
                    if (rewardAmount== 1){
                        // have to start the bot. because add shown from the task page
                    } else if (rewardAmount==2) {
                        //have to store id for bonus the user
                    }else {
                        // set points
                    }
                });

    }



































    public static void getdata() {

        DatabaseReference myRef = database.getReference("tikfan");


        myRef.child(userName).get().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.e("firebase", "Error getting data", task.getException());
            } else {
                Log.d("username", userName);
                points = String.valueOf(task.getResult().getValue());
                doTask.userpoints.setText(points);
                doTask.plusPoints = Integer.valueOf(points);
            }
        });
    }

    public static void getDatas() {
        DatabaseReference myRef = database.getReference("tikfan");


        myRef.get().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.e("firebase", "Error getting data", task.getException());
            } else {
                String dict = String.valueOf(task.getResult().getValue());
                dict = dict.replace("{", "");
                dict = dict.replace("}", "");
                String[] list = dict.split(",");
                String[] foll = followed.split(",");
                follow.addAll(Arrays.asList(foll));


//jei id gulake follow kora hoiche oigulake splash screen hote followed variable a
// add kora hoiche .Tarpor aigulake follow array te add kora holo


//aikhane jei id gula purbe follow kora hoiche oigulake remove kore
// namelist valiable a add kortechi
                for (int i = 0; i < list.length; i++) {
                    String[] split = list[i].split("=");

                    if (Integer.parseInt(split[1]) > 200 && !follow.contains(split[0])) {
                        nameList.add(list[i]);
                    }

                }

            }
        });
    }

    public static void savedata(String userName) {
        DatabaseReference myRef = database.getReference("tikfan");
        myRef.child(userName).setValue(Integer.valueOf(points));
    }


    public static void removedata(String userName) {
        DatabaseReference myRef = database.getReference("tikfan");
        myRef.child(userName).removeValue();

    }


    public static void storePlusMinus(Integer pluspoints, String minusUser, Integer minusPoints) {
        DatabaseReference myRef = database.getReference("tikfan");
        myRef.child(userName).setValue(pluspoints);
        myRef.child(minusUser).setValue(minusPoints);
    }

}

