package com.example.ubercoffee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    TextView tvSuccess;
    ImageView ivSuccess;
    TextView tvFail;
    ImageView ivFail;
    EditText editPhone;
    Button buttonEnter;
    CheckBox checkBox;

    boolean phoneNumberIsCorrect = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvSuccess = findViewById(R.id.text_correct);
        ivSuccess = findViewById(R.id.image_success);
        tvFail = findViewById(R.id.text_incorrect);
        ivFail = findViewById(R.id.image_fail);
        editPhone = findViewById(R.id.editTextPhone);
        buttonEnter = findViewById(R.id.button);
        checkBox = findViewById(R.id.checkBox2);

        buttonEnter.setEnabled(false);
        tvSuccess.setVisibility(View.INVISIBLE);
        ivSuccess.setVisibility(View.INVISIBLE);
        tvFail.setVisibility(View.INVISIBLE);
        ivFail.setVisibility(View.INVISIBLE);


        //here we show a right message when it's needed
        editPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String input = charSequence.toString();
                //Log.d("input", input); - print input for debugging
                if (!input.matches(".*\\d.*")) {
                    //no digits - field is empty
                    //initially there should be no messages on the screen
                    tvSuccess.setVisibility(View.INVISIBLE);
                    ivSuccess.setVisibility(View.INVISIBLE);
                    tvFail.setVisibility(View.INVISIBLE);
                    ivFail.setVisibility(View.INVISIBLE);
                } else {
                    if (input.contains("X")) {
                        //no 'X' - full number was entered, else - not enough digits were entered
                        phoneNumberIsCorrect = false;
                    } else {
                        //full number was entered
                        phoneNumberIsCorrect = true;
                    }

                    if (phoneNumberIsCorrect) {
                        tvFail.setVisibility(View.INVISIBLE);
                        ivFail.setVisibility(View.INVISIBLE);
                        tvSuccess.setVisibility(View.VISIBLE);
                        ivSuccess.setVisibility(View.VISIBLE);
                        if (checkBox.isChecked()) {
                            buttonEnter.setEnabled(true);
                        }

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


        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (checkBox.isChecked() && phoneNumberIsCorrect) {
                    buttonEnter.setEnabled(true);
                } else {
                    buttonEnter.setEnabled(false);
                }
            }
        });

        buttonEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent smsActivityIntent = new Intent(MainActivity.this, SmsCodeActivity.class);
                startActivity(smsActivityIntent);
            }
        });


    }
}