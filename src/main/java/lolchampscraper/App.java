package lolchampscraper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.Map;

public class App {
    public static void main(String[] args) throws IOException, InterruptedException {
        Champions champions = new Champions();
        ArrayList<String> championNames = champions.getChampionNames();
        System.out.println(championNames.size());

        HashMap<String, String> champWinRates = new HashMap<String, String>();

        WRScraper wrScraper = new WRScraper();
        for (String championName : championNames) {
            String winRate = wrScraper.scrapeUgg(championName);
            champWinRates.put(championName, winRate);
        }

        System.out.println(champWinRates);
        for (Map.Entry<String, String> entry : champWinRates.entrySet()) {
            String champ = entry.getKey();
            String wr = entry.getValue();
            System.out.println(champ + " " + wr);
        }
    }
}
