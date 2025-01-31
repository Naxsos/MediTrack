package org.example;

import java.util.List;

public class AlleMedikamenteAusgebenUseCase {
    private MedikamenteSpeicher medikamenteSpeicher;

    public AlleMedikamenteAusgebenUseCase(MedikamenteSpeicher medikamenteSpeicher) {
        this.medikamenteSpeicher = medikamenteSpeicher;
    }

    public List<Medikament> listeAllerMedikamente() {
        return medikamenteSpeicher.findeAlleMedikamente();
    }
}
