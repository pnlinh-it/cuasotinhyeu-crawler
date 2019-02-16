package com.pnlinh.cuasotinhyeu.crawler.main;

import com.pnlinh.cuasotinhyeu.crawler.db.DbOperations;
import com.pnlinh.cuasotinhyeu.crawler.fetch.FetchManager;
import com.pnlinh.cuasotinhyeu.crawler.task.CopyTask;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.hibernate.Session;
import org.hibernate.Transaction;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Main {
    private static ApiService mApiService;

    public static ApiService getApiService() {
        if (mApiService == null) {
            synchronized (Main.class) {
                if (mApiService == null)
                    mApiService = createApiService();
            }
        }
        return mApiService;
    }

    private static ApiService createApiService() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(System.err::println);
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        HttpLoggingInterceptor logging2 = new HttpLoggingInterceptor(System.err::println);
        logging2.setLevel(HttpLoggingInterceptor.Level.BASIC);

        OkHttpClient client = new OkHttpClient.Builder()
                //.addInterceptor(logging2)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.cuasotinhyeu.vn/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .client(client)
                .build();
        return retrofit.create(ApiService.class);
    }

    private static CountDownLatch countDownLatch = new CountDownLatch(1);

    public static void main(String[] args) throws InterruptedException {
        /*
            int  i =3;

            //increment i, but CAN NOT use new value, increment AFTER the current expression is execute
            //a = i then i = i + 1
            int a = i++; //a =3

            //increment i, but CAN use new value, increment BEFORE the current expression is execute
            //i = i + 1  then a = i
            int a = ++i; //a =4

            if(i++ == 4) //false
            if(++i == 4) //true
        */
        //FetchManager.getInstance().start();
        List<Callable<Object>> task = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            task.add(Executors.callable(new TestRunnable(i)));
        }
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        //Future<String> taskCallable = executorService.submit(new TestCallable());

        Future<?> taskCallable2 = executorService.submit(new CopyTask());

        /*new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    Thread.sleep(1000);
                    System.err.println(String.format("run: %s", taskCallable2.cancel(true)));

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();*/
        // List<Runnable> runnables = executorService.shutdownNow(); //interrupted all thread
       /* try {

            taskCallable2.get();
        } catch (ExecutionException e) {
            System.err.println(String.format("Execute exception: %s", e.getMessage()));
            e.printStackTrace();
        } catch (CancellationException e) {
            System.err.println(String.format("Cancel exception: %s", e.getMessage()));
        }*/


        //Thread.sleep(1000 * 1000);
       // System.err.println(String.format("main: before invokeAll"));
       // executorService.invokeAll(task);
      //  System.err.println(String.format("main: after invokeAll"));
    }

    private static class TestCallable implements Callable<String> {

        @Override
        public String call() throws Exception {
            System.err.println(String.format("Start Test Task"));
            Thread.sleep(2 * 1000);

            throw new NullPointerException("Hallooooo Exception");
        }
    }

    private static class TestRunnable implements Runnable {
        int i;

        public TestRunnable(int i) {
            this.i = i;
        }

        @Override
        public void run() {
            try {
                System.err.println(String.format("Start: %s", i));
                Thread.sleep(3 * 1000);
                System.err.println(String.format("Completed: %s", i));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}
