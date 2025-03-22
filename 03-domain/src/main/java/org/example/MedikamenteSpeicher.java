package org.example;

import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

public interface MedikamenteSpeicher {
    Medikament speichern(Medikament medikament);
    void entfernenViaUI(String ui);
    boolean existiert(UniqueIdentifier ui);
    
    Optional<Medikament> findeMedikamentViaUI(UniqueIdentifier ui);
    List<Medikament> findeViaMedikamentName(String name);             // Suche nach Medikamentennamen
    List<Medikament> findeViaWirkstoff(String wirkstoff);
    List<Medikament> findByVerfallsdatumBefore(YearMonth date);
    List<Medikament> findeAlleMedikamente();
    
    // Methoden für Lagerort-Beziehungen
    List<Medikament> findeViaLagerortId(String lagerortId);
    Medikament aktualisiereLagerort(UniqueIdentifier medikamentId, String neuerLagerortId);
}

