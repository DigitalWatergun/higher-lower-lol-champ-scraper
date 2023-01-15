package lolchampscraper;

import java.io.IOException;
import java.util.ArrayList;

public class App {
    public static void main(String[] args) throws IOException, InterruptedException {
        Champions champions = new Champions();
        ArrayList<String> championNames = champions.getChampionNames();

        System.out.println(championNames);
        System.out.println(championNames.getClass().getSimpleName());
        System.out.println(championNames.size());
    }
}
