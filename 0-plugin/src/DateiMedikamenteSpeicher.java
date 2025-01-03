import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.YearMonth;
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

    @Override
    public List<Medikament> findeAlleMedikamente() {
        return List.of();
    }
}
