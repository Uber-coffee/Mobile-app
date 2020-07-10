package com.example.ubercoffee;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class MenuFilterActivity extends Activity {

    Button buttonSize1;
    Button buttonSize2;
    Button buttonSize3;
    Button buttonPrice;
    Button buttonAccept;
    Button buttonResent;

    int sizeDrinkable = -1;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_filter);

        buttonSize1 = findViewById(R.id.s1);
        buttonSize2 = findViewById(R.id.s2);
        buttonSize3 = findViewById(R.id.s3);
        buttonAccept = findViewById(R.id.buttonAccept);
        buttonResent = findViewById(R.id.buttonResent);
        //buttonPrice = findViewById(R.id.coffee);


        buttonSize1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sizeDrinkable = 1;
                Context context = v.getContext();
                buttonSize1.setAlpha(1f);
                buttonSize2.setAlpha(0.7f);
                buttonSize3.setAlpha(0.7f);
                //buttonSize2.setBackground(context.getResources().getDrawable(R.drawable.rounded_button));
                //buttonSize3.setBackground(context.getResources().getDrawable(R.drawable.rounded_button));
                //buttonSize1.setBackground(context.getResources().getDrawable(R.drawable.rounded_new_button));
            }
        });

        buttonSize2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sizeDrinkable = 2;
                Context context = v.getContext();
                buttonSize2.setAlpha(1f);
                buttonSize1.setAlpha(0.7f);
                buttonSize3.setAlpha(0.7f);
            }
        });

        buttonSize3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sizeDrinkable = 3;
                Context context = v.getContext();
                buttonSize3.setAlpha(1f);
                buttonSize1.setAlpha(0.7f);
                buttonSize2.setAlpha(0.7f);
                //buttonSize2.setBackground(context.getResources().getDrawable(R.drawable.rounded_button));
                //buttonSize1.setBackground(context.getResources().getDrawable(R.drawable.rounded_button));
                //buttonSize3.setBackground(context.getResources().getDrawable(R.drawable.rounded_new_button));
            }
        });

        buttonAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Context context = v.getContext();
                //Intent intent = new Intent(MenuFilterActivity.this, MenuListActivity.class);
                //intent.putExtra("sizeDrink", sizeDrinkable);
                //sizeDrinkable = -1;
                //startActivity(intent);
                finish();
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