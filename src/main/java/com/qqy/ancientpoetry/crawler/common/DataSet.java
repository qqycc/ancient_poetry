package com.qqy.ancientpoetry.crawler.common;

import java.util.HashMap;
import java.util.Map;

/**
 * 数据集合，存储解析后数据
 * Author:qqy
 */
public class DataSet {
    /**
     * 存放DOM解析后的数据(键值对 -> 属性：值）
     */
    private Map<String, Object> data = new HashMap<>();

    public void putData(String key, Object value) {
        this.data.put(key, value);
    }

    public Object getData(String key) {
        return this.data.get(key);
    }

    public Map<String, Object> getData() {
        //返回一个副本，防止把内部对象的引用给了外部对象，导致数据被破坏
        return new HashMap<>(this.data);
    }
}
