package com.example.ubercoffee;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    TextView tvSuccess;
    TextView tvFail;
    EditText editPhone;
    Button buttonEnter;
    CheckBox checkBox;

    boolean phoneNumberIsCorrect = false;

    final int DIALOG_NO_INTERNET = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvSuccess = findViewById(R.id.text_correct);
        tvFail = findViewById(R.id.text_incorrect);
        editPhone = findViewById(R.id.editTextPhone);
        buttonEnter = findViewById(R.id.button);
        checkBox = findViewById(R.id.checkBox2);


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
                    tvFail.setVisibility(View.INVISIBLE);

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
                        tvSuccess.setVisibility(View.VISIBLE);
                        if (checkBox.isChecked()) {
                            buttonEnter.setEnabled(true);
                        }

                    } else {
                        buttonEnter.setEnabled(false);
                        tvFail.setVisibility(View.VISIBLE);
                        tvSuccess.setVisibility(View.INVISIBLE);
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
                if(isOnline()){
                    String str = parseNum(editPhone.getText().toString());
                    Intent smsActivityIntent = new Intent(MainActivity.this, SmsCodeActivity.class);
                    smsActivityIntent.putExtra("key", str);
                    startActivity(smsActivityIntent);
                }else{
                    showDialog(DIALOG_NO_INTERNET);
                }
            }
        });


    }

    private String parseNum(String inputNum){
        String str = new String(inputNum.replaceAll("[-()]",""));
        return str;
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if(id == DIALOG_NO_INTERNET){
            AlertDialog.Builder adb = new AlertDialog.Builder(this);
            adb.setTitle("No Internet!");
            adb.setMessage("We can't connect the internet, please check your internet connection!");
            adb.setPositiveButton("OK", myClickListener);
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
            }
        }
    };

    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null && cm.getActiveNetworkInfo() == null) {
            return false;
        } else {
            return true;
        }
    }
}