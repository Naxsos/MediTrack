package de.meditrack;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class DateiMedikamenteSpeicher implements MedikamenteSpeicher {
    private final File datei;

    public DateiMedikamenteSpeicher(String filePath) {
        this.datei = new File(filePath);
        try {
            if (!datei.exists()) {
                datei.createNewFile(); // Erstellt die Datei, falls sie nicht existiert
            }
        } catch (IOException e) {
            throw new RuntimeException("Fehler beim Initialisieren der Datei", e);
        }
    }

    @Override
    public Medikament speichern(Medikament medikament) {

        String line = medikament.toString();
        String filepath = datei.getPath();
        
        try {
            // Zuerst prüfen, ob der Lagerort bereits existiert
            if (!existiert(medikament.getUi())) {
                Files.writeString(Paths.get(filepath), line + System.lineSeparator(), StandardOpenOption.APPEND, StandardOpenOption.CREATE);
            }
            return medikament;
        } catch (IOException e) {
            System.err.printf("Fehler beim Speichern des Medikaments: %s%n", e.getMessage());
            throw new RuntimeException("Fehler beim Speichern des Medikaments", e);
        }
    }

    @Override
    public void entfernenViaUI(String ui) {
        String dateipfad = datei.getPath();
        Path pfad = Paths.get(dateipfad);
        try {
            List<String> alleZeilen = Files.readAllLines(pfad);
            List<String> gefilterteDateiZeilen = alleZeilen.stream()
                    .filter(line -> !line.startsWith(ui + ",")) // Filtert Zeilen mit der ID aus
                    .collect(Collectors.toList());

            // Prüfe, ob die UI existiert
            if (alleZeilen.size() == gefilterteDateiZeilen.size()) {
                throw new IllegalArgumentException("Medikament mit UI " + ui + " existiert nicht");
            }

            Files.write(pfad, gefilterteDateiZeilen, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (NumberFormatException | IOException ex) {
            throw new IllegalStateException("Fehler beim Entfernen des Medikaments: " + ex.getLocalizedMessage(), ex);
        }
    }

    @Override
    public Optional<Medikament> findeMedikamentViaUI(UniqueIdentifier ui) {
        try {
            List<String> alleEinträge = Files.readAllLines(Paths.get(datei.getPath()));
            for (String eintrag : alleEinträge) {
                Medikament medikament = parseMedikament(eintrag);
                if (medikament.getUi().equals(ui)) {
                    return Optional.of(medikament);
                }
            }
        } catch (IOException e) {
            System.err.printf("Fehler beim Suchen nach Medikament via UI: %s%n", e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public List<Medikament> findeViaMedikamentName(String name) {
        List<Medikament> gefundeneMedikamente = new ArrayList<>();
        try {
            List<String> alleEinträge = Files.readAllLines(Paths.get(datei.getPath()));
            for (String eintrag : alleEinträge) {
                Medikament medikament = parseMedikament(eintrag);
                if (medikament.getMedikamentenName().toLowerCase().contains(name.toLowerCase())) {
                    gefundeneMedikamente.add(medikament);
                }
            }
        } catch (IOException e) {
            System.err.printf("Fehler beim Suchen nach Medikament via Name: %s%n", e.getMessage());
        }
        return gefundeneMedikamente;
    }

    @Override
    public List<Medikament> findeViaWirkstoff(String wirkstoff) {
        List<Medikament> gefundeneMedikamente = new ArrayList<>();
        try {
            List<String> alleEinträge = Files.readAllLines(Paths.get(datei.getPath()));
            for (String eintrag : alleEinträge) {
                Medikament medikament = parseMedikament(eintrag);
                if (medikament.getWirkstoffBezeichnung().toLowerCase().contains(wirkstoff.toLowerCase())) {
                    gefundeneMedikamente.add(medikament);
                }
            }
        } catch (IOException e) {
            System.err.printf("Fehler beim Suchen nach Medikament via Wirkstoff: %s%n", e.getMessage());
        }
        return gefundeneMedikamente;
    }

    @Override
    public List<Medikament> findByVerfallsdatumBefore(YearMonth date) {
        List<Medikament> ablaufendeMedikamente = new ArrayList<>();
        try {
            List<String> alleEinträge = Files.readAllLines(Paths.get(datei.getPath()));
            for (String eintrag : alleEinträge) {
                Medikament medikament = parseMedikament(eintrag);
                if (medikament.getAblaufDatum().isBefore(date) || medikament.getAblaufDatum().equals(date)) {
                    ablaufendeMedikamente.add(medikament);
                }
            }
        } catch (IOException e) {
            System.err.printf("Fehler beim Suchen nach ablaufenden Medikamenten: %s%n", e.getMessage());
        }
        return ablaufendeMedikamente;
    }

    @Override
    public List<Medikament> findeViaLagerortId(String lagerortId) {
        List<Medikament> medikamenteMitLagerort = new ArrayList<>();
        try {
            List<String> alleEinträge = Files.readAllLines(Paths.get(datei.getPath()));
            for (String eintrag : alleEinträge) {
                if (eintrag.contains(lagerortId)) {
                    medikamenteMitLagerort.add(parseMedikament(eintrag));
                }
            }
        } catch (IOException e) {
            System.out.println("Fehler beim Suchen nach Lagerort: " + e.getMessage());
        }
        return medikamenteMitLagerort;
    }

    @Override
    public Medikament aktualisiereLagerort(UniqueIdentifier medikamentId, String neuerLagerortId) {
        try {
            // Medikament finden
            Optional<Medikament> optMedikament = findeMedikamentViaUI(medikamentId);
            if (optMedikament.isEmpty()) {
                System.err.println("Medikament mit ID " + medikamentId + " nicht gefunden.");
                return null;
            }
            
            Medikament altMedikament = optMedikament.get();
            
            // Neues Medikament mit aktualisiertem Lagerort erstellen
            Medikament neuMedikament = new Medikament.Builder(altMedikament.getUi())
                .serienNummer(altMedikament.getSerienNummer())
                .pzn(altMedikament.getPzn())
                .chargenNummer(altMedikament.getChargenNummer())
                .medikamentenName(altMedikament.getMedikamentenName())
                .wirkstoffBezeichnung(altMedikament.getWirkstoffBezeichnung())
                .hersteller(altMedikament.getHersteller())
                .ablaufDatum(altMedikament.getAblaufDatum())
                .lagerortId(LagerortID.fromString(neuerLagerortId))
                .build();
            
            // Altes Medikament entfernen
            entfernenViaUI(medikamentId.toString());
            
            // Neues Medikament speichern
            speichern(neuMedikament);
            
            return neuMedikament;
        } catch (Exception e) {
            System.err.printf("Fehler beim Aktualisieren des Lagerorts: %s%n", e.getMessage());
            return null;
        }
    }

    private Medikament parseMedikament(String eintrag) {
        List<String> listeFuerWerteProEintrag = List.of(eintrag.split(","));
        UniqueIdentifier ui = UniqueIdentifier.fromString(listeFuerWerteProEintrag.get(0));
        String chargenNummer = listeFuerWerteProEintrag.get(1).trim();
        String medikamentenName = listeFuerWerteProEintrag.get(2).trim();
        String wirkstoffBezeichnung = listeFuerWerteProEintrag.get(3).trim();
        String hersteller = listeFuerWerteProEintrag.get(4).trim();
        String darreichungsform = listeFuerWerteProEintrag.get(5).trim();
        String dosierung = listeFuerWerteProEintrag.get(6).trim();
        YearMonth ablaufDatum = LocalDateParser.parseDate(listeFuerWerteProEintrag.get(7).trim(), Konstanten.ABLAUF_DATUM_FORMAT);
        LagerortID lagerortId = LagerortID.fromString(listeFuerWerteProEintrag.get(8).trim());

        
        return new Medikament.Builder(ui)
                .serienNummer(new Seriennummer(ui.getSerienNummer()))
                .pzn(new PZN(ui.getPzn()))
                .chargenNummer(new Chargennummer(chargenNummer))
                .medikamentenName(medikamentenName)
                .wirkstoffBezeichnung(wirkstoffBezeichnung)
                .hersteller(hersteller)
                .ablaufDatum(ablaufDatum)
                .lagerortId(lagerortId)
                .build();
    }

    @Override
    public List<Medikament> findeAlleMedikamente() {
        List<Medikament> alleMedikamente = new ArrayList<>();
        try {
            List<String> alleEinträgeInZeichenkettenForm = Files.readAllLines(Paths.get(datei.getPath()));
            for (String eintrag : alleEinträgeInZeichenkettenForm){
                alleMedikamente.add(parseMedikament(eintrag));
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return alleMedikamente;
    }

    @Override
    public boolean existiert(UniqueIdentifier ui) {
        List<Medikament> alleMedikamente = findeAlleMedikamente();
        for (Medikament medikament : alleMedikamente) {
            if (medikament.getUi().equals(ui)) {
                return true;
            }
        }
        return false;
    }
}
