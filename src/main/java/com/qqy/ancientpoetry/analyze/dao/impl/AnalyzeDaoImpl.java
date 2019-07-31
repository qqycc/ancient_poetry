package com.qqy.ancientpoetry.analyze.dao.impl;

import com.qqy.ancientpoetry.analyze.dao.AnalyzeDao;
import com.qqy.ancientpoetry.analyze.entity.PoetryInfo;
import com.qqy.ancientpoetry.analyze.model.AuthorCount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Dao的实现类
 * Author:qqy
 */
public class AnalyzeDaoImpl implements AnalyzeDao {
    private final Logger logger = LoggerFactory.getLogger(AnalyzeDaoImpl.class);
    private final DataSource dataSource;

    public AnalyzeDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<AuthorCount> queryAuthorCount() {
        //存放结果
        List<AuthorCount> datas = new ArrayList<>();

        String sql = "select count(*) as count,author from poetry_info group by author";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()
        ) {
            while (resultSet.next()) {
                AuthorCount authorCount = new AuthorCount();
                authorCount.setAuthor(resultSet.getString("author"));
                authorCount.setCount(resultSet.getInt("count"));
                datas.add(authorCount);
            }
        } catch (SQLException e) {
            logger.error("Database query occur exception{}", e.getMessage());
        }
        return datas;
    }

    @Override
    public List<AuthorCount> queryTopTen() {
        //存放结果
        List<AuthorCount> datas = new ArrayList<>();

        String sql = "select count(*) as count,author from poetry_info group by author order by count desc limit 0,10;";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()
        ) {
            while (resultSet.next()) {
                AuthorCount authorCount = new AuthorCount();
                authorCount.setAuthor(resultSet.getString("author"));
                authorCount.setCount(resultSet.getInt("count"));
                datas.add(authorCount);
            }
        } catch (SQLException e) {
            logger.error("Database query occur exception{}", e.getMessage());
        }
        return datas;
    }

    @Override
    public List<PoetryInfo> queryAllPoetryInfo() {
        List<PoetryInfo> datas = new ArrayList<>();
        String sql = "select title,dynasty,author,content from poetry_info;";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()
        ) {
            while (resultSet.next()) {
                PoetryInfo poetryInfo = new PoetryInfo();
                poetryInfo.setTitle(resultSet.getString("title"));
                poetryInfo.setDynasty(resultSet.getString("dynasty"));
                poetryInfo.setAuthor(resultSet.getString("author"));
                poetryInfo.setContent(resultSet.getString("content"));
                datas.add(poetryInfo);
            }
        } catch (SQLException e) {
            logger.error("Database query occur exception{}", e.getMessage());
        }
        return datas;
    }
}
