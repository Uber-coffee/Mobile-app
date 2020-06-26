package com.example.ubercoffee;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SmsCodeActivity extends AppCompatActivity {

    TextView tvSuccess;
    ImageView ivSuccess;
    TextView tvFail;
    ImageView ivFail;
    EditText editSmsCode;
    Button buttonEnter;
    CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms_code);

        tvSuccess = (TextView) findViewById(R.id.text_correct);
        ivSuccess = (ImageView) findViewById(R.id.image_success);
        tvFail = (TextView) findViewById(R.id.text_incorrect);
        ivFail = (ImageView) findViewById(R.id.image_fail);
        editSmsCode = (EditText) findViewById(R.id.editTextPhone);
        buttonEnter = (Button) findViewById(R.id.button);

        editSmsCode.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                CharSequence input = textView.getText();

                if (input.length() == 0){
                    //initially there should be no messages on the screen
                    tvSuccess.setVisibility(View.INVISIBLE);
                    ivSuccess.setVisibility(View.INVISIBLE);
                    tvFail.setVisibility(View.INVISIBLE);
                    ivFail.setVisibility(View.INVISIBLE);
                } else if (input.charAt(0) != '+' && input.charAt(1) != '7' && input.length() == 12){
                    //Phone number should start with '+'
                    tvFail.setVisibility(View.VISIBLE);
                    ivFail.setVisibility(View.VISIBLE);
                }
                return true;
            }
        });
    }


}
