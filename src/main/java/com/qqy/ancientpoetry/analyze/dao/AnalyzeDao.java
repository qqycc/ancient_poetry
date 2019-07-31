package com.qqy.ancientpoetry.analyze.dao;

import com.qqy.ancientpoetry.analyze.entity.PoetryInfo;
import com.qqy.ancientpoetry.analyze.model.AuthorCount;

import java.util.List;

/**
 * 获取所有数据
 * Author:qqy
 */
public interface AnalyzeDao {
    /**
     * 获取每个作者及其作品数
     * @return
     */
    List<AuthorCount> queryAuthorCount();

    /**
     * 获取作品数的前十
     * @return
     */
    List<AuthorCount> queryTopTen();

    /**
     * 获取所有古诗的信息
     * @return
     */
    List<PoetryInfo> queryAllPoetryInfo();
}
