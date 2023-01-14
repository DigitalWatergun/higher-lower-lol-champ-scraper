package lolchampscraper;

import java.io.IOException;

public class App {
    public static void main(String[] args) throws IOException, InterruptedException {
        Champions champions = new Champions();
        String siteInfo = champions.getChampionData();
        
        System.out.print(siteInfo);
    }
}
