package com.example.ubercoffee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

    //check if input only contains digits
    public boolean allDigits(CharSequence sequence) {
        for (int i = 0; i < sequence.length(); i++) {
            if (!Character.isDigit(sequence.charAt(i))) {
                return false;
            }
        }
        return true;
    }

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
                if (charSequence.length() == 0) {
                    //initially there should be no messages on the screen
                    tvSuccess.setVisibility(View.INVISIBLE);
                    ivSuccess.setVisibility(View.INVISIBLE);
                    tvFail.setVisibility(View.INVISIBLE);
                    ivFail.setVisibility(View.INVISIBLE);
                } else {
                    if (charSequence.charAt(0) != '+') {
                        //Phone number should start with '+'
                        //additional message for user could be added here
                        phoneNumberIsCorrect = false;
                    } else if (charSequence.length() == 12 && allDigits(charSequence.subSequence(1, charSequence.length()))) {
                        phoneNumberIsCorrect = true;
                    } else {
                        //Phone number should consist of 11 digits
                        //additional message for user could be added here
                        phoneNumberIsCorrect = false;
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