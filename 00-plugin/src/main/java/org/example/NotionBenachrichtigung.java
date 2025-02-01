package org.example;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.OutputStream;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import io.github.cdimascio.dotenv.Dotenv;

public class NotionBenachrichtigung {

    public void sendeNotionBenachrichtigung(String medikamentName, String ablaufdatum) {
        try {
            Dotenv dotenv = Dotenv.configure()
                    .directory("./")
                    .filename(".env")
                    .load();

            URL url = new URL("https://api.notion.com/v1/pages"); // Ersetze durch den korrekten Endpunkt
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorization", "Bearer " + dotenv.get("NOTION_API_KEY")); // Ersetze NOTION_API_KEY
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Notion-Version", "2022-06-28");
            connection.setDoOutput(true);
            System.out.println(connection.toString());

            // JSON-Body erstellen (verwende hier die createJson Methode aus dem vorherigen Beispiel)
            JsonObject jsonBody = createJson();
            String jsonInputString = jsonBody.toString();

            // JSON-Body in die Anfrage schreiben
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonInputString.toString().getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_CREATED) {
                System.out.println("Notion-Benachrichtigung erfolgreich gesendet.");
            } else {
                System.err.println("Fehler beim Senden der Notion-Benachrichtigung: " + responseCode);
            }

        } catch (Exception e) {
            System.err.println("Fehler beim Senden der Notion-Benachrichtigung: " + e.getMessage());
        }
    }
    private static JsonObject createJson() {
        JsonObject json = new JsonObject();

        JsonObject parent = new JsonObject();
        parent.addProperty("type", "database_id");
        parent.addProperty("database_id", "18dc6fd332398095a283c34ce995b647");
        json.add("parent", parent);

        JsonObject properties = new JsonObject();
        JsonObject title = new JsonObject();
        title.addProperty("type", "title");

        JsonArray titleArray = new JsonArray();
        JsonObject text = new JsonObject();
        text.addProperty("content", "Habits");

        titleArray.add(text);
        title.add("title", titleArray);
        properties.add("Title", title);

        json.add("properties", properties);

        return json;
    }


}
