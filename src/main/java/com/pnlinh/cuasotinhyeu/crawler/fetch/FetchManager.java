package com.pnlinh.cuasotinhyeu.crawler.fetch;

import com.pnlinh.cuasotinhyeu.crawler.fetch.task.FetchDetailTask;
import com.pnlinh.cuasotinhyeu.crawler.fetch.task.FetchListTask;
import com.pnlinh.cuasotinhyeu.crawler.fetch.task.SaveToDBTask;
import com.pnlinh.cuasotinhyeu.crawler.model.Post;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class FetchManager {

    private static final int NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors();

    private volatile static FetchManager INSTANCE;

    private static BlockingQueue<Runnable> mListQueue = new LinkedBlockingQueue<>();
    private static BlockingQueue<Runnable> mDetailQueue = new LinkedBlockingQueue<>();
    private static BlockingQueue<Runnable> mPostQueue = new LinkedBlockingQueue<>();

    private ThreadPoolExecutor mListExecutor = new ThreadPoolExecutor(NUMBER_OF_CORES * 2, NUMBER_OF_CORES * 2, 5, TimeUnit.SECONDS, mListQueue);
    private ThreadPoolExecutor mDetailExecutor = new ThreadPoolExecutor(NUMBER_OF_CORES * 2, NUMBER_OF_CORES * 2, 5, TimeUnit.SECONDS, mDetailQueue);
    private Executor mPostExecutor = Executors.newFixedThreadPool(15);
    private AtomicInteger mCount = new AtomicInteger(0);

    public static FetchManager getInstance() {
        if (INSTANCE == null)
            synchronized (FetchManager.class) {
                if (INSTANCE == null)
                    INSTANCE = new FetchManager();
            }
        return INSTANCE;
    }

    public void start() {
        for (int i = 0; i < 390; i++) {
            mListExecutor.execute(new FetchListTask(i));
        }
    }

    public void getDetail(Post post) {
        mDetailExecutor.execute(new FetchDetailTask(post));
    }

    public void report(Post post) {
        int count = mCount.incrementAndGet();
        System.err.println(String.format("report: %s %s", count, post.getUrl()));
    }

    public void saveDb(Post post) {
        mPostExecutor.execute(new SaveToDBTask(post));
    }
}
