package com.example.ubercoffee;


import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {
    AnimatorSet set;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        clearFilterFile();

        setContentView(R.layout.activity_splash);
        //animation start
        ImageView imgView=(ImageView)findViewById(R.id.imageview);
        set = (AnimatorSet) AnimatorInflater.loadAnimator(this,R.animator.flip);
        set.setTarget(imgView);
        set.start();

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 3000);
    }

    void clearFilterFile(){
        SharedPreferences sharedPreferences = getSharedPreferences("for.filters", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear().apply();
    }

}
