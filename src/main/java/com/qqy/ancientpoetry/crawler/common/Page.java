package com.qqy.ancientpoetry.crawler.common;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

/**
 * 页面
 * Author:qqy
 */
@Data
public class Page {
    /**
     * 数据网站的根地址
     */
    private final String base;

    /**
     * 详情页的具体路径
     */
    private final String path;

    /**
     * 网页的DOM对象（文档对象模型）
     */
    private HtmlPage htmlPage;

    /**
     * 表示网页是否是详情页(true)
     */
    private final boolean detail;

    /**
     * 子页面对象集合
     */
    private Set<Page> subPage = new HashSet<>();

    /**
     * 数据对象
     */
    private DataSet dataSet = new DataSet();

    public String getUrl() {
        return this.base + this.path;
    }

}
