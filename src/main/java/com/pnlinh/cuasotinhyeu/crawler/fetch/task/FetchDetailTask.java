package com.pnlinh.cuasotinhyeu.crawler.fetch.task;

import com.pnlinh.cuasotinhyeu.crawler.db.DbOperations;
import com.pnlinh.cuasotinhyeu.crawler.fetch.FetchManager;
import com.pnlinh.cuasotinhyeu.crawler.log.fetch.ContentEmptyLog;
import com.pnlinh.cuasotinhyeu.crawler.log.fetch.ErrorLog;
import com.pnlinh.cuasotinhyeu.crawler.logging.LoggingManager;
import com.pnlinh.cuasotinhyeu.crawler.main.ApiService;
import com.pnlinh.cuasotinhyeu.crawler.main.Main;
import com.pnlinh.cuasotinhyeu.crawler.model.Post;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import retrofit2.Response;

import java.io.IOException;

public class FetchDetailTask implements Runnable {
    private Post post;

    public FetchDetailTask(Post post) {
        this.post = post;
    }

    @Override
    public void run() {
        ApiService apiService = Main.getApiService();
        LoggingManager manager = LoggingManager.getInstance();
        FetchManager fetchManager = FetchManager.getInstance();
        try {
            Response<String> response = apiService.getDetail(post.getUrl()).execute();
            String htmlContent = response.body();
            Document doc = Jsoup.parse(htmlContent);
            Element questionElement = doc.select("div.field-name-field-cau-hoi div.field-item").first();
            if (questionElement == null) {
                manager.log(new ContentEmptyLog(post.getUrl()));
                return;
            }
            post.setQuestion(questionElement.html());

            Element answerElement = doc.select("div.field-name-field-cau-tra-loi div.field-item").first();
            if (answerElement == null) {
                manager.log(new ContentEmptyLog(post.getUrl()));
                return;
            }
            post.setAnswer(answerElement.html());

            fetchManager.saveDb(post);
        } catch (IOException ex) {
            manager.log(new ErrorLog(post.getUrl(), ex));
        }

    }


}
