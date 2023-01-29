package higherlowerlolchamp.scraper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.Map;

import io.github.cdimascio.dotenv.Dotenv;

public class Datastore {
    public static Connection connectToPostgres() throws SQLException {
        // Dotenv dotenv = Config.getEnvVars();
        String url = System.getenv("POSTGRES_URL");
        String user = System.getenv("POSTGRES_USER");
        String password = System.getenv("POSTGRES_PASS");
        Connection connection = DriverManager.getConnection(url, user, password);

        return connection;
    }

    public static void closePostgres(Connection connection) throws SQLException {
        connection.close();
    }

    public static void batchInsert(Connection connection, Map<String, Map<String, String>> championData) throws SQLException {
        String sql = "INSERT into champions (champion_name, matches_played, loading_screen_url) VALUES (?, ?, ?) " +
                "ON CONFLICT (champion_name) DO UPDATE SET matches_played = excluded.matches_played, loading_screen_url = excluded.loading_screen_url " +
                "WHERE champions.matches_played <> excluded.matches_played OR champions.loading_screen_url <> excluded.loading_screen_url";
        PreparedStatement sqlStatement = connection.prepareStatement(sql);

        for (Map.Entry<String, Map<String, String>> championDataEntry : championData.entrySet()) {
            Map<String, String> championInfo = championDataEntry.getValue();
            String championName = championInfo.get("championName");
            String matchesPlayed = championInfo.get("matchesPlayed");
            String loadingScreenUrl = championInfo.get("loadingScreenUrl");

            sqlStatement.setString(1, championName);
            sqlStatement.setString(2, matchesPlayed);
            sqlStatement.setString(3, loadingScreenUrl);
            sqlStatement.addBatch();
        }

        sqlStatement.executeBatch();
        sqlStatement.close();
    }
}
