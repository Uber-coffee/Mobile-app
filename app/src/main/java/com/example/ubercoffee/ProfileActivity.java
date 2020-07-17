package com.example.ubercoffee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {

    private String phone;
    private Button buttonShops;
    private Button buttonProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        buttonShops = findViewById(R.id.listOfShops);
        buttonProfile = findViewById(R.id.profile);

        buttonShops.setAlpha(0.7f);
        buttonProfile.setAlpha(1f);

        phone = getIntent().getStringExtra("phone_number");

        TextView tvPhone= (TextView) findViewById(R.id.phone);
        //передаем строкой адрес
        int sizePhone = phone.length();
        String new_phone = "<u> " + phone.substring(0, sizePhone) + " </u>";
        Spanned textSpan  =  android.text.Html.fromHtml(new_phone);
        tvPhone.setText(textSpan);

        buttonShops.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //buttonShops.setAlpha(1f);
                //buttonProfile.setAlpha(0.7f);
                finish();
                //Intent intentShops = new Intent(ProfileActivity.this, ListOfShopsActivity.class);
                //startActivity(intentShops);
            }
        });


    }

}