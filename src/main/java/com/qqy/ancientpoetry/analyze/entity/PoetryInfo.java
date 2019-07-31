package com.qqy.ancientpoetry.analyze.entity;

import lombok.Data;

/**
 * 映射数据库记录 -> 分析数据
 * 将查询出的数据包装为一个对象
 * Author:qqy
 */
@Data
public class PoetryInfo {
    private String title;
    private String dynasty;
    private String author;
    private String content;
}
