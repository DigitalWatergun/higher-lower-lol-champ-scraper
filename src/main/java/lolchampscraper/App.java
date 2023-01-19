package lolchampscraper;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class App {
    public static void main(String[] args) throws IOException, InterruptedException, SQLException {
        System.out.println("Starting cronjob...");
        ArrayList<String> championNames = Champions.getChampionNames();
        System.out.println("Number of champion names pulled: " + championNames.size());

        System.out.println("Pulling winrates for each champion...");
        HashMap<String, String> champWinRates = new HashMap<String, String>();
        for (String championName : championNames) {
            String winRate = WRScraper.scrapeUgg(championName);
            champWinRates.put(championName, winRate);
        }

        System.out.println("Upserting champion winrates to database...");
        Connection connection = Datastore.connectToPostgres();
        Datastore.batchInsert(connection, champWinRates);
        Datastore.closePostgres(connection);
        System.out.println("Done!!");
    }
}
