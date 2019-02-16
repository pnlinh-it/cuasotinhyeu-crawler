package com.pnlinh.cuasotinhyeu.crawler.log.fetch;

public class ContentEmptyLog extends BaseLog {

    public ContentEmptyLog(String page) {
        super(page);
    }

    @Override
    public String format() {
        return String.format("%s - Empty", page);
    }
    @Override
    public boolean isSuccess() {
        return false;
    }
}
