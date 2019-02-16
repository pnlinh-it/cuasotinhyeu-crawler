package com.pnlinh.cuasotinhyeu.crawler.log.fetch;

public class ErrorLog extends BaseLog {
    private Exception exception;

    public ErrorLog(String page, Exception exception) {
        super(page);
        this.exception = exception;
    }

    @Override
    public String format() {
        return String.format("%s - %s", page, exception == null ? null : exception.getMessage());
    }

    @Override
    public boolean isSuccess() {
        return false;
    }
}
