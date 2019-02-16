package com.pnlinh.cuasotinhyeu.crawler.log.fetch;

import com.pnlinh.cuasotinhyeu.crawler.model.Post;

public class SuccessLog extends BaseLog {
    private Post post;

    public SuccessLog(String page, Post post) {
        super(page);
        this.post = post;
    }

    @Override
    public String format() {
        return String.format("%s - %s - %s", page, post.getTitle(), post.getUrl());
    }

    @Override
    public boolean isSuccess() {
        return true;
    }
}
