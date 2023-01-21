package higherlowerlolchamp.scraper;

import java.io.IOException;
import java.net.MalformedURLException;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class Scraper {
    public static String scrapeUgg(String championName) throws FailingHttpStatusCodeException, MalformedURLException, IOException {
        try (WebClient client = new WebClient()) {
            client.getOptions().setCssEnabled(false);
            client.getOptions().setJavaScriptEnabled(false);

            String baseUrl = "https://u.gg/lol/champions/%s/build";
            String searchUrl = String.format(baseUrl, championName);

            HtmlPage page = (HtmlPage) client.getPage(searchUrl);
            HtmlDivision matchesPlayed;
            try {
                matchesPlayed = (HtmlDivision) page
                .getByXPath("//div[contains(@class, 'champion-ranking-stats-normal')]" +
                        "/div[contains(@class, 'matches')]/div[contains(@class, 'value')]")
                .get(0);
            }
            catch (java.lang.IndexOutOfBoundsException e) {
                System.out.println("[ERROR] Could not pull matches played for chapmion: " + championName);
                return null;
            }

            return matchesPlayed.asNormalizedText().replace(",","");
        }
    }
}
