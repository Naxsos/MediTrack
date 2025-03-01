package org.example;

import javax.swing.text.html.parser.Parser;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
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
            Files.writeString(Paths.get(filepath), line + System.lineSeparator(), StandardOpenOption.APPEND, StandardOpenOption.CREATE);
        } catch (IOException ex) {
            System.out.printf("Fehler beim Speichern des Medikaments: %s%n", ex.getMessage());
        }
        return medikament;
    }

    @Override
    public void entfernenViaUI(String ui) {
        String dateipfad = datei.getPath();
        Path pfad = Paths.get(dateipfad);
        try {
            List<String> gefilterteDateiZeilen = Files.readAllLines(pfad).stream()
                    .filter(line -> !line.startsWith(ui + ",")) // Filtert Zeilen mit der ID aus
                    .collect(Collectors.toList());

            Files.write(pfad, gefilterteDateiZeilen, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (NumberFormatException | IOException ex) {
            System.err.printf("Fehler beim Laden der Datei: %s%n", ex.getLocalizedMessage());
        }
    }

    @Override
    public Medikament findeMedikamentViaUI(UniqueIdentifier ui) {
        return null;
    }

    @Override
    public List<Medikament> findeViaMedikamentName(String name) {
        return List.of();
    }

    @Override
    public List<Medikament> findeViaWirkstoff(String wirkstoff) {
        return List.of();
    }

    @Override
    public List<Medikament> findByVerfallsdatumBefore(YearMonth date) {
        return List.of();
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
        return new Medikament.Builder(ui)
                .serienNummer(new Seriennummer(ui.getSerienNummer()))
                .pzn(new PZN(ui.getPzn()))
                .chargenNummer(new Chargennummer(chargenNummer))
                .medikamentenName(medikamentenName)
                .wirkstoffBezeichnung(wirkstoffBezeichnung)
                .ablaufDatum(ablaufDatum)
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

}
