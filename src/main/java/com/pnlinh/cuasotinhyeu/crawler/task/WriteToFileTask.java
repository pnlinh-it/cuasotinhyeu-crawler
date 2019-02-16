package com.pnlinh.cuasotinhyeu.crawler.task;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.concurrent.Callable;

public class WriteToFileTask implements Callable<String> {

    private String data;
    private Writer mWriter;

    public WriteToFileTask(String data, Writer mWriter) {
        this.data = data;
        this.mWriter = mWriter;
    }

    public WriteToFileTask(String data) {
        this.data = data;
    }

    @Override
    public String call() throws FileNotFoundException {

        OutputStream outputStream = new BufferedOutputStream(new FileOutputStream("out.txt"));

        return null;
    }
}
