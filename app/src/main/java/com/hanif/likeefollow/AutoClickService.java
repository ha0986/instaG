package com.hanif.likeefollow;



import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.GestureDescription;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import android.view.accessibility.AccessibilityEvent;

import java.nio.file.Path;

public class AutoClickService extends AccessibilityService {

    @Override
    public void onCreate() {
        super.onCreate();
    }


    //apparently this method is called every time a event occurs
    @Override
    public void onAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        System.out.println("access event");
        //autoClick(2000, 100, 950, 581);
    }


    @Override
    public void onServiceConnected() {
        super.onServiceConnected();
        autoClick(2000, 100, 950, 581);
    }

    @Override
    public void onInterrupt() {

    }

    public void autoClick(int startTimeMs, int durationMs, int x, int y) {
        boolean isCalled = false;

        System.out.println(isCalled);
    }



    public GestureDescription createGestureDescription(GestureDescription.StrokeDescription... strokes) {
        GestureDescription.Builder builder = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            builder = new GestureDescription.Builder();
        }
        for (GestureDescription.StrokeDescription stroke : strokes) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                builder.addStroke(stroke);
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return builder.build();
        }
        return null;
    }




}