package org.example;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        DateiMedikamenteSpeicher dateiSpeicher = new DateiMedikamenteSpeicher("test.txt") ;
        DateiMedikamenteSpeicherAdapter dateiSpeicherAdapter = new DateiMedikamenteSpeicherAdapter(dateiSpeicher);

        ErstelleMedikamentUseCase erstellenService = new ErstelleMedikamentUseCase(dateiSpeicherAdapter);
        EntferneMedikamentUseCase entfernenService = new EntferneMedikamentUseCase(dateiSpeicherAdapter);
        ListeAllerMedikamenteUseCase listeAllerMedikamenteUseCase = new ListeAllerMedikamenteUseCase(dateiSpeicherAdapter);

        erstellenService.erstelleMedikament(21234567,"ABC1234567890XYZ", "BATCH001", "Kreativer Name mit ganz vielen xyz", "Zanax", "2025-02");
        erstellenService.erstelleMedikament(23414564, "XYZ1234234290ABC", "BATCH004", "Kreativer Name mit ganz vielen abx", "Soledum", "2025-06");
        List<Medikament> liste = listeAllerMedikamenteUseCase.listeAllerMedikamente();
        for (Medikament medikament : liste){
            System.out.println(medikament);
        }
        entfernenService.medikamentEntfernen("01 23414564 21 XYZ1234234290ABC 10 BATCH004 17 06-2025");

    }
}