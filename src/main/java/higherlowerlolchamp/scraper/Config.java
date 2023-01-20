package higherlowerlolchamp.scraper;

import io.github.cdimascio.dotenv.Dotenv;

public class Config {
    public static Dotenv getEnvVars() {
        Dotenv dotenv = Dotenv.load();
        return dotenv;
    }
}
