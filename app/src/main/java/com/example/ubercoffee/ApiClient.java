package com.example.ubercoffee;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {


    public static final String BASE_URL = "http://ecse005008ef.epam.com:8080/";
    private static Retrofit retrofit = null;
    private static Retrofit retrofit1 = null;



    public static Retrofit getClientForReg() {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);


        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(300, TimeUnit.SECONDS)
                .connectTimeout(300, TimeUnit.SECONDS)
                .addInterceptor(logging)
                .build();


        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static Retrofit getClientForTrade(String str) {

        final  String mToken = str;

        Interceptor mTokenInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                if (mToken != null) {
                    Request.Builder requestBuilder = request.newBuilder()
                            .addHeader("Authorization", mToken);
                    Request newRequest = requestBuilder.build();

                    return chain.proceed(newRequest);
                }
                return chain.proceed(request);

            }
        };

        OkHttpClient client = new OkHttpClient.Builder()
                .addNetworkInterceptor(mTokenInterceptor)
                .build();


        if (retrofit1 == null) {
            retrofit1 = new Retrofit.Builder()
                    .client(client)
                    .baseUrl(BASE_URL)
                    .build();
        }
        return retrofit1;
    }
}