package com.qqy.ancientpoetry.crawler.pipeline;

import com.qqy.ancientpoetry.crawler.common.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * 解析后的数据存入数据库
 * Author:qqy
 */
public class DatabasePipeline implements Pipeline {
    private final Logger logger = LoggerFactory.getLogger(DatabasePipeline.class);

    private final DataSource dataSource;

    public DatabasePipeline(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void pipeline(final Page page) {
        String title = (String) page.getDataSet().getData("title");
        String dynasty = (String) page.getDataSet().getData("dynasty");
        String author = (String) page.getDataSet().getData("author");
        String content = (String) page.getDataSet().getData("content");

        String sql = "insert into poetry_info(title, dynasty, author, content) values (?,?,?,?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            //设置参数
            statement.setString(1, title);
            statement.setString(2, dynasty);
            statement.setString(3, author);
            statement.setString(4, content);

            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Database insert occur exception {}", e.getMessage());
        }
    }
}
