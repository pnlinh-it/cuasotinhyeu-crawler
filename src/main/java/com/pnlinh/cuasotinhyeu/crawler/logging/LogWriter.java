package com.pnlinh.cuasotinhyeu.crawler.logging;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class LogWriter {
    Writer mSuccessWriter;
    Writer mErrorWriter;

    public LogWriter() {
        try {
            mSuccessWriter = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream("success.txt", true), StandardCharsets.UTF_8));
            mErrorWriter = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream("error.txt", true), StandardCharsets.UTF_8));

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void writeLine(Log log) {
        if (log.isSuccess())
            writeLine(mSuccessWriter, log);
        else
            writeLine(mErrorWriter, log);
    }

    private void writeLine(Writer writer, Log log) {
        try {
            writer.write(log.format() + "\n");
            writer.flush();
        } catch (IOException ex) {
            System.err.println(String.format("writeLine: %s", log.format()));
        }
    }
}
