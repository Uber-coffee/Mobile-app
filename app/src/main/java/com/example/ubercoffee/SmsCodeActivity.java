package com.example.ubercoffee;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SmsCodeActivity extends AppCompatActivity {

    boolean codeFormatIsCorrect = false;
    boolean enterCanBeEnabled = true;

    String num = null;

    TextView tvFail;
    EditText editSmsCode;
    Button buttonEnter;
    Button buttonResendCode;
    TextView tvTimer;

    RegistrationAnswer rg;
    String realCode;

    String error;

    final int DIALOG_GOOD_ENTRY = 1;
    final int DIALOG_BAD_PHONE = 2;
    final int DIALOG_SOMETHING_WRONG = 3;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms_code);

        Bundle arguments = getIntent().getExtras();



        if(arguments!=null){
            num = arguments.get("key").toString();
        }

        rg = new RegistrationAnswer();
        rg.setPhoneNumber(num);
        rg.setSessionId("empty");
        rg.setVeryfiCode("empty");
        System.out.println(rg.getPhoneNumber());


        tvFail = findViewById(R.id.text_incorrect);
        editSmsCode = findViewById(R.id.editTextPhone);
        buttonEnter = findViewById(R.id.button);
        buttonResendCode = findViewById(R.id.button_resend);
        tvTimer = findViewById(R.id.timer);


        editSmsCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (enterCanBeEnabled) {
                    String input = charSequence.toString();

                    if (!input.contains("X")) {
                        codeFormatIsCorrect = true;
                    } else {
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

                Retrofit retrofit = ApiClient.getClientForReg();

                Json service = retrofit.create(Json.class);

                Call<ResponseBody> call1 = service.postData(rg);
                call1.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                        if(response.code() == 201){
                            String phone = getIntent().getStringExtra("key");
                            System.out.println("Congrad");
                            Intent shopsActivityIntent = new Intent(SmsCodeActivity.this, ListOfShopsActivity.class);
                            shopsActivityIntent.putExtra("phone_number", phone);
                            startActivity(shopsActivityIntent);

                            SharedPreferences sharedPreferences = getSharedPreferences("forAuthorization",MODE_PRIVATE);
                            SharedPreferences.Editor edit = sharedPreferences.edit();
                            edit.putString("Authorization", response.headers().get("Authorization"));
                            edit.putString("Refresh-token", response.headers().get("Refresh-token")).apply();
                        }else {
                            enterCanBeEnabled = false;
                            buttonEnter.setEnabled(false);
                            tvFail.setVisibility(View.VISIBLE);
                            startTimer();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        enterCanBeEnabled = false;
                        buttonEnter.setEnabled(false);
                        tvFail.setVisibility(View.VISIBLE);
                        startTimer();
                    }
                });
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

        Thread thread = new Thread(new Runnable() {
            @Override public void run() {

                Retrofit retrofit = ApiClient.getClientForReg();

                Json service = retrofit.create(Json.class);
                Call<ResponseBody> call = service.postData(rg);
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                        if(response.isSuccessful() || response.code() == 202){
                            rg.setSessionId(response.headers().get("session_id"));
                            generateCode(rg.getPhoneNumber().substring(rg.getPhoneNumber().length() - 4));
                            rg.setVeryfiCode(rg.getPhoneNumber().substring(rg.getPhoneNumber().length() - 4));
                            showDialog(DIALOG_GOOD_ENTRY);
                        }else {
                            showDialog(DIALOG_BAD_PHONE);
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        error = t.toString();
                        showDialog(DIALOG_SOMETHING_WRONG);
                    }
                });
            }});
        thread.start();
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if(id == DIALOG_GOOD_ENTRY){
            AlertDialog.Builder adb = new AlertDialog.Builder(this, R.style.AlertDialogTheme);
            adb.setTitle("Congratulations!");
            adb.setMessage("Your code is -> " + realCode);
            adb.setPositiveButton("OK", myClickListener);
            return adb.create();
        }
        if(id == DIALOG_BAD_PHONE){
            AlertDialog.Builder adb = new AlertDialog.Builder(this, R.style.AlertDialogTheme);
            adb.setTitle("BAD PHONE NUMBER");
            adb.setMessage("We can't sent the code for you, please check input data!");
            adb.setNegativeButton("OK", myClickListener);
            return adb.create();
        }
        if(id == DIALOG_SOMETHING_WRONG){
            AlertDialog.Builder adb = new AlertDialog.Builder(this, R.style.AlertDialogTheme);
            adb.setTitle("Something Wrong!");
            adb.setMessage("We can't sent the code for you, because " + error + " please send email for support team!");
            adb.setNegativeButton("OK", myClickListener);
            return adb.create();
        }
        return super.onCreateDialog(id);
    }

    DialogInterface.OnClickListener myClickListener = new DialogInterface.OnClickListener() {

        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which){
                case Dialog.BUTTON_POSITIVE:
                    break;
                case  DialogInterface.BUTTON_NEGATIVE:
                    finish();
                    break;
            }
        }
    };

    private void generateCode(String code){
        StringBuffer stringBuffer = new StringBuffer(code);
        stringBuffer.insert(1,"-");
        stringBuffer.insert(3,"-");
        stringBuffer.insert(5,"-");
        realCode = stringBuffer.toString();
    }
}
