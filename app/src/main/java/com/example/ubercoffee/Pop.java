package com.example.ubercoffee;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.OrientationEventListener;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;


public class Pop extends Activity implements View.OnClickListener {

    private LinearLayout linearLayout;
    Button small_distance;
    Button middle_distance;
    Button big_distance;

    Button red_fullness;
    Button yellow_fullness;
    Button green_fullness;

    Button apply_filter;
    Button reset_filter;

    private int distance_filter = 0;
    private int fullness_filter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.pop_activity);


        linearLayout = (LinearLayout) findViewById(R.id.llmain);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);


        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.75),(int)(height*.65));

        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.gravity = Gravity.CENTER;
        layoutParams.x = 0;
        layoutParams.y = -20;

        getWindow().setAttributes(layoutParams);

        SharedPreferences sharedPreferences = getSharedPreferences("for.filters", MODE_PRIVATE);

        if(sharedPreferences.contains("Distance")) {
            distance_filter = sharedPreferences.getInt("Distance", 100);
            fullness_filter = sharedPreferences.getInt("Fullness",100);
        }

        apply_filter = (Button) findViewById(R.id.enter);
        reset_filter = (Button) findViewById(R.id.reset);

        small_distance = (Button) findViewById(R.id.less_half);
        middle_distance = (Button) findViewById(R.id.less_km);
        big_distance = (Button) findViewById(R.id.less_km_and_half);

        red_fullness = (Button) findViewById(R.id.red_fullness);
        yellow_fullness = (Button) findViewById(R.id.yellow_fullness);
        green_fullness = (Button) findViewById(R.id.green_fullness);

        apply_filter.setOnClickListener(this);
        reset_filter.setOnClickListener(this);

        small_distance.setOnClickListener(this);
        middle_distance.setOnClickListener(this);
        big_distance.setOnClickListener(this);

        red_fullness.setOnClickListener(this);
        yellow_fullness.setOnClickListener(this);
        green_fullness.setOnClickListener(this);

        switch (distance_filter){
            case 1:
                small_distance.performClick();
                break;
            case 2:
                middle_distance.performClick();
                break;
            case 3:
                big_distance.performClick();
                break;
        }

        switch (fullness_filter){
            case 1:
                green_fullness.performClick();
                break;
            case 2:
                yellow_fullness.performClick();
                break;
            case 3:
                red_fullness.performClick();
                break;
        }

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.less_half:
                small_distance.setAlpha(1f);
                middle_distance.setAlpha(0.7f);
                big_distance.setAlpha(0.7f);
                distance_filter = 1;
                break;
            case R.id.less_km:
                small_distance.setAlpha(0.7f);
                middle_distance.setAlpha(1f);
                big_distance.setAlpha(0.7f);
                distance_filter = 2;
                break;
            case R.id.less_km_and_half:
                small_distance.setAlpha(0.7f);
                middle_distance.setAlpha(0.7f);
                big_distance.setAlpha(1f);
                distance_filter = 3;
                break;
            case R.id.red_fullness:
                red_fullness.setTextColor(getColor(R.color.white));
                green_fullness.setTextColor(getColor(R.color.green));
                yellow_fullness.setTextColor(getColor(R.color.yellow));
                fullness_filter = 3;
                break;
            case R.id.yellow_fullness:
                red_fullness.setTextColor(getColor(R.color.red));
                green_fullness.setTextColor(getColor(R.color.green));
                yellow_fullness.setTextColor(getColor(R.color.white));
                fullness_filter = 2;
                break;
            case  R.id.green_fullness:
                red_fullness.setTextColor(getColor(R.color.red));
                green_fullness.setTextColor(getColor(R.color.white));
                yellow_fullness.setTextColor(getColor(R.color.yellow));
                fullness_filter = 1;
                break;
            case R.id.enter:
                SharedPreferences sharedPreferences = getSharedPreferences("for.filters", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear().apply();
                editor.putInt("Distance", distance_filter);
                editor.putInt("Fullness", fullness_filter);
                editor.apply();
                setResult(RESULT_OK);
                finish();
                break;
            case R.id.reset:
                big_distance.setAlpha(0.7f);
                small_distance.setAlpha(0.7f);
                middle_distance.setAlpha(0.7f);
                red_fullness.setTextColor(getColor(R.color.red));
                green_fullness.setTextColor(getColor(R.color.green));
                yellow_fullness.setTextColor(getColor(R.color.yellow));
                distance_filter = fullness_filter = 0;
                SharedPreferences sharedPreferences1 = getSharedPreferences("for.filters", MODE_PRIVATE);
                SharedPreferences.Editor editor1 = sharedPreferences1.edit();
                editor1.clear().apply();
        }
    }
}
