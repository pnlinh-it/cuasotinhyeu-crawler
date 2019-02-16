package com.pnlinh.cuasotinhyeu.crawler;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadStartTest {

    public static void main(String[] args) {
        int count = 5;

        CountDownLatch latch = new CountDownLatch(count);
        CyclicBarrier barrier = new CyclicBarrier(count);
        ExecutorService pool = Executors.newFixedThreadPool(count);

        Random random = new Random();
        for (int i = 0; i < count; i++) {
            pool.execute(() -> {
                try {
                    System.out.println("Thread starts");
                    // startup
                    Thread.sleep(random.nextInt(100));
                    // wait for other threads
                    System.out.println("Thread waits");
                    barrier.await();
                    // process whaterver should be processed
                    System.out.println("Thread works");
                    Thread.sleep(random.nextInt(1000));
                    System.out.println("Thread finished");
                } catch (Exception e) {
                    System.err.println("Worker thread inrerrupted");
                } finally {
                    latch.countDown();
                }
            });
        }

        try {
            // wait for the threads to be done
            latch.await();
            System.out.println("== End == ");
        } catch (InterruptedException e) {
            System.err.println("Starting inrerrupted");
        }
        pool.shutdown();
    }

}