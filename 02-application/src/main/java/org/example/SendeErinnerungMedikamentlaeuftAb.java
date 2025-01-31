package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.util.List;

public class SendeErinnerungMedikamentlaeuftAb {
    private final MedikamenteSpeicher medikamenteSpeicher;
    ListeInZeitraumAblaufenderMedikamenteUseCase listeInZeitraumAblaufenderMedikamenteUseCase;
    AbgelaufeneMedikamenteUseCase abgelaufeneMedikamenteUseCase;

    public SendeErinnerungMedikamentlaeuftAb(MedikamenteSpeicher medikamenteSpeicher) {
        this.medikamenteSpeicher = medikamenteSpeicher;
        this.listeInZeitraumAblaufenderMedikamenteUseCase = new ListeInZeitraumAblaufenderMedikamenteUseCase(medikamenteSpeicher);
        this.abgelaufeneMedikamenteUseCase = new AbgelaufeneMedikamenteUseCase(medikamenteSpeicher);
    }

    public void sendeErinnerungen() {
        List<Medikament> ablaufendeMedikamente = listeInZeitraumAblaufenderMedikamenteUseCase.wirdIn2WochenAblaufen();
        List<Medikament> abgelaufeneMedikamente = abgelaufeneMedikamenteUseCase.abgelaufeneMedikamente();
        // (Wer "wirklich" E‑Mails verschicken will, kann hier ein Notification-Interface ansprechen)


        Path dateiPfadAblaufende = Paths.get("ablaufende_medikamente.txt");
        Path dateiPfadAbgelaufene = Paths.get("abgelaufene_medikamente.txt");


        try {
            for (Medikament med: ablaufendeMedikamente) {
                String output = med.getMedikamentenName() + "' läuft am " + med.getAblaufDatum() + " ab.\n";
                Files.write(dateiPfadAblaufende, output.getBytes(), StandardOpenOption.APPEND, StandardOpenOption.CREATE);
            }
            for (Medikament med: abgelaufeneMedikamente) {
                String output = med.getMedikamentenName() + "' ist abgelaufen" ;
                Files.write(dateiPfadAbgelaufene, output.getBytes(), StandardOpenOption.APPEND, StandardOpenOption.CREATE);
            }
        } catch (IOException e) {
            System.err.println("Fehler beim Schreiben in die Datei: " + e.getMessage());
        }
    }
}
