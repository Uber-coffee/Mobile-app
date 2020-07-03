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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class SmsCodeActivity extends AppCompatActivity {

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


    public boolean isCodeAccepted(String input, String realCode) {
        //TODO: realCode = "1-1-1-1", should be changed to the actual code from SMS
        return input.equals(realCode);
    }

    public boolean isCodeFormatCorrect(String code) {
        /*
         input contains "X" - not enough digits were entered
         input contains no "X" - full code was entered
         */
        return !code.contains("X");
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

        sendVerificationCodeToUser(phoneNo);


//        editSmsCode.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //enter button can't be enabled and error message shouldn't be shown before "RESEND CODE" button is clicked
                if (enterCanBeEnabled) {
                    if (isCodeFormatCorrect(charSequence.toString())) {
                        buttonEnter.setEnabled(true);
                    } else {
                        buttonEnter.setEnabled(false);
                    }
                }
            }
//            @Override
//            public void afterTextChanged(Editable editable) {
//            }
//        });
//
//        buttonEnter.setOnClickListener(new View.OnClickListener() {
//            @Override
//                if (codeIsAccepted(editSmsCode.getText().toString())) {
//            public void onClick(View view) {
//                    Intent shopsActivityIntent = new Intent(SmsCodeActivity.this, ListOfShopsActivity.class);
//                    startActivity(shopsActivityIntent);
//                } else {
//                    buttonEnter.setEnabled(false);
//                    enterCanBeEnabled = false;
//                    tvFail.setVisibility(View.VISIBLE);
//                }
//                    startTimer();
//
//            }
//        });
//        buttonResendCode.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                enterCanBeEnabled = true;
//                buttonResendCode.setEnabled(false);
//                editSmsCode.getText().clear();
//                tvFail.setVisibility(View.INVISIBLE);
//            }
//        });
    }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);

            verificationCodeBySystem = s;
        }

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            if(code!=null){
                verifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(SmsCodeActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    };

        buttonEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isCodeAccepted(editSmsCode.getText().toString(), "1-1-1-1")) { //TODO: insert the actual code from SMS to the "realCode"
                    Intent shopsActivityIntent = new Intent(SmsCodeActivity.this, ListOfShopsActivity.class);
                    startActivity(shopsActivityIntent);
                } else {
                    enterCanBeEnabled = false;
                    buttonEnter.setEnabled(false);
                    tvFail.setVisibility(View.VISIBLE);
                    startTimer();
                }
    private void verifyCode(String codeByUser){

        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationCodeBySystem, codeByUser);
        signInTheUserByCredentials(credential);

    }

    private void signInTheUserByCredentials(PhoneAuthCredential credential) {

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(SmsCodeActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Intent shopsActivityIntent = new Intent(SmsCodeActivity.this, ListOfShopsActivity.class);
                            shopsActivityIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(shopsActivityIntent);
                        } else {
                            tvFail.setVisibility(View.VISIBLE);
                            startTimer();
                        }
                    }
                });

    }


}
