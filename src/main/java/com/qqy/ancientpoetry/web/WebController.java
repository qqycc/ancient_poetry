package com.qqy.ancientpoetry.web;

import com.google.gson.Gson;
import com.qqy.ancientpoetry.analyze.model.AuthorCount;
import com.qqy.ancientpoetry.analyze.model.WordCount;
import com.qqy.ancientpoetry.analyze.service.AnalyzeService;
import com.qqy.ancientpoetry.config.ObjectFactory;
import com.qqy.ancientpoetry.crawler.Crawler;
import spark.ResponseTransformer;
import spark.Spark;

import java.util.List;

/**
 * Web API
 * Author:qqy
 */
public class WebController {
    private final AnalyzeService analyzeService;

    public WebController(AnalyzeService analyzeService) {
        this.analyzeService = analyzeService;
    }

    // -> http://127.0.0.1:4567/
    // -> /analyze/author_count
    private List<AuthorCount> analyzeAuthorCount() {
        return analyzeService.analyzeAuthorCount();
    }

    // -> http://127.0.0.1:4567/
    // -> /analyze/top_ten
    private List<AuthorCount> analyzeTopTen() {
        return analyzeService.analyzeTopTen();
    }

    // -> http://127.0.0.1:4567/
    // -> /analyze/word_cloud
    private List<WordCount> analyzeWordCloud() {
        return analyzeService.analyzeWordCloud();
    }

    /**
     * 启动 Web
     */
    public void launch() {
        ResponseTransformer transformer = new JSONResponseTransform();

        //前端静态文件的目录
        Spark.staticFileLocation("/static");

        Spark.get("/analyze/author_count", ((request, response) -> analyzeAuthorCount()), transformer);
        Spark.get("/analyze/top_ten", ((request, response) -> analyzeTopTen()), transformer);
        Spark.get("/analyze/word_cloud", ((request, response) -> analyzeWordCloud()), transformer);
        Spark.get("/crawler/stop", ((request, response) -> {
            Crawler crawler = ObjectFactory.getInstance().getObject(Crawler.class);
            crawler.stop();
            return "爬虫停止";
        }));
    }

    public static class JSONResponseTransform implements ResponseTransformer {
        private Gson gson = new Gson();

        //对象 -> 字符串
        @Override
        public String render(Object model) throws Exception {
            return gson.toJson(model);
        }
    }
}
