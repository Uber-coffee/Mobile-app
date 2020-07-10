package com.example.ubercoffee;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;


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
    private int fullness_filter_green = 0;
    private int fullness_filter_yellow = 0;
    private int fullness_filter_red = 0;

    private SharedPreferences sharedPreferences;

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

        sharedPreferences = getSharedPreferences("for.filters", MODE_PRIVATE);

        if(sharedPreferences.contains("Distance")) {
            distance_filter = sharedPreferences.getInt("Distance", 100);
            fullness_filter_green = sharedPreferences.getInt("Fullness_green",100);
            fullness_filter_yellow = sharedPreferences.getInt("Fullness_yellow",100);
            fullness_filter_red = sharedPreferences.getInt("Fullness_red",100);
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
                small_distance.setAlpha(1f);
                break;
            case 2:
                middle_distance.setAlpha(1f);
                break;
            case 3:
                big_distance.setAlpha(1f);
                break;
        }

        if(fullness_filter_green != 0) {
            green_fullness.setTextColor(getColor(R.color.white));
        }
        if( fullness_filter_yellow != 0) {
            yellow_fullness.setTextColor(getColor(R.color.white));
        }
        if(fullness_filter_red != 0) {
            red_fullness.setTextColor(getColor(R.color.white));
        }

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.less_half:
                if(distance_filter == 1){
                    small_distance.setAlpha(0.7f);
                    distance_filter = 0;
                }else {
                    small_distance.setAlpha(1f);
                    middle_distance.setAlpha(0.7f);
                    big_distance.setAlpha(0.7f);
                    distance_filter = 1;
                }
                break;
            case R.id.less_km:
                if(distance_filter == 2){
                    middle_distance.setAlpha(0.7f);
                    distance_filter = 0;
                }else {
                    small_distance.setAlpha(0.7f);
                    middle_distance.setAlpha(1f);
                    big_distance.setAlpha(0.7f);
                    distance_filter = 2;
                }
                break;
            case R.id.less_km_and_half:
                if(distance_filter == 3){
                    big_distance.setAlpha(0.7f);
                    distance_filter = 0;
                }else {
                    small_distance.setAlpha(0.7f);
                    middle_distance.setAlpha(0.7f);
                    big_distance.setAlpha(1f);
                    distance_filter = 3;
                }
                break;
            case R.id.red_fullness:
                if(fullness_filter_red == 3){
                    red_fullness.setTextColor(getColor(R.color.red));
                    fullness_filter_red = 0;
                }else {
                    red_fullness.setTextColor(getColor(R.color.white));
                    fullness_filter_red = 3;
                }
                break;
            case R.id.yellow_fullness:
                if( fullness_filter_yellow == 2){
                    yellow_fullness.setTextColor(getColor(R.color.yellow));
                    fullness_filter_yellow = 0;
                }else {
                    yellow_fullness.setTextColor(getColor(R.color.white));
                    fullness_filter_yellow = 2;
                }
                break;
            case  R.id.green_fullness:
                if(fullness_filter_green == 1){
                    green_fullness.setTextColor(getColor(R.color.green));
                    fullness_filter_green = 0;
                }else {
                    green_fullness.setTextColor(getColor(R.color.white));
                    fullness_filter_green = 1;
                }
                break;
            case R.id.enter:
                SharedPreferences sharedPreferences = getSharedPreferences("for.filters", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear().apply();
                editor.putInt("Distance", distance_filter);
                editor.putInt("Fullness_green", fullness_filter_green);
                editor.putInt("Fullness_yellow", fullness_filter_yellow);
                editor.putInt("Fullness_red", fullness_filter_red);
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
                distance_filter = fullness_filter_green = fullness_filter_red = fullness_filter_yellow = 0;
                SharedPreferences sharedPreferences1 = getSharedPreferences("for.filters", MODE_PRIVATE);
                SharedPreferences.Editor editor1 = sharedPreferences1.edit();
                editor1.clear().apply();
        }
    }
}
