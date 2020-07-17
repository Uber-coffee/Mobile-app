package com.example.ubercoffee;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Json {
    @POST("api/auth/m/auth/signup")
    Call<ResponseBody> postData(@Body RegistrationAnswer data);

    @GET("api/trade-point/m/trade-points/nearest")
    Call<ResponseBody> getTrade(@Query("longitude") double longitude, @Query("latitude") double latitude);
}
