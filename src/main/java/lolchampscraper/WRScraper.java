package lolchampscraper;

import java.io.IOException;
import java.net.MalformedURLException;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class WRScraper {
    public static String scrapeUgg(String championName) throws FailingHttpStatusCodeException, MalformedURLException, IOException {
        try (WebClient client = new WebClient()) {
            client.getOptions().setCssEnabled(false);
            client.getOptions().setJavaScriptEnabled(false);

            String baseUrl = "https://u.gg/lol/champions/%s/build";
            String searchUrl = String.format(baseUrl, championName);

            HtmlPage page = (HtmlPage) client.getPage(searchUrl);            
            HtmlDivision winRate = (HtmlDivision) page
                    .getByXPath("//div[contains(@class, 'champion-ranking-stats-normal')]" +
                    "/div[contains(@class, 'win-rate')]/div[contains(@class, 'value')]").get(0);

            return winRate.asNormalizedText();
        }
    }
}
