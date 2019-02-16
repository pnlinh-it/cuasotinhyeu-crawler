package com.pnlinh.facebook;

import com.pnlinh.facebook.model.BaseResponse;
import com.pnlinh.facebook.model.Feed;
import com.pnlinh.facebook.model.Reaction;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

import java.util.List;

public interface FacebookService {
    @GET("{uid}/feed?limit=5000")
    Call<BaseResponse<List<Feed>>> feeds(@Path("uid") String uid);

    @GET("{fid}/reactions?limit=5000")
    Call<BaseResponse<List<Reaction>>> reactions(@Path("fid") String fid);
}
