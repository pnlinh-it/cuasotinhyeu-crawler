package com.pnlinh.cuasotinhyeu.crawler.log.fetch;

import com.pnlinh.cuasotinhyeu.crawler.logging.Log;

public abstract class BaseLog implements Log {
    protected String page;

    public BaseLog(String page) {
        this.page = page;
    }
}
