package com.qqy.ancientpoetry.analyze.service;

import com.qqy.ancientpoetry.analyze.model.AuthorCount;
import com.qqy.ancientpoetry.analyze.model.WordCount;

import java.util.List;

/**
 * 分析业务接口
 * Author:qqy
 */
public interface AnalyzeService {
    /**
     * 分析作者的作品数
     * @return
     */
    List<AuthorCount> analyzeAuthorCount();

    /**
     * 分析作品数的前十
     * @return
     */
    List<AuthorCount> analyzeTopTen();

    /**
     * 词云分析
     * @return
     */
    List<WordCount> analyzeWordCloud();
}
