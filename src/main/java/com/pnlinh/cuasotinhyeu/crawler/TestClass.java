package com.pnlinh.cuasotinhyeu.crawler;

import com.pnlinh.cuasotinhyeu.crawler.task.WriteToFileTask;

import java.util.concurrent.*;


public class TestClass extends Thread {
    ExecutorService writeFileExecutor = Executors.newSingleThreadExecutor();

    @Override
    public void run() {
        Future<String> stringFuture = writeFileExecutor.submit(new WriteToFileTask("data"));
        writeFileExecutor.shutdown();
        try {
            String s = stringFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } catch (CancellationException e) {
            int a = 1;
        }
    }
}
