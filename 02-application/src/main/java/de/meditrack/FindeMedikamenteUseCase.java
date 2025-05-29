package de.meditrack;

import java.util.List;
import java.util.Optional;

public class FindeMedikamenteUseCase {

    private MedikamenteSpeicher medikamenteSpeicher;

    public FindeMedikamenteUseCase(MedikamenteSpeicher medikamenteSpeicher) {
        this.medikamenteSpeicher = medikamenteSpeicher;
    }

    public Optional<Medikament> findeMedikamentViaUI(UniqueIdentifier ui){
        return medikamenteSpeicher.findeMedikamentViaUI(ui);
    }
    public List<Medikament> findeViaWirkstoff(String wirkstoff){
        return medikamenteSpeicher.findeViaWirkstoff(wirkstoff);
    }

    public List<Medikament> findeViaMedikamentName(String name){
        return medikamenteSpeicher.findeViaMedikamentName(name);
    }

    public List<Medikament> listeAllerMedikamente() {
        return medikamenteSpeicher.findeAlleMedikamente();
    }

}
