package de.meditrack;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.OutputStream;
import java.nio.file.StandardOpenOption;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import io.github.cdimascio.dotenv.Dotenv;

public class NotionBenachrichtigungDatei implements Nachrichtenformat {

    private String inhaltDerNachrichtalsJson ;
    @Override
    public boolean sendeNachricht(Medikament medikament) {

        try {
            Dotenv dotenv = Dotenv.configure()
                    .directory("./")
                    .filename(".env")
                    .load();

            URL url = new URL("https://api.notion.com/v1/pages");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorization", "Bearer " + dotenv.get("NOTION_API_KEY"));
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Notion-Version", "2022-06-28");
            connection.setDoOutput(true);
            System.out.println(connection.toString());

            JsonObject jsonBody = createJson(medikament);
            String jsonInputString = jsonBody.toString();
            inhaltDerNachrichtalsJson = jsonInputString;
            
            // Schreibe JSON-String in eine Datei
            try {
                java.nio.file.Path path = java.nio.file.Paths.get("notion_request.json");
                java.nio.file.Files.writeString(path, jsonInputString, 
                    java.nio.file.StandardOpenOption.CREATE, 
                    StandardOpenOption.APPEND);
                System.out.println("JSON-String in Datei 'notion_request.json' geschrieben.");
            } catch (Exception e) {
                System.err.println("Fehler beim Schreiben in Datei: " + e.getMessage());
            }

            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonInputString.toString().getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_CREATED) {
                System.out.println("Notion-Benachrichtigung erfolgreich gesendet.");
                return true;
            } else {
                String fehlerMeldung = "Fehler beim Senden der Notion-Benachrichtigung: " + responseCode;
                System.err.println(fehlerMeldung);
                return false;
            }

        } catch (Exception e) {
            String fehlerMeldung = "Fehler beim Senden der Notion-Benachrichtigung: " + e.getMessage();
            System.err.println(fehlerMeldung);
            return false;
        }
    }
    
    private static JsonObject createJson(Medikament medikament) {
        JsonObject json = new JsonObject();
    
        // Parent info
        JsonObject parent = new JsonObject();
        parent.addProperty("type", "database_id");
        parent.addProperty("database_id", "18dc6fd332398095a283c34ce995b647");
        json.add("parent", parent);
    
        // Properties
        JsonObject properties = new JsonObject();
    
        // Title property
        JsonObject title = new JsonObject();
        JsonArray titleArray = new JsonArray();
        JsonObject titleText = new JsonObject();
        JsonObject textObj = new JsonObject();
        textObj.addProperty("content", medikament.getMedikamentenName());
        titleText.add("text", textObj);
        titleArray.add(titleText);
        title.add("title", titleArray);
        properties.add("Title", title);
    
        // Ablaufdatum property
        JsonObject ablaufdatum = new JsonObject();
        JsonObject date = new JsonObject();
        date.addProperty("start", medikament.getAblaufDatum().toString()); // e.g. "2021-05"
        ablaufdatum.add("date", date);
        properties.add("Ablaufdatum", ablaufdatum);
    
        // Lagerort property (rich_text)
        JsonObject lagerort = new JsonObject();
        JsonArray richTextArray = new JsonArray();
        JsonObject richTextObj = new JsonObject();
        JsonObject lagerortText = new JsonObject();
        lagerortText.addProperty("content", medikament.getLagerortId().toString());
        richTextObj.add("text", lagerortText);
        richTextArray.add(richTextObj);
        lagerort.add("rich_text", richTextArray);
        properties.add("Lagerort", lagerort);
    
        json.add("properties", properties);
    
        return json;
    }

    public String getInhaltDerNachrichtalsJson() {
        return inhaltDerNachrichtalsJson;
    }
}
