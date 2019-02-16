package com.pnlinh.cuasotinhyeu.crawler.logging;

import java.util.ArrayDeque;
import java.util.Queue;

class LoggingThread extends Thread {
    private volatile boolean isRunning = true;
    private Queue<Log> queue = new ArrayDeque<>();
    private final Object LOCK = new Object();
    private LogWriter logWriter = new LogWriter();

    public void log(Log log) {
        synchronized (LOCK) {
            queue.add(log);
            LOCK.notify();
        }
    }

    @Override
    public void run() {
        Log log;
        while (isRunning && !isInterrupted()) {
            synchronized (LOCK) {
                while (queue.size() == 0) {
                    try {
                        LOCK.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                log = queue.poll();
            }
            if (log != null) {
                logWriter.writeLine(log);
            }
        }
    }

    public void stopLog() {
        isRunning = false;
        interrupt();
    }



}
