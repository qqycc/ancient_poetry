package com.qqy.ancientpoetry.crawler.prase;

import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.qqy.ancientpoetry.crawler.common.Page;

import java.util.function.Consumer;

/**
 * 非详情页解析
 * Author:qqy
 */
public class DocumentParse implements Parse {
    @Override
    public void parse(final Page page) {
        if (page.isDetail()) {
            return;
        }
        HtmlPage htmlPage = page.getHtmlPage();
        htmlPage.getBody()
                .getElementsByAttribute("div", "class", "typecont")
                .forEach(new Consumer<HtmlElement>() {
                    @Override
                    public void accept(HtmlElement div) {
                        DomNodeList<HtmlElement> aNodelist = div.getElementsByTagName("a");
                        aNodelist.forEach(new Consumer<HtmlElement>() {
                            @Override
                            public void accept(HtmlElement aNode) {
                                String path = aNode.getAttribute("href");
                                Page subPage = new Page(page.getBase(), path, true);
                                page.getSubPage().add(subPage);
                            }
                        });
                    }
                });
    }
}
