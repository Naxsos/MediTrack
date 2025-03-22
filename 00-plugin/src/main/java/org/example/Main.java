package org.example;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import javax.sound.midi.Soundbank;
import javax.swing.*;
import java.sql.SQLOutput;
import java.util.List;
import java.util.TimeZone;


public class Main {
    public static void main(String[] args) throws SchedulerException {
        // Medikamente-Speicher und UseCase-Instanzen
        DateiMedikamenteSpeicher dateiMedikamenteSpeicher = new DateiMedikamenteSpeicher("test.txt");
        
        // Lagerorte-Speicher und UseCase-Instanzen
        DateiLagerortSpeicher dateiLagerortSpeicher = new DateiLagerortSpeicher("lagerorte.txt");
        LagerorteVerwaltenUseCase lagerorteVerwaltenUseCase = new LagerorteVerwaltenUseCase(dateiLagerortSpeicher);

        // Medikamente Use Cases
        ErstelleMedikamentUseCase erstellenService = new ErstelleMedikamentUseCase(dateiMedikamenteSpeicher);
        EntferneMedikamentUseCase entfernenService = new EntferneMedikamentUseCase(dateiMedikamenteSpeicher);
        AlleMedikamenteAusgebenUseCase listeAllerMedikamenteUseCase = new AlleMedikamenteAusgebenUseCase(dateiMedikamenteSpeicher);
        ListeInZeitraumAblaufenderMedikamenteUseCase baldAblaufendeMedikamenteUseCase = new ListeInZeitraumAblaufenderMedikamenteUseCase(dateiMedikamenteSpeicher);
        AbgelaufeneMedikamenteUseCase abgelaufeneMedikamentUseCase = new AbgelaufeneMedikamenteUseCase(dateiMedikamenteSpeicher);


        // Testdaten für Lagerorte erstellen
        try{
            lagerorteVerwaltenUseCase.erstelleLagerort("Medikamentenlager", 1, "101");
            lagerorteVerwaltenUseCase.erstelleLagerort("Kühlschrank", 2, "202");
            lagerorteVerwaltenUseCase.erstelleLagerort("Notfalldepot", 3, "303");
        }catch(IllegalArgumentException ex){
            System.out.println("Lagerorte bereits erstellt.");
        }
        
        // Medikamente-Testcode
        erstellenService.erstelleMedikament(21234567,"ABC1234567890XYZ", "BATCH001", "Kreativer Name mit ganz vielen xyz", "Zanax", "2025-04", "3-26A");
        erstellenService.erstelleMedikament(23414564, "XYZ1234234290ABC", "BATCH004", "Kreativer Name mit ganz vielen abx", "Soledum", "2024-06", "1-202B");
        List<Medikament> liste = listeAllerMedikamenteUseCase.listeAllerMedikamente();
        SendeErinnerungMedikamentlaeuftAb sendeErinnerungMedikamentlaeuftAb = new SendeErinnerungMedikamentlaeuftAb(dateiMedikamenteSpeicher);
        sendeErinnerungMedikamentlaeuftAb.sendeErinnerungen();
        entfernenService.medikamentEntfernen("01 23414564 21 XYZ1234234290ABC 10 BATCH004 17 06-2025");

        // Controller erstellen
        MedikamenteController medikamenteController = new MedikamenteController(
            erstellenService,
            listeAllerMedikamenteUseCase,
            entfernenService,
            baldAblaufendeMedikamenteUseCase,
            abgelaufeneMedikamentUseCase
        );
        
        LagerorteController lagerorteController = new LagerorteController(lagerorteVerwaltenUseCase);
        
        // UI starten mit beiden Controllern
        new MediTrackUI(medikamenteController, lagerorteController);
        
        //NotionBenachrichtigung notionBenachrichtigung = new NotionBenachrichtigung();
        //notionBenachrichtigung.sendeNotionBenachrichtigung("test","2525-12");
        //AllgemeinerScheduler allgemeinerScheduler = new AllgemeinerScheduler(dateiSpeicherAdapter);
    }
}