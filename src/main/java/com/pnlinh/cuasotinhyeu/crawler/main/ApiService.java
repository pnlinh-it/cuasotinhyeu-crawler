package com.pnlinh.cuasotinhyeu.crawler.main;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface ApiService {

    @GET("tu-van/tinh-yeu")
    Call<String> getList(@Query("page") int page);

    @GET
    Call<String> getDetail(@Url String url);
}
