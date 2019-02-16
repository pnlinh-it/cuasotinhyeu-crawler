package com.pnlinh.cuasotinhyeu.crawler.logging;

public class LoggingManager {
    private volatile static LoggingManager INSTANCE;

    private LoggingThread mLoggingThread;

    public static LoggingManager getInstance() {
        if (INSTANCE == null)
            synchronized (LoggingManager.class) {
                if (INSTANCE == null)
                    INSTANCE = new LoggingManager();
            }
        return INSTANCE;
    }

    private LoggingManager() {
        mLoggingThread = new LoggingThread();
        mLoggingThread.start();
    }

    public void log(Log o) {
        mLoggingThread.log(o);
    }

}
