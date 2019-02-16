package com.pnlinh.cuasotinhyeu.crawler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

public class TestHashMap {
    static ConcurrentHashMap<Integer, List<String>> map = new ConcurrentHashMap<>();

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 20; i++) {
            new Thread(new TestRunnable()).start();
        }
        Thread.sleep(3000);
        int a = 1;
    }

    private static class TestRunnable implements Runnable {

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    map.computeIfAbsent(i, new Function<Integer, List<String>>() {
                        @Override
                        public List<String> apply(Integer integer) {
                            return new ArrayList<>();
                        }
                    }).add(String.valueOf(j) + Thread.currentThread().getName());
                }
            }
        }
    }
}
