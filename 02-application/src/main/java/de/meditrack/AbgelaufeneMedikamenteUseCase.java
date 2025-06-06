package de.meditrack;

import java.util.List;
import java.util.stream.Collectors;

public class AbgelaufeneMedikamenteUseCase {
    private MedikamenteSpeicher medikamenteSpeicher;

    public AbgelaufeneMedikamenteUseCase(MedikamenteSpeicher medikamenteSpeicher) {
        this.medikamenteSpeicher = medikamenteSpeicher;
    }

    public List<Medikament> abgelaufeneMedikamente() {
        return medikamenteSpeicher.findeAlleMedikamente().stream()
                .filter(Medikament::istAbgelaufen)
                .collect(Collectors.toList());
    }
}
