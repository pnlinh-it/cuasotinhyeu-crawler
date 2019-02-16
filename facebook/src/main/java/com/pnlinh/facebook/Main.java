package com.pnlinh.facebook;

import com.pnlinh.facebook.model.BaseResponse;
import com.pnlinh.facebook.model.Feed;
import okhttp3.*;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        FetchManager.getInstance().start();
    }

    private static FacebookService mFacebookService;

    public static FacebookService getApiService() {
        if (mFacebookService == null) {
            synchronized (Main.class) {
                if (mFacebookService == null)
                    mFacebookService = createApiService();
            }
        }
        return mFacebookService;
    }

    private static final String ACCESS_TOKEN = "EAAAAUaZA8jlABAJkXbn9w6jZARi6JHDLhpEA4ZA7vaENZA1Kb8hm9ti25BOWDomxt7Bqzlf6dBdWByClQpanR5vjU0zTK53igU283ZAsS397v2LWzvweCuIGTwoXY8fMEF6AMyZCNCw2WMzwGCLmkn2uWxpBRsXEqXmrCR5TeZAMHqpGOJO6dx0Ec1pP413MVMZD";

    private static FacebookService createApiService() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(System.err::println);
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);

        OkHttpClient client = new OkHttpClient.Builder()
               // .addInterceptor(logging)
                .addNetworkInterceptor(chain -> {
                    HttpUrl url = chain.request().url()
                            .newBuilder()
                            .addQueryParameter("access_token", ACCESS_TOKEN)
                            .build();
                    Request request = chain.request().newBuilder().url(url).build();
                    return chain.proceed(request);
                })
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://graph.facebook.com/v3.2/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        return retrofit.create(FacebookService.class);
    }
}
