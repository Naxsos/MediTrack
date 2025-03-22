package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MedikamenteController {
    private final ErstelleMedikamentUseCase erstelleMedikamentUseCase;
    private final AlleMedikamenteAusgebenUseCase alleMedikamenteAusgebenUseCase;
    private final EntferneMedikamentUseCase entferneMedikamentUseCase;
    private final ListeInZeitraumAblaufenderMedikamenteUseCase listeInZeitraumAblaufenderMedikamenteUseCase;
    private final AbgelaufeneMedikamenteUseCase abgelaufeneMedikamenteUseCase;
    private List<MedikamenteUI> ui;

    public MedikamenteController(
            ErstelleMedikamentUseCase erstelleMedikamentUseCase,
            AlleMedikamenteAusgebenUseCase alleMedikamenteAusgebenUseCase,
            EntferneMedikamentUseCase entferneMedikamentUseCase,
            ListeInZeitraumAblaufenderMedikamenteUseCase listeInZeitraumAblaufenderMedikamenteUseCase,
            AbgelaufeneMedikamenteUseCase abgelaufeneMedikamenteUseCase
    ) {
        this.erstelleMedikamentUseCase = erstelleMedikamentUseCase;
        this.alleMedikamenteAusgebenUseCase = alleMedikamenteAusgebenUseCase;
        this.entferneMedikamentUseCase = entferneMedikamentUseCase;
        this.listeInZeitraumAblaufenderMedikamenteUseCase = listeInZeitraumAblaufenderMedikamenteUseCase;
        this.abgelaufeneMedikamenteUseCase = abgelaufeneMedikamenteUseCase;
        this.ui = new ArrayList<>();
    }

    public void addObserver(MedikamenteUI ui) {
        this.ui.add(ui);
    }

    public void initialisiereMedikamentenlisten() {
        for (MedikamenteUI medikamente : ui) {
            medikamente.aktualisiereMedikamentenliste(convertToStrings(alleMedikamenteAusgebenUseCase.listeAllerMedikamente()));
            medikamente.aktualisiereBaldAblaufendeMedikamente(convertToStrings(listeInZeitraumAblaufenderMedikamenteUseCase.findeMedikamenteDieInXWochenAblaufen(2)));
            medikamente.aktualisiereAbgelaufeneMedikamente(convertToStrings(abgelaufeneMedikamenteUseCase.abgelaufeneMedikamente()));
        }
    }

    public void erstelleMedikament(MedikamentDTO dto) {
        erstelleMedikamentUseCase.erstelleMedikament(
                Integer.parseInt(dto.pzn), dto.seriennummer, dto.chargennummer, dto.name, dto.wirkstoff, dto.ablaufdatum, dto.lagerort
        );
        initialisiereMedikamentenlisten();
    }

    public void loescheMedikament(int index) {
        List<Medikament> medikamente = alleMedikamenteAusgebenUseCase.listeAllerMedikamente();
        if (index >= 0 && index < medikamente.size()) {
            entferneMedikamentUseCase.medikamentEntfernen(medikamente.get(index).getUi().toString());
        }
        initialisiereMedikamentenlisten();
    }

    private List<String> convertToStrings(List<Medikament> medikamente) {
        return medikamente.stream()
                .map(Medikament::toString)
                .collect(Collectors.toList());
    }
}
