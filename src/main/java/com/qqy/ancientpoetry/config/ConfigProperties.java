package com.qqy.ancientpoetry.config;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 加载配置信息
 * Author:qqy
 */
@Data
public class ConfigProperties {
    private final Logger logger = LoggerFactory.getLogger(ConfigProperties.class);

    private String crawlerBase;
    private String crawlerPath;
    private boolean crawlerDetail;

    private String dbUsername;
    private String dbPassword;
    private String dbUrl;
    private String dbDriverClass;

    public ConfigProperties() {
        InputStream inputStream = ConfigProperties.class.getClassLoader().getResourceAsStream("config.properties");
        Properties properties = new Properties();
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            logger.error("Config init occur exception{}", e.getMessage());
        }

        this.crawlerBase = String.valueOf(properties.get("crawler.base"));
        this.crawlerPath = String.valueOf(properties.get("crawler.path"));
        this.crawlerDetail = Boolean.parseBoolean(String.valueOf(properties.get("crawler.detail")));

        this.dbUsername = String.valueOf(properties.get("db.username"));
        this.dbPassword = String.valueOf(properties.get("db.password"));
        this.dbUrl = String.valueOf(properties.get("db.url"));
        this.dbDriverClass = String.valueOf(properties.get("db.driver"));
    }
}
