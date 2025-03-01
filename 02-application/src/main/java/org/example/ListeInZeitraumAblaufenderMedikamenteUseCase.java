package org.example;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

public class ListeInZeitraumAblaufenderMedikamenteUseCase {

    private MedikamenteSpeicher medikamenteSpeicher;

    public ListeInZeitraumAblaufenderMedikamenteUseCase(MedikamenteSpeicher medikamenteSpeicher) {
        this.medikamenteSpeicher = medikamenteSpeicher;
    }

    public List<Medikament> findeMedikamenteDieInXWochenAblaufen(int wochen) {
        List<Medikament> alleMedikamenteListe = medikamenteSpeicher.findeAlleMedikamente();
        List<Medikament> wirdAblaufenListe = new ArrayList<>();

        for (Medikament medikament : alleMedikamenteListe) {
            if (medikament.laeuftInXWochenAb(wochen)) {
                wirdAblaufenListe.add(medikament);
            }
        }
        return wirdAblaufenListe;
    }

    public List<Medikament> findeMedikamenteDieZuBestimmtemDatumAblaufen(YearMonth datum){
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
