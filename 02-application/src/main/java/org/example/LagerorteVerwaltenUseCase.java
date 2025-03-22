package org.example;

import java.util.List;
import java.util.Optional;

public class LagerorteVerwaltenUseCase {
    private LagerortSpeicher lagerortSpeicher;

    public LagerorteVerwaltenUseCase(LagerortSpeicher lagerortSpeicher) {
        this.lagerortSpeicher = lagerortSpeicher;
    }

    public Lagerort erstelleLagerort(String bezeichnung, int stockwerk, String raumnummer) {

        String lagerortIdString = stockwerk + "-" + raumnummer;
        
        if (lagerortSpeicher.existiert(lagerortIdString)) {
            throw new IllegalArgumentException("Ein Lagerort mit Stockwerk " + stockwerk + 
                " und Raumnummer " + raumnummer + " existiert bereits.");
        }
        
        Lagerort lagerort = new Lagerort(bezeichnung, stockwerk, raumnummer);
        
        System.out.println("Lagerort erstellt: " + lagerort);
        return lagerortSpeicher.speichern(lagerort);
    }


    public boolean loescheLagerort(String lagerortId) {

        if (!lagerortSpeicher.existiert(lagerortId)) {
            System.out.println("Lagerort mit ID " + lagerortId + " nicht gefunden.");
            return false;
        }
        
        boolean erfolg = lagerortSpeicher.loeschen(lagerortId);
        if (erfolg) {
            System.out.println("Lagerort mit ID " + lagerortId + " wurde gelöscht.");
        } else {
            System.out.println("Lagerort mit ID " + lagerortId + " konnte nicht gelöscht werden.");
        }
        return erfolg;
    }

    public Optional<Lagerort> aktualisiereBezeichnung(String lagerortId, String neueBezeichnung) {

        Optional<Lagerort> lagerortOptional = lagerortSpeicher.findeViaId(lagerortId);
        
        if (lagerortOptional.isPresent()) {
            Lagerort lagerort = lagerortOptional.get();
            lagerort.setBezeichnung(neueBezeichnung);
            System.out.println("Bezeichnung des Lagerorts mit ID " + lagerortId + " aktualisiert auf: " + neueBezeichnung);
            return Optional.of(lagerortSpeicher.speichern(lagerort));
        } else {
            System.out.println("Lagerort mit ID " + lagerortId + " nicht gefunden. Bezeichnung konnte nicht aktualisiert werden.");
        }
        
        return Optional.empty();
    }
    
    public List<Lagerort> findeAlleLagerorte() {
        return lagerortSpeicher.findeAlle();
    }
    
    public Optional<Lagerort> findeLagerortViaId(String lagerortId) {
        return lagerortSpeicher.findeViaId(lagerortId);
    }
    
    public List<Lagerort> findeLagerorteViaBezeichnung(String bezeichnung) {
        return lagerortSpeicher.findeViaBezeichnung(bezeichnung);
    }
}
