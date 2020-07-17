package com.example.ubercoffee;

import com.google.gson.annotations.SerializedName;

public class RegistrationAnswer {
    @SerializedName("phoneNumber")
    private String phoneNumber;
    @SerializedName("sessionID")
    private  String sessionId;
    @SerializedName("verifyCode")
    private String verifyCode;

    public void setSessionId(String sessionId_){
        sessionId = sessionId_;
    }

    public void setPhoneNumber(String phoneNumber_){
        phoneNumber = phoneNumber_;
    }

    public void setVeryfiCode(String verifyCode_){
        verifyCode = verifyCode_;
    }

    public String getSessionId(){
        return sessionId;
    }

    public  String getPhoneNumber(){
        return phoneNumber;
    }

    public String getVeryfiCode(){
        return verifyCode;
    }
}
