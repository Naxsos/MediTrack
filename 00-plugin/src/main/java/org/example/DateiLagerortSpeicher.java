package org.example;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Eine Implementierung des LagerortSpeicher-Interfaces, die Lagerorte in einer Datei speichert.
 */
public class DateiLagerortSpeicher implements LagerortSpeicher {
    private final File datei;

    /**
     * Erstellt einen neuen DateiLagerortSpeicher, der Lagerorte in der angegebenen Datei speichert.
     * 
     * @param filePath Der Pfad zur Datei, in der die Lagerorte gespeichert werden sollen
     */
    public DateiLagerortSpeicher(String filePath) {
        this.datei = new File(filePath);
        try {
            if (!datei.exists()) {
                datei.createNewFile(); // Erstellt die Datei, falls sie nicht existiert
            }
        } catch (IOException e) {
            throw new RuntimeException("Fehler beim Initialisieren der Lagerort-Datei", e);
        }
    }

    @Override
    public Lagerort speichern(Lagerort lagerort) {
        String line = lagerort.toString();
        String filepath = datei.getPath();
        
        try {
            // Zuerst prüfen, ob der Lagerort bereits existiert
            if (existiert(lagerort.getLagerortId().toString())) {
                // Bestehenden Eintrag entfernen
                loeschen(lagerort.getLagerortId().toString());
            }
            
            // Neuen Eintrag speichern
            Files.writeString(Paths.get(filepath), line + System.lineSeparator(), 
                StandardOpenOption.APPEND, StandardOpenOption.CREATE);
            
            return lagerort;
        } catch (IOException e) {
            System.err.printf("Fehler beim Speichern des Lagerorts: %s%n", e.getMessage());
            throw new RuntimeException("Fehler beim Speichern des Lagerorts", e);
        }
    }

    @Override
    public Optional<Lagerort> findeViaId(String lagerortId) {
        try {
            List<String> zeilen = Files.readAllLines(Paths.get(datei.getPath()));
            for (String zeile : zeilen) {
                if (zeile.contains("ID=" + lagerortId)) {
                    return Optional.of(parseLagerort(zeile));
                }
            }
        } catch (IOException e) {
            System.err.printf("Fehler beim Suchen des Lagerorts mit ID %s: %s%n", lagerortId, e.getMessage());
        }
        
        return Optional.empty();
    }

    @Override
    public List<Lagerort> findeViaBezeichnung(String bezeichnung) {
        List<Lagerort> gefundeneLagerorte = new ArrayList<>();
        
        try {
            List<String> zeilen = Files.readAllLines(Paths.get(datei.getPath()));
            for (String zeile : zeilen) {
                if (zeile.contains(bezeichnung)) {
                    gefundeneLagerorte.add(parseLagerort(zeile));
                }
            }
        } catch (IOException e) {
            System.err.printf("Fehler beim Suchen von Lagerorten mit Bezeichnung %s: %s%n", bezeichnung, e.getMessage());
        }
        
        return gefundeneLagerorte;
    }

    @Override
    public List<Lagerort> findeAlle() {
        List<Lagerort> alleLagerorte = new ArrayList<>();
        
        try {
            List<String> zeilen = Files.readAllLines(Paths.get(datei.getPath()));
            for (String zeile : zeilen) {
                alleLagerorte.add(parseLagerort(zeile));
            }
        } catch (IOException e) {
            System.err.printf("Fehler beim Laden aller Lagerorte: %s%n", e.getMessage());
        }
        
        return alleLagerorte;
    }

    @Override
    public boolean loeschen(String lagerortId) {
        Path pfad = Paths.get(datei.getPath());
        
        try {
            List<String> zeilen = Files.readAllLines(pfad);
            List<String> gefilterteZeilen = zeilen.stream()
                    .filter(zeile -> !zeile.contains("ID=" + lagerortId))
                    .collect(Collectors.toList());
            
            // Wenn die Anzahl der Zeilen gleich ist, wurde nichts gelöscht
            if (zeilen.size() == gefilterteZeilen.size()) {
                return false;
            }
            
            Files.write(pfad, gefilterteZeilen, StandardOpenOption.TRUNCATE_EXISTING);
            return true;
        } catch (IOException e) {
            System.err.printf("Fehler beim Löschen des Lagerorts mit ID %s: %s%n", lagerortId, e.getMessage());
            return false;
        }
    }

    @Override
    public boolean existiert(String lagerortId) {
        try {
            List<String> zeilen = Files.readAllLines(Paths.get(datei.getPath()));
            for (String zeile : zeilen) {
                if (zeile.contains("ID=" + lagerortId)) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.err.printf("Fehler beim Prüfen der Existenz des Lagerorts mit ID %s: %s%n", lagerortId, e.getMessage());
        }
        
        return false;
    }
    
    /**
     * Wandelt eine Zeile aus der Datei in ein Lagerort-Objekt um.
     * Beispiel: Lagerort[ID=1-101, Lager, Stockwerk 1, Raum 101]
     * 
     * @param zeile Die zu parsende Zeile
     * @return Das erstellte Lagerort-Objekt
     */
    private Lagerort parseLagerort(String zeile) {
        // Format: ID=3-303, Notfalldepot, Stockwerk 3, Raum 303
        String inhalt = zeile.substring(zeile.indexOf('=') + 1); // Entfernt "ID="
        
        // Teile extrahieren
        String[] teile = inhalt.split(", ");
        
        // ID extrahieren
        String id = teile[0].trim(); // "3-303"
        
        // Bezeichnung ist der zweite Teil
        String bezeichnung = teile[1].trim(); // "Notfalldepot"
        
        // Stockwerk extrahieren (Format: "Stockwerk 3")
        String stockwerkStr = teile[2].substring(10).trim(); // Entfernt "Stockwerk "
        int stockwerk = Integer.parseInt(stockwerkStr);
        
        // Raumnummer extrahieren (Format: "Raum 303")
        String raumnummer = teile[3].substring(5).trim(); // Entfernt "Raum "
        
        return new Lagerort(bezeichnung, stockwerk, raumnummer);
    }
} 