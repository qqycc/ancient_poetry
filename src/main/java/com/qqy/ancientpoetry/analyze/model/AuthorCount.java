package com.qqy.ancientpoetry.analyze.model;

import lombok.Data;

/**
 * 作者及其创作诗歌的数量
 * Author:qqy
 */
@Data
public class AuthorCount {
    private String author;
    private Integer count;
}
