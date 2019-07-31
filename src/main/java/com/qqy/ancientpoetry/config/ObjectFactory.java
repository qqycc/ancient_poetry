package com.qqy.ancientpoetry.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.qqy.ancientpoetry.analyze.dao.AnalyzeDao;
import com.qqy.ancientpoetry.analyze.dao.impl.AnalyzeDaoImpl;
import com.qqy.ancientpoetry.analyze.service.AnalyzeService;
import com.qqy.ancientpoetry.analyze.service.impl.AnalyzeServiceImpl;
import com.qqy.ancientpoetry.crawler.Crawler;
import com.qqy.ancientpoetry.crawler.common.Page;
import com.qqy.ancientpoetry.crawler.pipeline.DatabasePipeline;
import com.qqy.ancientpoetry.crawler.prase.DataPageParse;
import com.qqy.ancientpoetry.crawler.prase.DocumentParse;
import com.qqy.ancientpoetry.web.WebController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * 对象工厂
 * Author:qqy
 */
public class ObjectFactory {
    private static final ObjectFactory INSTANCE = new ObjectFactory();

    private final Logger logger = LoggerFactory.getLogger(ObjectFactory.class);

    /**
     * 存放所有对象
     */
    private final Map<Class, Object> objectMap = new HashMap<>();

    public ObjectFactory() {
        //1.初始化配置文件
        initConfigProperties();

        //2.初始化数据源
        initDataSource();

        //3.爬虫对象
        initCrawler();

        //4.Web对象
        initWebController();

        //5.对象清单打印输出
        printObjectList();
    }

    private void initConfigProperties() {
        ConfigProperties configProperties = new ConfigProperties();

        objectMap.put(ConfigProperties.class, configProperties);
        logger.info("ConfigProperties info:\n{}", configProperties.toString());
    }

    private void initDataSource() {
        ConfigProperties configProperties = getObject(ConfigProperties.class);
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUsername(configProperties.getDbUsername());
        dataSource.setPassword(configProperties.getDbPassword());
        dataSource.setDriverClassName(configProperties.getDbDriverClass());
        dataSource.setUrl(configProperties.getDbUrl());

        objectMap.put(DataSource.class, dataSource);
    }

    private void initCrawler() {
        ConfigProperties configProperties = getObject(ConfigProperties.class);
        DataSource dataSource = getObject(DataSource.class);

        final Page page = new Page(configProperties.getCrawlerBase(),
                configProperties.getCrawlerPath(),
                configProperties.isCrawlerDetail());

        Crawler crawler = new Crawler();
        crawler.addParse(new DocumentParse());
        crawler.addParse(new DataPageParse());
        crawler.addPipeline(new DatabasePipeline(dataSource));
        //添加根文档
        crawler.addPage(page);

        objectMap.put(Crawler.class, crawler);
    }

    private void initWebController() {
        DataSource dataSource = getObject(DataSource.class);
        AnalyzeDao analyzeDao = new AnalyzeDaoImpl(dataSource);
        AnalyzeService analyzeService = new AnalyzeServiceImpl(analyzeDao);
        WebController webController = new WebController(analyzeService);

        objectMap.put(WebController.class, webController);
    }

    public <T> T getObject(Class classz) {
        if (!objectMap.containsKey(classz)) {
            throw new IllegalArgumentException("Class" + classz.getName() + "not found Object");
        }
        return (T) objectMap.get(classz);
    }

    public static ObjectFactory getInstance() {
        return INSTANCE;
    }

    private void printObjectList() {
        logger.info("\n===== ObjectFactory List =====");
        for (Map.Entry<Class, Object> entry : objectMap.entrySet()) {
            //取全名称
            logger.info(String.format("\t[%s] ==> [%s]", entry.getKey().getCanonicalName()
                    , entry.getValue().getClass().getCanonicalName()));
        }
        logger.info("\n==========================\n");
    }
}
