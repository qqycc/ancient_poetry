package com.qqy.ancientpoetry.analyze.model;

import lombok.Data;

/**
 * 分词及其出现次数
 * Author:qqy
 */
@Data
public class WordCount {
    private String word;
    private Integer count;
}
