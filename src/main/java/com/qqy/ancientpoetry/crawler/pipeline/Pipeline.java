package com.qqy.ancientpoetry.crawler.pipeline;

import com.qqy.ancientpoetry.crawler.common.Page;

/**
 * 数据管道传送接口
 * Author:qqy
 */
public interface Pipeline {
    /**
     * 传送
     * @param page
     */
    void pipeline(final Page page);
}
