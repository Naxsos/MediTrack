package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MedikamenteController {
    private final MedikamenteVerwaltenUseCase medikamenteVerwaltenUseCase;
    private final FindeMedikamenteUseCase findeMedikamenteUseCase;
    private final ListeInZeitraumAblaufenderMedikamenteUseCase listeInZeitraumAblaufenderMedikamenteUseCase;
    private final AbgelaufeneMedikamenteUseCase abgelaufeneMedikamenteUseCase;
    private List<MedikamenteUI> ui;

    public MedikamenteController(
            MedikamenteVerwaltenUseCase medikamenteVerwaltenUseCase,
            ListeInZeitraumAblaufenderMedikamenteUseCase listeInZeitraumAblaufenderMedikamenteUseCase,
            FindeMedikamenteUseCase findeMedikamenteUseCase,
            AbgelaufeneMedikamenteUseCase abgelaufeneMedikamenteUseCase
    ) {
        this.medikamenteVerwaltenUseCase = medikamenteVerwaltenUseCase;
        this.findeMedikamenteUseCase = findeMedikamenteUseCase;
        this.listeInZeitraumAblaufenderMedikamenteUseCase = listeInZeitraumAblaufenderMedikamenteUseCase;
        this.abgelaufeneMedikamenteUseCase = abgelaufeneMedikamenteUseCase;
        this.ui = new ArrayList<>();
    }

    public void addObserver(MedikamenteUI ui) {
        this.ui.add(ui);
    }

    public void initialisiereMedikamentenlisten() {
        for (MedikamenteUI medikamente : ui) {
            medikamente.aktualisiereMedikamentenliste(convertToStrings(findeMedikamenteUseCase.listeAllerMedikamente()));
            medikamente.aktualisiereBaldAblaufendeMedikamente(convertToStrings(listeInZeitraumAblaufenderMedikamenteUseCase.findeMedikamenteDieInXWochenAblaufen(2)));
            medikamente.aktualisiereAbgelaufeneMedikamente(convertToStrings(abgelaufeneMedikamenteUseCase.abgelaufeneMedikamente()));
        }
    }

    public void erstelleMedikament(MedikamentDTO dto) {
        medikamenteVerwaltenUseCase.erstelleMedikament(
                Integer.parseInt(dto.pzn), dto.seriennummer, dto.chargennummer, dto.name, dto.wirkstoff, dto.ablaufdatum, dto.lagerort
        );
        initialisiereMedikamentenlisten();
    }

    public void loescheMedikament(int index) {
        List<Medikament> medikamente = findeMedikamenteUseCase.listeAllerMedikamente();
        if (index >= 0 && index < medikamente.size()) {
            medikamenteVerwaltenUseCase.medikamentEntfernen(medikamente.get(index).getUi().toString());
        }
        initialisiereMedikamentenlisten();
    }

    private List<String> convertToStrings(List<Medikament> medikamente) {
        return medikamente.stream()
                .map(Medikament::toString)
                .collect(Collectors.toList());
    }
}
