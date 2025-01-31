package org.example;

import java.util.List;

public class FindeMedikamenteUseCase {

    private MedikamenteSpeicher medikamenteSpeicher;

    public FindeMedikamenteUseCase(MedikamenteSpeicher medikamenteSpeicher) {
        this.medikamenteSpeicher = medikamenteSpeicher;
    }

    public Medikament findeMedikamentViaUI(UniqueIdentifier ui){
        return medikamenteSpeicher.findeMedikamentViaUI(ui);
    }
    public List<Medikament> findeViaWirkstoff(String wirkstoff){
        return medikamenteSpeicher.findeViaWirkstoff(wirkstoff);
    }

    public List<Medikament> findeViaMedikamentName(String name){
        return medikamenteSpeicher.findeViaMedikamentName(name);
    }

}
