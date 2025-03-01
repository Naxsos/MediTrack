package org.example;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import javax.swing.*;
import java.util.List;
import java.util.TimeZone;


public class Main {
    public static void main(String[] args) throws SchedulerException {
        DateiMedikamenteSpeicher dateiSpeicher = new DateiMedikamenteSpeicher("test.txt") ;

        ErstelleMedikamentUseCase erstellenService = new ErstelleMedikamentUseCase(dateiSpeicher);
        EntferneMedikamentUseCase entfernenService = new EntferneMedikamentUseCase(dateiSpeicher);
        AlleMedikamenteAusgebenUseCase listeAllerMedikamenteUseCase = new AlleMedikamenteAusgebenUseCase(dateiSpeicher);
        ListeInZeitraumAblaufenderMedikamenteUseCase baldAblaufendeMedikamenteUseCase = new ListeInZeitraumAblaufenderMedikamenteUseCase(dateiSpeicher);
        AbgelaufeneMedikamenteUseCase abgelaufeneMedikamentUseCase = new AbgelaufeneMedikamenteUseCase(dateiSpeicher);

        erstellenService.erstelleMedikament(21234567,"ABC1234567890XYZ", "BATCH001", "Kreativer Name mit ganz vielen xyz", "Zanax", "2025-04");
        erstellenService.erstelleMedikament(23414564, "XYZ1234234290ABC", "BATCH004", "Kreativer Name mit ganz vielen abx", "Soledum", "2024-06");
        List<Medikament> liste = listeAllerMedikamenteUseCase.listeAllerMedikamente();
        SendeErinnerungMedikamentlaeuftAb sendeErinnerungMedikamentlaeuftAb = new SendeErinnerungMedikamentlaeuftAb(dateiSpeicher);
        sendeErinnerungMedikamentlaeuftAb.sendeErinnerungen();
        entfernenService.medikamentEntfernen("01 23414564 21 XYZ1234234290ABC 10 BATCH004 17 06-2025");

        SwingUtilities.invokeLater(() ->
                new MediTrackUI(
                    erstellenService,
                    listeAllerMedikamenteUseCase,
                    entfernenService,
                    baldAblaufendeMedikamenteUseCase,
                    abgelaufeneMedikamentUseCase
                )
        );
        //NotionBenachrichtigung notionBenachrichtigung = new NotionBenachrichtigung();
        //notionBenachrichtigung.sendeNotionBenachrichtigung("test","2525-12");
        //AllgemeinerScheduler allgemeinerScheduler = new AllgemeinerScheduler(dateiSpeicherAdapter);


    }
}