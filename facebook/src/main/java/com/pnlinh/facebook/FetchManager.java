package com.pnlinh.facebook;

import com.pnlinh.facebook.model.BaseResponse;
import com.pnlinh.facebook.model.Feed;
import com.pnlinh.facebook.task.GetLikesTask;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class FetchManager {

    private static final int NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors();

    private volatile static FetchManager INSTANCE;

    private static BlockingQueue<Runnable> mListQueue = new LinkedBlockingQueue<>();
    private ThreadPoolExecutor mListExecutor = new ThreadPoolExecutor(NUMBER_OF_CORES * 2, NUMBER_OF_CORES * 2, 5, TimeUnit.SECONDS, mListQueue);


    public static FetchManager getInstance() {
        if (INSTANCE == null)
            synchronized (FetchManager.class) {
                if (INSTANCE == null)
                    INSTANCE = new FetchManager();
            }
        return INSTANCE;
    }

    public void start() {
        FacebookService service = Main.getApiService();
        service.feeds("100009360755726").enqueue(new Callback<BaseResponse<List<Feed>>>() {
            @Override
            public void onResponse(Call<BaseResponse<List<Feed>>> call, Response<BaseResponse<List<Feed>>> response) {
                for (Feed feed : response.body().data) {
                    mListExecutor.execute(new GetLikesTask(feed));
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<List<Feed>>> call, Throwable t) {

            }
        });
    }

}
