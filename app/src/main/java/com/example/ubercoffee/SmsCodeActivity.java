package com.example.ubercoffee;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SmsCodeActivity extends AppCompatActivity {

    boolean codeFormatIsCorrect = false;


    TextView tvFail;
    ImageView ivFail;
    EditText editSmsCode;
    Button buttonEnter;
    Button buttonResendCode;

    public boolean codeIsAccepted(String input) {
        String realCode = "1-1-1-1"; //TODO: should be changed to the actual code from SMS
        return input.equals(realCode);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms_code);

        tvFail = findViewById(R.id.text_incorrect);
        ivFail = findViewById(R.id.image_fail);
        editSmsCode = findViewById(R.id.editTextPhone);
        buttonEnter = findViewById(R.id.button);
        buttonResendCode = findViewById(R.id.button_resend);

        //buttonEnter.setEnabled(false);

        tvFail.setVisibility(View.INVISIBLE);
        ivFail.setVisibility(View.INVISIBLE);


        editSmsCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
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

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });


        buttonEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (codeIsAccepted(editSmsCode.getText().toString())){
                    Intent shopsActivityIntent = new Intent(SmsCodeActivity.this, ListOfShopsActivity.class);
                    startActivity(shopsActivityIntent);
                } else {
                    buttonEnter.setEnabled(false);
                    tvFail.setVisibility(View.VISIBLE);
                    ivFail.setVisibility(View.VISIBLE);
                    //TODO: start a 1-min timer
                    //TODO: enable resend button after time is up
                    //TODO: after resend button was clicked it should be disabled and enter button should become enabled
                    //TODO: hide error message
                }

            }
        });
    }


}
