package com.example.ubercoffee;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.SpannableString;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MenuFilterActivity extends Activity {

    private Button buttonSize1;
    private Button buttonSize2;
    private Button buttonSize3;
    private Button buttonAccept;
    private Button buttonResent;
    private SeekBar buttonTopPrice;
    private TextView textTop;
    private TextView tTop1;
    private TextView tTop2;


    public static final String APP_PREFERENCES = "PreferencesFilter";
    public static final String APP_PREFERENCES_SIZE = "Size";
    public static final String APP_PREFERENCES_TYPE = "Type";
    public static final String APP_PREFERENCES_TOP_PRICE = "TopPrice";
    private SharedPreferences PreferencesFilter;
    private SharedPreferences.Editor editor;
    private int sizeFilter = 0;
    private String type = "Popular";
    private int topPrice = 230;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_filter);

        buttonSize1 = findViewById(R.id.s1);
        buttonSize2 = findViewById(R.id.s2);
        buttonSize3 = findViewById(R.id.s3);
        buttonAccept = findViewById(R.id.buttonAccept);
        buttonResent = findViewById(R.id.buttonResent);
        buttonTopPrice = findViewById(R.id.priceBarTop);
        textTop = findViewById(R.id.priceBarTextTop);

        tTop1 = findViewById(R.id.topBar1);
        tTop2 = findViewById(R.id.topBar2);

        SpannableString text =  new SpannableString("100");
        tTop1.setText(text);
        text =  new SpannableString("230");
        tTop2.setText(text);


        PreferencesFilter = getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE);
        if(PreferencesFilter.contains(APP_PREFERENCES_SIZE)) {
            sizeFilter = PreferencesFilter.getInt(APP_PREFERENCES_SIZE, 0);
        }
        if(PreferencesFilter.contains(APP_PREFERENCES_TYPE)){
            type = PreferencesFilter.getString(APP_PREFERENCES_TYPE, "Popular");
        }
        if(PreferencesFilter.contains(APP_PREFERENCES_TOP_PRICE)){
            topPrice = PreferencesFilter.getInt(APP_PREFERENCES_TOP_PRICE, 230);
        }

        buttonTopPrice.setMax(230);
        buttonTopPrice.setMin(100);

        //type = getIntent().getStringExtra("type");

        switch (sizeFilter){
            case 0:
                buttonSize1.setAlpha(0.7f);
                buttonSize2.setAlpha(0.7f);
                buttonSize3.setAlpha(0.7f);
                break;
            case 1:
                buttonSize1.setAlpha(1f);
                buttonSize2.setAlpha(0.7f);
                buttonSize3.setAlpha(0.7f);
                break;
            case 2:
                buttonSize1.setAlpha(0.7f);
                buttonSize2.setAlpha(1f);
                buttonSize3.setAlpha(0.7f);
                break;
            case 3:
                buttonSize1.setAlpha(0.7f);
                buttonSize2.setAlpha(0.7f);
                buttonSize3.setAlpha(1f);
                break;
        }

        buttonTopPrice.setProgress(topPrice);
        String textTopPrice = Integer.toString(topPrice);
        textTop.setText(textTopPrice);

        buttonTopPrice.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                topPrice = seekBar.getProgress();
                String textTopPrice = Integer.toString(topPrice);
                textTop.setText(textTopPrice);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                topPrice = seekBar.getProgress();
                String textTopPrice = Integer.toString(topPrice);
                textTop.setText(textTopPrice);
            }
        });

        buttonSize1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sizeFilter = 1;
                buttonSize1.setAlpha(1f);
                buttonSize2.setAlpha(0.7f);
                buttonSize3.setAlpha(0.7f);
            }
        });

        buttonSize2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sizeFilter = 2;
                buttonSize2.setAlpha(1f);
                buttonSize1.setAlpha(0.7f);
                buttonSize3.setAlpha(0.7f);
            }
        });

        buttonSize3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sizeFilter = 3;
                buttonSize3.setAlpha(1f);
                buttonSize1.setAlpha(0.7f);
                buttonSize2.setAlpha(0.7f);
            }
        });

        buttonAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //SharedPreferences PreferencesMenuAccept = getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE);
                //SharedPreferences.Editor editorAccept = PreferencesMenuAccept.edit();
                //editorAccept.clear().apply();
                //editorAccept.putInt(APP_PREFERENCES_SIZE, sizeFilter);
                //editorAccept.putString(APP_PREFERENCES_TYPE, type);
                //editorAccept.apply();
                PreferencesFilter = getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE);
                editor = PreferencesFilter.edit();
                editor.clear().apply();
                editor.putInt(APP_PREFERENCES_SIZE, sizeFilter);
                editor.putString(APP_PREFERENCES_TYPE, type);
                editor.putInt(APP_PREFERENCES_TOP_PRICE, topPrice);
                editor.apply();
                setResult(RESULT_OK);

                finish();
            }
        });

        buttonResent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonSize1.setAlpha(0.7f);
                buttonSize2.setAlpha(0.7f);
                buttonSize3.setAlpha(0.7f);
                sizeFilter = 0;
                topPrice = 230;
                buttonTopPrice.setProgress(topPrice);
                String textTopPrice = Integer.toString(topPrice);
                textTop.setText(textTopPrice);
                //
                //SharedPreferences PreferencesMenuResent = getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE);
                //SharedPreferences.Editor editorResent = PreferencesMenuResent.edit();
                //editorResent.clear().apply();
                PreferencesFilter = getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE);
                editor = PreferencesFilter.edit();
                editor.clear().apply();
            }
        });

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int)(width*.8), (int)(height*.7));
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = -20;
        getWindow().setAttributes(params);
    }

}