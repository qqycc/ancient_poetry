package com.qqy.ancientpoetry.crawler;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.qqy.ancientpoetry.crawler.common.Page;
import com.qqy.ancientpoetry.crawler.pipeline.Pipeline;
import com.qqy.ancientpoetry.crawler.prase.Parse;
import javafx.scene.Parent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 爬虫
 * Author:qqy
 */
public class Crawler {
    private final Logger logger = LoggerFactory.getLogger(Crawler.class);

    /**
     * 根页 + 还未分析的详情页
     */
    private final Queue<Page> docQueue = new LinkedBlockingQueue<>();

    /**
     * 处理完的详情页
     */
    private final Queue<Page> detailQueue = new LinkedBlockingQueue<>();

    /**
     * 采集器
     */
    private final WebClient webClient;

    /**
     * 解析器
     */
    private final List<Parse> parseList = new LinkedList<>();

    /**
     * 传送管道
     */
    private final List<Pipeline> pipelineList = new LinkedList<>();

    /**
     * 线程调度器
     */
    private final ExecutorService executorService;

    /**
     * 构造方法
     */
    public Crawler() {
        this.webClient = new WebClient(BrowserVersion.CHROME);
        this.webClient.getOptions().setJavaScriptEnabled(false);

        this.executorService = Executors.newFixedThreadPool(8, new ThreadFactory() {
            private final AtomicInteger id = new AtomicInteger(0);

            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                thread.setName("Crawler-Thread-" + id.getAndIncrement());
                return thread;
            }
        });
    }

    public void addPage(Page page) {
        this.docQueue.add(page);
    }

    public void addParse(Parse parse) {
        this.parseList.add(parse);
    }

    public void addPipeline(Pipeline pipeline) {
        this.pipelineList.add(pipeline);
    }

    public void start() {
        //采集与解析
        this.executorService.submit(() -> parse());
        //传送
        this.executorService.submit(() -> pipeline());
    }

    public void parse() {
        while (true) {
            try {
                //控制循环的速度
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                logger.error("Parse occur exception {}", e.getMessage());
            }

            final Page page = this.docQueue.poll();
            if (page == null) {
                continue;
            }

            this.executorService.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        HtmlPage htmlPage = Crawler.this.webClient.getPage(page.getUrl());
                        page.setHtmlPage(htmlPage);

                        //只会找到符合要求的解析器解析
                        for (Parse parse : parseList) {
                            parse.parse(page);
                        }

                        if (page.isDetail()) {
                            Crawler.this.detailQueue.add(page);
                        } else {
                            //获取详情页面，并将其加入到详情页队列
                            Iterator<Page> iterator = page.getSubPage().iterator();
                            while (iterator.hasNext()) {
                                Page subPage = iterator.next();
                                Crawler.this.docQueue.add(subPage);
                                iterator.remove();
                            }
                        }
                    } catch (IOException e) {
                        logger.error("Parse occur exception {}", e.getMessage());
                    }
                }
            });
        }
    }

    public void pipeline() {
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                logger.error("Pipeline occur exception {}", e.getMessage());
            }
            final Page page = this.detailQueue.poll();
            if (page == null) {
                continue;
            }
            this.executorService.submit(new Runnable() {
                @Override
                public void run() {
                    for (Pipeline pipeline : Crawler.this.pipelineList) {
                        pipeline.pipeline(page);
                    }
                }
            });

        }
    }

    public void stop() {
        if (this.executorService != null && !this.executorService.isShutdown()) {
            this.executorService.shutdown();
        }
        logger.info("Crawler stop ...");
    }
}
