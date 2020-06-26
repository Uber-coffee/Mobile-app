package com.example.ubercoffee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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
        for (int i = 0; i < sequence.length(); i++){
            if (!Character.isDigit(sequence.charAt(i))){
                return false;
            }
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvSuccess = (TextView) findViewById(R.id.text_correct);
        ivSuccess = (ImageView) findViewById(R.id.image_success);
        tvFail = (TextView) findViewById(R.id.text_incorrect);
        ivFail = (ImageView) findViewById(R.id.image_fail);
        editPhone = (EditText) findViewById(R.id.editTextPhone);
        buttonEnter = (Button) findViewById(R.id.button);
        checkBox = (CheckBox) findViewById(R.id.checkBox2);


        //initially there should be no messages on the screen
        tvSuccess.setVisibility(View.INVISIBLE);
        ivSuccess.setVisibility(View.INVISIBLE);
        tvFail.setVisibility(View.INVISIBLE);
        ivFail.setVisibility(View.INVISIBLE);


        //here we show a right message when it's needed
        editPhone.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                CharSequence input = textView.getText();

                if (input.length() == 0){
                    //initially there should be no messages on the screen
                    tvSuccess.setVisibility(View.INVISIBLE);
                    ivSuccess.setVisibility(View.INVISIBLE);
                    tvFail.setVisibility(View.INVISIBLE);
                    ivFail.setVisibility(View.INVISIBLE);
                } else if (input.charAt(0) != '+' || input.charAt(1) != '7'){
                    //Phone number should start with '+'
                    tvFail.setVisibility(View.VISIBLE);
                    ivFail.setVisibility(View.VISIBLE);
                } else if (input.length() == 12 && allDigits(input.subSequence(2, input.length()))) {
                    tvFail.setVisibility(View.INVISIBLE);
                    ivFail.setVisibility(View.INVISIBLE);
                    tvSuccess.setVisibility(View.VISIBLE);
                    ivSuccess.setVisibility(View.VISIBLE);
                    phoneNumberIsCorrect = true;
                }
                return true;
            }
        });

        buttonEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (phoneNumberIsCorrect && checkBox.isChecked()) {
                    Intent smsActivityIntent = new Intent(MainActivity.this, SmsCodeActivity.class);
                    startActivity(smsActivityIntent);
                }
            }
        });




    }
}