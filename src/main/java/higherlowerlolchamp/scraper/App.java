package higherlowerlolchamp.scraper;

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

        System.out.println("Pulling matches played for each champion...");
        HashMap<String, String> champMatchesPlayed = new HashMap<String, String>();
        for (String championName : championNames) {
            String matchesPlayed = Scraper.scrapeUgg(championName);
            champMatchesPlayed.put(championName.toLowerCase(), matchesPlayed);
            System.out.println(championName + " " + matchesPlayed);
        }

        System.out.println("Upserting champion matches played to database...");
        Connection connection = Datastore.connectToPostgres();
        Datastore.batchInsert(connection, champMatchesPlayed);
        Datastore.closePostgres(connection);
        System.out.println("Done!!");
    }
}
