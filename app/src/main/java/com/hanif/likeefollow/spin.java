package com.hanif.likeefollow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import com.bluehomestudio.luckywheel.LuckyWheel;
import com.bluehomestudio.luckywheel.OnLuckyWheelReachTheTarget;
import com.bluehomestudio.luckywheel.WheelItem;

import java.util.ArrayList;

public class spin extends AppCompatActivity {
    String points;
    LuckyWheel luckyWheel;
    ArrayList<WheelItem> wheelItemsList  = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spin);


        luckyWheel = findViewById(R.id.lwv);


        WheelItem wheelItem1 = new WheelItem(Color.WHITE,
                BitmapFactory.decodeResource(getResources(), R.drawable.heart),
                "+4");

        wheelItemsList.add(wheelItem1);
        luckyWheel.addWheelItems(wheelItemsList);


        luckyWheel.setLuckyWheelReachTheTarget(() -> {
            WheelItem itemSelect  = wheelItemsList.get(Integer.parseInt(points)-1);
            String points_ammount  = itemSelect.text;
            Toast.makeText(spin.this, points_ammount, Toast.LENGTH_SHORT).show();
        });





//        WheelItem wheelItem2 = new WheelItem(Color.BLACK,
//                BitmapFactory.decodeResource(getResources(), R.drawable.heart),
//                "+8");
//
//        WheelItem wheelItem3 = new WheelItem(Color.BLUE,
//                BitmapFactory.decodeResource(getResources(), R.drawable.heart),
//                "+10");
//
//        WheelItem wheelItem4 = new WheelItem(Color.GRAY,
//                BitmapFactory.decodeResource(getResources(), R.drawable.heart),
//                "+20");
//
//        WheelItem wheelItem5 = new WheelItem(Color.GRAY,
//                BitmapFactory.decodeResource(getResources(), R.drawable.person),
//                "+1");
//
//        WheelItem wheelItem6 = new WheelItem(Color.GREEN,
//                BitmapFactory.decodeResource(getResources(), R.drawable.person),
//                "+4");

    }
}