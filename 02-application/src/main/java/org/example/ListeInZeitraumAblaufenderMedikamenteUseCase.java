package org.example;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

public class ListeInZeitraumAblaufenderMedikamenteUseCase {

    private MedikamenteSpeicher medikamenteSpeicher;

    public ListeInZeitraumAblaufenderMedikamenteUseCase(MedikamenteSpeicher medikamenteSpeicher) {
        this.medikamenteSpeicher = medikamenteSpeicher;
    }

    public List<Medikament> wirdIn1WocheAblaufen() {
        List<Medikament> alleMedikamenteListe = medikamenteSpeicher.findeAlleMedikamente();
        List<Medikament> wirdAblaufenListe = new ArrayList<>();
        LocalDate heute = LocalDate.now();
        LocalDate inZweiWochen = heute.plusWeeks(1);

        for (Medikament medikament : alleMedikamenteListe) {
            LocalDate ablaufDatum = LocalDateParser.parseJahrMonatZuVollemDatum(medikament.getAblaufDatum());
            // Optional: Sicherstellen, dass das Medikament noch nicht abgelaufen ist
            if (!ablaufDatum.isBefore(heute) && ablaufDatum.isBefore(inZweiWochen)) {
                wirdAblaufenListe.add(medikament);
            }
        }
        return wirdAblaufenListe;
    }

    // HIER WURDE DRY VERLETZT... DAS MUSS ANGEPASST WERDEN
    public List<Medikament> wirdIn2WochenAblaufen() {
        List<Medikament> alleMedikamenteListe = medikamenteSpeicher.findeAlleMedikamente();
        List<Medikament> wirdAblaufenListe = new ArrayList<>();
        LocalDate heute = LocalDate.now();
        LocalDate inZweiWochen = heute.plusWeeks(2);

        for (Medikament medikament : alleMedikamenteListe) {
            LocalDate ablaufDatum = LocalDateParser.parseJahrMonatZuVollemDatum(medikament.getAblaufDatum());
            // Optional: Sicherstellen, dass das Medikament noch nicht abgelaufen ist
            if (!ablaufDatum.isBefore(heute) && ablaufDatum.isBefore(inZweiWochen)) {
                wirdAblaufenListe.add(medikament);
            }
        }
        return wirdAblaufenListe;
    }

    public List<Medikament> wirdIn4WochenAblaufen() {
        List<Medikament> alleMedikamenteListe = medikamenteSpeicher.findeAlleMedikamente();
        List<Medikament> wirdAblaufenListe = new ArrayList<>();
        LocalDate heute = LocalDate.now();
        LocalDate inZweiWochen = heute.plusWeeks(4);

        for (Medikament medikament : alleMedikamenteListe) {
            LocalDate ablaufDatum = LocalDateParser.parseJahrMonatZuVollemDatum(medikament.getAblaufDatum());
            // Optional: Sicherstellen, dass das Medikament noch nicht abgelaufen ist
            if (!ablaufDatum.isBefore(heute) && ablaufDatum.isBefore(inZweiWochen)) {
                wirdAblaufenListe.add(medikament);
            }
        }
        return wirdAblaufenListe;
    }
    public List<Medikament> wirdAblaufenzuBestimmtemDatum(YearMonth datum){
        List<Medikament> alleMedikamenteListe = medikamenteSpeicher.findeAlleMedikamente();
        List<Medikament> wirdAblaufenListe = new ArrayList<Medikament>();
        for (Medikament medikament : alleMedikamenteListe) {
            if (medikament.getAblaufDatum().equals(datum)) {
                wirdAblaufenListe.add(medikament);
            }
        }
        return wirdAblaufenListe;
    }
}
