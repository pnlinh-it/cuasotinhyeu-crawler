package com.pnlinh.cuasotinhyeu.crawler.task;

import java.io.*;
import java.util.concurrent.Callable;

public class CopyTask implements Runnable {
    @Override
    public void run() {
        try {
            System.err.println(String.format("run: Start"));
            InputStream inputStream = new BufferedInputStream(new FileInputStream("D:\\6.DVD.hoc.lam.giau [Ky.nang.mem].rar"));
            OutputStream outputStream = new BufferedOutputStream(new FileOutputStream("test.iso"));

            byte[] buffer = new byte[1024];
            while (inputStream.read(buffer) > 0) {
                if (Thread.currentThread().isInterrupted()) {
                    System.err.println(String.format("run: Interrupted"));
                    break;
                }
                outputStream.write(buffer);
                outputStream.flush();

                if (Thread.currentThread().isInterrupted()) {
                    System.err.println(String.format("run: Interrupted"));
                    break;
                }
            }

            System.err.println(String.format("run: Completed"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
