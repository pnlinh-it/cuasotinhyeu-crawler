package com.pnlinh.cuasotinhyeu.crawler.fetch.task;

import com.pnlinh.cuasotinhyeu.crawler.db.DbOperations;
import com.pnlinh.cuasotinhyeu.crawler.fetch.FetchManager;
import com.pnlinh.cuasotinhyeu.crawler.model.Post;

public class SaveToDBTask implements Runnable {
    private Post post;

    public SaveToDBTask(Post post) {
        this.post = post;
    }

    @Override
    public void run() {
        FetchManager fetchManager = FetchManager.getInstance();
        DbOperations.createRecord(post);
        fetchManager.report(post);
    }
}
