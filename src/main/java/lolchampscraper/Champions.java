package lolchampscraper;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;


public class Champions {
    ArrayList<String> champions = new ArrayList<String>();

    private static JsonObject retrieveChampionJson() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .header("Accept", "application/json")
                .uri(URI.create("http://ddragon.leagueoflegends.com/cdn/13.1.1/data/en_US/champion.json"))
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        
        Gson g = new Gson();
        String responseString = response.body();
        JsonObject jsonObject = g.fromJson(responseString, JsonObject.class);
        return jsonObject;
    }

    public ArrayList<String> getChampionNames() throws IOException, InterruptedException {
        JsonObject championJson = retrieveChampionJson();
        JsonObject championJsonData = (JsonObject) championJson.get("data");
        Set<Map.Entry<String, JsonElement>> champions = championJsonData.entrySet();
        for (Map.Entry<String, JsonElement> champion : champions) {
            String championName = champion.getKey();
            if (championName.equals("MonkeyKing")) {
                this.champions.add("Wukong");
                continue;
            }
            this.champions.add(championName);
        }

        return this.champions;
    }
}
