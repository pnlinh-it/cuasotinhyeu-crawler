package com.pnlinh.cuasotinhyeu.crawler.fetch.task;

import com.pnlinh.cuasotinhyeu.crawler.fetch.FetchManager;
import com.pnlinh.cuasotinhyeu.crawler.log.fetch.ContentEmptyLog;
import com.pnlinh.cuasotinhyeu.crawler.log.fetch.ErrorLog;
import com.pnlinh.cuasotinhyeu.crawler.log.fetch.SuccessLog;
import com.pnlinh.cuasotinhyeu.crawler.logging.LoggingManager;
import com.pnlinh.cuasotinhyeu.crawler.main.ApiService;
import com.pnlinh.cuasotinhyeu.crawler.main.Main;
import com.pnlinh.cuasotinhyeu.crawler.model.Post;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import retrofit2.Response;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FetchListTask implements Runnable {
    private int page;

    public FetchListTask(int page) {
        this.page = page;
    }

    @Override
    public void run() {
        ApiService apiService = Main.getApiService();
        LoggingManager manager = LoggingManager.getInstance();
        FetchManager fetchManager = FetchManager.getInstance();
        try {
            Response<String> response = apiService.getList(page).execute();
            String htmlContent = response.body();
            Document doc = Jsoup.parse(htmlContent);
            Elements element = doc.select("#block-views-tuvan-chuyenmuc-block-5 div.view-content");
            Element postContent = element.first();
            if (postContent == null || postContent.children().size() == 0) {
                manager.log(new ContentEmptyLog(String.valueOf(page)));
                return;
            }

            for (Element node : postContent.children()) {
                Post post = parseElement(node);
                manager.log(new SuccessLog(String.valueOf(page), post));
                fetchManager.getDetail(post);
            }
        } catch (IOException ex) {
            manager.log(new ErrorLog(String.valueOf(page), ex));
        }
    }

    private Post parseElement(Element element) {
        String des = element.select("div.views-field-field-cau-hoi span").first().text();

        return new Post.Builder()
                .title(element.select("div.views-field-title a").first().text())
                .category(element.select("div.views-field-field-chuyen-muc-hoi-dap a").first().attr("href"))
                .description(des)
                .time(getDate(des))
                .url("http://www.cuasotinhyeu.vn" + element.select("div.views-field-title a").first().attr("href"))
                .build();
    }

    private Date getDate(String des) {
        if (des == null)
            return null;

        int index = des.indexOf("-");
        if (index == -1)
            return null;

        String dateStr = des.substring(0, index).trim();
        try {
            SimpleDateFormat parser = new SimpleDateFormat("dd.MM.yyyy");
            return parser.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
