package com.example.ubercoffee;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    private SmsCodeActivity smsCodeActivity = new SmsCodeActivity();
    private MainActivity mainActivity = new MainActivity();

    @Test
    public void code_accepted_correctly(){
        assertTrue(smsCodeActivity.isCodeAccepted("1-1-1-1", "1-1-1-1"));
        assertTrue(smsCodeActivity.isCodeAccepted("1-1-2-1", "1-1-2-1"));
    }

    @Test
    public void code_declined_correctly(){
        assertFalse(smsCodeActivity.isCodeAccepted("3-1-1-1", "1-1-1-1"));
        assertFalse(smsCodeActivity.isCodeAccepted("3-2-1-1", "1-1-2-1"));
    }

    @Test
    public void code_format_accepted_correctly(){
        assertTrue(smsCodeActivity.isCodeFormatCorrect("1-2-3-4"));
        assertTrue(smsCodeActivity.isCodeFormatCorrect("1-1-1-1"));
    }

    @Test
    public void code_format_declined_correctly(){
        assertFalse(smsCodeActivity.isCodeFormatCorrect("1-2-3-X"));
        assertFalse(smsCodeActivity.isCodeFormatCorrect("X-X-X-X"));
    }

    @Test
    public void phone_format_accepted_correctly(){
        assertTrue(mainActivity.isPhoneNumberCorrect("+7-921-111-11-11"));
        assertTrue(mainActivity.isPhoneNumberCorrect("+0-921-111-11-11"));
    }

    @Test
    public void phone_format_declined_correctly(){
        assertFalse(mainActivity.isPhoneNumberCorrect("+7-921-111-11-XX"));
        assertFalse(mainActivity.isPhoneNumberCorrect("+X-XXX-XXX-XX-XX"));
    }

}