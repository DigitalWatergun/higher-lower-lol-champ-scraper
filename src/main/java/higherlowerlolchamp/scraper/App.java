package higherlowerlolchamp.scraper;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class App {
    public static void main(String[] args) throws IOException, InterruptedException, SQLException {
        System.out.println("Starting cronjob...");
        ArrayList<String> championNames = Champions.getChampionNames();
        System.out.println("Number of champion names pulled: " + championNames.size());

        System.out.println("Pulling matches played for each champion...");
        Map<String, Map<String, String>> championData = new HashMap<>();
        for (String championName : championNames) {
            String matchesPlayed = Scraper.scrapeUgg(championName);
            if (matchesPlayed == null) {
                continue;
            }

            Map<String, String> championInfo = new HashMap<>();
            championInfo.put("championName", championName);
            championInfo.put("matchesPlayed", matchesPlayed);
            championInfo.put("loadingScreenUrl", String
                    .format("http://ddragon.leagueoflegends.com/cdn/img/champion/loading/%s_0.jpg", championName));
            championData.put(championName, championInfo);
            System.out.println(championName + " " + championInfo);
        }

        System.out.println("Upserting champion data to database...");
        Connection connection = Datastore.connectToPostgres();
        Datastore.batchInsert(connection, championData);
        Datastore.closePostgres(connection);
        System.out.println("Done!!");
    }
}
