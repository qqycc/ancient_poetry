package com.qqy.ancientpoetry.analyze.service.impl;

import com.qqy.ancientpoetry.analyze.dao.AnalyzeDao;
import com.qqy.ancientpoetry.analyze.entity.PoetryInfo;
import com.qqy.ancientpoetry.analyze.model.AuthorCount;
import com.qqy.ancientpoetry.analyze.model.WordCount;
import com.qqy.ancientpoetry.analyze.service.AnalyzeService;
import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.NlpAnalysis;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * 具体业务实现
 * Author:qqy
 */
public class AnalyzeServiceImpl implements AnalyzeService {
    private final Logger logger = LoggerFactory.getLogger(AnalyzeServiceImpl.class);

    private final AnalyzeDao analyzeDao;

    public AnalyzeServiceImpl(AnalyzeDao analyzeDao) {
        this.analyzeDao = analyzeDao;
    }

    @Override
    public List<AuthorCount> analyzeAuthorCount() {
        List<AuthorCount> authorCounts = analyzeDao.queryAuthorCount();
        //按照降序排列
        //authorCounts.sort(Comparator.comparing(authorCount -> authorCount.getCount() * (-1)));
        return authorCounts;
    }

    @Override
    public List<AuthorCount> analyzeTopTen() {
        List<AuthorCount> authorCounts = analyzeDao.queryTopTen();
        return authorCounts;
    }

    @Override
    public List<WordCount> analyzeWordCloud() {
        //存放结果：词，数
        Map<String, Integer> wordCountResult = new HashMap<>();

        List<PoetryInfo> poetryInfos = analyzeDao.queryAllPoetryInfo();
        for (PoetryInfo poetryInfo : poetryInfos) {
            List<Term> terms = new ArrayList<>();

            String title = poetryInfo.getTitle();
            String content = poetryInfo.getContent();

            //分词
            terms.addAll(NlpAnalysis.parse(title).getTerms());
            terms.addAll(NlpAnalysis.parse(content).getTerms());

            //过滤
            Iterator<Term> iterator = terms.iterator();
            while (iterator.hasNext()) {
                Term term = iterator.next();

                //词性的过滤
                if (term.getNatureStr() == null || term.getNatureStr().equals("w")) {
                    iterator.remove();
                    continue;
                }
                //词长度的过滤
                if (term.getRealName().length() < 2) {
                    iterator.remove();
                    continue;
                }

                String realName = term.getRealName();
                Integer count;
                if (wordCountResult.containsKey(realName)) {
                    count = wordCountResult.get(realName) + 1;
                } else {
                    count = 1;
                }
                wordCountResult.put(realName, count);
            }

        }

        List<WordCount> wordCounts = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : wordCountResult.entrySet()) {
            WordCount wordCount = new WordCount();
            wordCount.setCount(entry.getValue());
            wordCount.setWord(entry.getKey());
            wordCounts.add(wordCount);
        }
        return wordCounts;
    }
}
