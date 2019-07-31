package com.qqy.ancientpoetry.crawler.prase;

import com.gargoylesoftware.htmlunit.html.*;
import com.qqy.ancientpoetry.crawler.common.Page;

/**
 * 详情页解析
 * Author:qqy
 */
public class DataPageParse implements Parse {
    @Override
    public void parse(final Page page) {
        if (!page.isDetail()) {
            return;
        }

        //htmlunit -> XPath
        HtmlPage htmlPage = page.getHtmlPage();
        HtmlElement body = htmlPage.getBody();

        //标题
        String titlePath = "//div[@class='cont']/h1/text()";
        DomText titleDom = (DomText) body.getByXPath(titlePath).get(0);
        String title = titleDom.asText();

        //朝代
        String dynastyPath = "//div[@class='cont']/p[@class='source']/a[1]";
        HtmlAnchor dynastyDom = (HtmlAnchor) body.getByXPath(dynastyPath).get(0);
        String dynasty = dynastyDom.asText();

        //作者
        String authorPath = "//div[@class='cont']/p[@class='source']/a[2]";
        HtmlAnchor authorDom = (HtmlAnchor) body.getByXPath(authorPath).get(0);
        String author = authorDom.asText();

        //正文
        String contentPath = "//div[@class='cont']/div[@class='contson']";
        HtmlDivision contentDom = (HtmlDivision) body.getByXPath(contentPath).get(0);
        String content = contentDom.asText();

        page.getDataSet().putData("title", title);
        page.getDataSet().putData("dynasty", dynasty);
        page.getDataSet().putData("author", author);
        page.getDataSet().putData("content", content);
    }
}
