package com.example.ubercoffee;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SmsCodeActivity extends AppCompatActivity {

    boolean codeIsCorrect = false;

    TextView tvSuccess;
    ImageView ivSuccess;
    TextView tvFail;
    ImageView ivFail;
    EditText editSmsCode;
    Button buttonEnter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms_code);

        tvSuccess = findViewById(R.id.text_correct);
        ivSuccess = findViewById(R.id.image_success);
        tvFail = findViewById(R.id.text_incorrect);
        ivFail = findViewById(R.id.image_fail);
        editSmsCode = findViewById(R.id.editTextPhone);
        buttonEnter = findViewById(R.id.button);

        buttonEnter.setEnabled(false);
        tvSuccess.setVisibility(View.INVISIBLE);
        ivSuccess.setVisibility(View.INVISIBLE);
        tvFail.setVisibility(View.INVISIBLE);
        ivFail.setVisibility(View.INVISIBLE);


        editSmsCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String input = charSequence.toString();

                if (!input.matches(".*\\d.*")) {
                    //no digits - field is empty
                    //initially there should be no messages on the screen
                    tvSuccess.setVisibility(View.INVISIBLE);
                    ivSuccess.setVisibility(View.INVISIBLE);
                    tvFail.setVisibility(View.INVISIBLE);
                    ivFail.setVisibility(View.INVISIBLE);
                } else {
                    if (!input.contains("X")) {
                        //4 digits were entered
                        codeIsCorrect = true;
                    } else {
                        //less that 4 digits were entered
                        //Code should consist of 4 digits
                        //additional message for user could be added here
                        codeIsCorrect = false;
                    }

                    if (codeIsCorrect) {
                        buttonEnter.setEnabled(true);
                        tvFail.setVisibility(View.INVISIBLE);
                        ivFail.setVisibility(View.INVISIBLE);
                        tvSuccess.setVisibility(View.VISIBLE);
                        ivSuccess.setVisibility(View.VISIBLE);
                    } else {
                        buttonEnter.setEnabled(false);
                        tvFail.setVisibility(View.VISIBLE);
                        ivFail.setVisibility(View.VISIBLE);
                        tvSuccess.setVisibility(View.INVISIBLE);
                        ivSuccess.setVisibility(View.INVISIBLE);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });


    }


}
