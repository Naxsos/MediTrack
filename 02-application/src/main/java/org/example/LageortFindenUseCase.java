package org.example;

import java.util.List;
import java.util.Optional;

public class LageortFindenUseCase {
    
    private LagerortSpeicher lagerortSpeicher;

    public LageortFindenUseCase(LagerortSpeicher lagerortSpeicher) {
        this.lagerortSpeicher = lagerortSpeicher;
    }

    public List<Lagerort> findeLagerorteViaBezeichnung(String bezeichnung) {
        return lagerortSpeicher.findeViaBezeichnung(bezeichnung);
    }
    
    public Optional<Lagerort> findeViaId(String lagerortId){
        return lagerortSpeicher.findeViaId(lagerortId);
    }

    public List<Lagerort> findeAlleLagerorte() {
        return lagerortSpeicher.findeAlle();
    }
}
