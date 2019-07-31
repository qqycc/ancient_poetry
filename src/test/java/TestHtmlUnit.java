import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomText;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import java.io.IOException;

/**
 * Author:qqy
 */
public class TestHtmlUnit {
    public static void main(String[] args) {
        try (WebClient webClient = new WebClient(BrowserVersion.CHROME)) {
            webClient.getOptions().setJavaScriptEnabled(false);
            HtmlPage htmlPage = webClient.getPage("https://so.gushiwen.org/shiwenv_21c484c79935.aspx");

//            HtmlDivision domElement = (HtmlDivision) htmlPage.getElementById("contson04395ca29c6b");
//            String text = domElement.asText();
//            System.out.println(text);

            String titlePath="//div[@class='cont']/p[@class='source']/a[2]";
            Object o=htmlPage.getBody().getByXPath(titlePath).get(0);
            System.out.println(o.getClass().getName());


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
