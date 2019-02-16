package com.pnlinh.facebook.task;

import com.pnlinh.facebook.FacebookService;
import com.pnlinh.facebook.Main;
import com.pnlinh.facebook.StringUtils;
import com.pnlinh.facebook.model.BaseResponse;
import com.pnlinh.facebook.model.Feed;
import com.pnlinh.facebook.model.Reaction;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;
import java.util.List;

public class GetLikesTask implements Runnable {
    private Feed mFeed;

    public GetLikesTask(Feed mFeed) {
        this.mFeed = mFeed;
    }

    @Override
    public void run() {
        FacebookService service = Main.getApiService();
        try {
            Response<BaseResponse<List<Reaction>>> response = service.reactions(mFeed.id).execute();
            if (response.body() == null)
                return;

            List<Reaction> reactions = response.body().data;
            if (reactions == null)
                return;

            for (Reaction reaction : reactions) {
                if (StringUtils.removeAccent(reaction.name).toLowerCase().contains("van"))
                    System.err.println(String.format("Found: %s %s", reaction.name, reaction.id));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
