package lolchampscraper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


public class Champions {
    public void test() {
        System.out.println("Hello Champions");
    }

    public String getChampionData() throws IOException, InterruptedException {
        String siteInfo = scrapeUGG();
        return siteInfo;
    }
 
    private String scrapeUGG() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .header("Accept", "application/json")
                .uri(URI.create("http://ddragon.leagueoflegends.com/cdn/13.1.1/data/en_US/champion.json"))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return response.body();
    }
}
