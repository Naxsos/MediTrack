package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class LagerorteController {
    private final LagerorteVerwaltenUseCase lagerorteVerwaltenUseCase;
    private List<LagerorteUI> observers;

    public LagerorteController(LagerorteVerwaltenUseCase lagerorteVerwaltenUseCase) {
        this.lagerorteVerwaltenUseCase = lagerorteVerwaltenUseCase;
        this.observers = new ArrayList<>();
    }

    public void addObserver(LagerorteUI ui) {
        this.observers.add(ui);
    }

    public void initialisiereLagerortListe() {
        List<Lagerort> lagerorte = lagerorteVerwaltenUseCase.findeAlleLagerorte();
        for (LagerorteUI observer : observers) {
            observer.aktualisiereLagerortListe(convertToStrings(lagerorte));
        }
    }

    public void erstelleLagerort(LagerortDTO dto) {
        try {
            int stockwerk = Integer.parseInt(dto.stockwerk);
            lagerorteVerwaltenUseCase.erstelleLagerort(dto.bezeichnung, stockwerk, dto.raumnummer);
            initialisiereLagerortListe();
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Stockwerk muss eine Zahl sein.");
        } catch (IllegalArgumentException e) {
            throw e;
        }
    }

    public void loescheLagerort(int index) {
        List<Lagerort> lagerorte = lagerorteVerwaltenUseCase.findeAlleLagerorte();
        if (index >= 0 && index < lagerorte.size()) {
            lagerorteVerwaltenUseCase.loescheLagerort(lagerorte.get(index).getLagerortId().toString());
        }
        initialisiereLagerortListe();
    }

    public void aktualisiereBezeichnung(int index, String neueBezeichnung) {
        List<Lagerort> lagerorte = lagerorteVerwaltenUseCase.findeAlleLagerorte();
        if (index >= 0 && index < lagerorte.size()) {
            lagerorteVerwaltenUseCase.aktualisiereBezeichnung(
                lagerorte.get(index).getLagerortId().toString(), neueBezeichnung);
        }
        initialisiereLagerortListe();
    }

    private List<String> convertToStrings(List<Lagerort> lagerorte) {
        return lagerorte.stream()
                .map(Lagerort::toString)
                .collect(Collectors.toList());
    }
} 