package de.meditrack;

import java.util.List;
import java.util.Optional;

public interface LagerortSpeicher {

    Lagerort speichern(Lagerort lagerort);
    boolean loeschen(String lagerortId);
    boolean existiert(String lagerortId);

    Optional<Lagerort> findeViaId(String lagerortId);
    List<Lagerort> findeViaBezeichnung(String bezeichnung);
    List<Lagerort> findeAlle();
  
} 