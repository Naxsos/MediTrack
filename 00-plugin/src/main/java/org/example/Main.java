package org.example;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.List;
import java.util.TimeZone;


public class Main {
    public static void main(String[] args) throws SchedulerException {
        DateiMedikamenteSpeicher dateiSpeicher = new DateiMedikamenteSpeicher("test.txt") ;
        DateiMedikamenteSpeicherAdapter dateiSpeicherAdapter = new DateiMedikamenteSpeicherAdapter(dateiSpeicher);

        ErstelleMedikamentUseCase erstellenService = new ErstelleMedikamentUseCase(dateiSpeicherAdapter);
        EntferneMedikamentUseCase entfernenService = new EntferneMedikamentUseCase(dateiSpeicherAdapter);
        AlleMedikamenteAusgebenUseCase listeAllerMedikamenteUseCase = new AlleMedikamenteAusgebenUseCase(dateiSpeicherAdapter);

        erstellenService.erstelleMedikament(21234567,"ABC1234567890XYZ", "BATCH001", "Kreativer Name mit ganz vielen xyz", "Zanax", "2025-02");
        erstellenService.erstelleMedikament(23414564, "XYZ1234234290ABC", "BATCH004", "Kreativer Name mit ganz vielen abx", "Soledum", "2025-06");
        List<Medikament> liste = listeAllerMedikamenteUseCase.listeAllerMedikamente();
        SendeErinnerungMedikamentlaeuftAb sendeErinnerungMedikamentlaeuftAb = new SendeErinnerungMedikamentlaeuftAb(dateiSpeicherAdapter);
        sendeErinnerungMedikamentlaeuftAb.sendeErinnerungen();
        entfernenService.medikamentEntfernen("01 23414564 21 XYZ1234234290ABC 10 BATCH004 17 06-2025");

        //AllgemeinerScheduler allgemeinerScheduler = new AllgemeinerScheduler(dateiSpeicherAdapter);


    }
}