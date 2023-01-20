package lolchampscraper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.Map;

import io.github.cdimascio.dotenv.Dotenv;

public class Datastore {
    public static Connection connectToPostgres() throws SQLException {
        Dotenv dotenv = Config.getEnvVars();
        String url = dotenv.get("POSTGRES_URL");
        String user = dotenv.get("POSTGRES_USER");
        String password = dotenv.get("POSTGRES_PASS");
        Connection connection = DriverManager.getConnection(url, user, password);

        return connection;
    }

    public static void closePostgres(Connection connection) throws SQLException {
        connection.close();
    }

    public static void batchInsert(Connection connection, HashMap<String, String> champWinRates) throws SQLException {
        String sql = "INSERT into champions (champion_name, win_rate) VALUES (?, ?) " +
                "ON CONFLICT (champion_name) DO UPDATE SET win_rate = excluded.win_rate " +
                "WHERE champions.win_rate <> excluded.win_rate";
        PreparedStatement sqlStatement = connection.prepareStatement(sql);

        for (Map.Entry<String, String> entry : champWinRates.entrySet()) {
            String champion = entry.getKey();
            String winrate = entry.getValue();
            sqlStatement.setString(1, champion.toLowerCase());
            sqlStatement.setString(2, winrate);
            sqlStatement.addBatch();
        }

        sqlStatement.executeBatch();
        sqlStatement.close();
    }
}
