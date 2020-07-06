package com.example.ubercoffee;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.arch.core.executor.TaskExecutor;

import java.util.concurrent.TimeUnit;

public class SmsCodeActivity extends AppCompatActivity {

    boolean codeFormatIsCorrect = false;
    boolean enterCanBeEnabled = true;

    TextView tvFail;
    EditText editSmsCode;
    Button buttonEnter;
    Button buttonResendCode;
    TextView tvTimer;
    String verificationCodeBySystem;

    CountDownTimer cTimer = null;

    void startTimer() {
        tvTimer.setVisibility(View.VISIBLE);
        cTimer = new CountDownTimer(10000, 1000) {
            public void onTick(long millisUntilFinished) {
                tvTimer.setText(getResources().getString(R.string.timer_start_value, millisUntilFinished / 1000));
            }

            public void onFinish() {
                tvTimer.setVisibility(View.INVISIBLE);
                buttonResendCode.setEnabled(true);
            }
        };
        cTimer.start();
    }


    public boolean codeIsAccepted(String input) {
        String realCode = "1-1-1-1"; //TODO: should be changed to the actual code from SMS
        return input.equals(realCode);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms_code);

        tvFail = findViewById(R.id.text_incorrect);
        editSmsCode = findViewById(R.id.editTextPhone);
        buttonEnter = findViewById(R.id.button);
        buttonResendCode = findViewById(R.id.button_resend);
        tvTimer = findViewById(R.id.timer);

        String phoneNo = getIntent().getStringExtra("phoneNo");


        editSmsCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //enter button can't be enabled and error message shouldn't be shown before "RESEND CODE" button is clicked
                if (enterCanBeEnabled) {
                    String input = charSequence.toString();

                    if (!input.contains("X")) {
                        //all 4 digits were entered
                        codeFormatIsCorrect = true;
                    } else {
                        //less that 4 digits were entered
                        codeFormatIsCorrect = false;
                    }

                    if (codeFormatIsCorrect) {
                        buttonEnter.setEnabled(true);
                    } else {
                        buttonEnter.setEnabled(false);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });


        buttonEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (codeIsAccepted(editSmsCode.getText().toString())) {
                    Intent shopsActivityIntent = new Intent(SmsCodeActivity.this, ListOfShopsActivity.class);
                    startActivity(shopsActivityIntent);
                } else {
                    enterCanBeEnabled = false;
                    buttonEnter.setEnabled(false);
                    tvFail.setVisibility(View.VISIBLE);
                    startTimer();
                }

            }
        });

        buttonResendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enterCanBeEnabled = true;
                editSmsCode.getText().clear();
                buttonResendCode.setEnabled(false);
                tvFail.setVisibility(View.INVISIBLE);
            }
        });
    }


}
