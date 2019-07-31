package com.qqy.ancientpoetry.crawler.prase;

import com.qqy.ancientpoetry.crawler.common.Page;

/**
 * 数据解析接口
 * Author:qqy
 */
public interface Parse {
    /**
     * 解析
     * @param page
     */
    void parse(final Page page);
}
