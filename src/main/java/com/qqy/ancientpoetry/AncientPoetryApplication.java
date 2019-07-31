package com.qqy.ancientpoetry;

import com.qqy.ancientpoetry.config.ObjectFactory;
import com.qqy.ancientpoetry.crawler.Crawler;
import com.qqy.ancientpoetry.web.WebController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 程序的主类
 * Author:qqy
 */
public class AncientPoetryApplication {
    private static final Logger LOGGER = LoggerFactory.getLogger(AncientPoetryApplication.class);

    public static void main(String[] args) {

        WebController webController = ObjectFactory.getInstance().getObject(WebController.class);
        //运行Web服务，提供接口
        LOGGER.info("Web Server launch ...");
        webController.launch();

        //爬虫
        if (args.length == 1 && args[0].equals("run-crawler")) {
            Crawler crawler = ObjectFactory.getInstance().getObject(Crawler.class);
            LOGGER.info("Crawler start ...");
            crawler.start();
        }
    }
}
