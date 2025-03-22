package org.example;

import org.quartz.*;

import java.util.List;

public class MainMitNotionBenachrichtigung {
    public static void main(String[] args) throws SchedulerException {
        DateiMedikamenteSpeicher dateiMedikamenteSpeicher = new DateiMedikamenteSpeicher("test.txt");
        

        DateiLagerortSpeicher dateiLagerortSpeicher = new DateiLagerortSpeicher("lagerorte.txt");
        LagerorteVerwaltenUseCase lagerorteVerwaltenUseCase = new LagerorteVerwaltenUseCase(dateiLagerortSpeicher);


        MedikamenteVerwaltenUseCase erstellenUndLoeschenService = new MedikamenteVerwaltenUseCase(dateiMedikamenteSpeicher);
        FindeMedikamenteUseCase findeMedikamenteUseCase = new FindeMedikamenteUseCase(dateiMedikamenteSpeicher);
        ListeInZeitraumAblaufenderMedikamenteUseCase baldAblaufendeMedikamenteUseCase = new ListeInZeitraumAblaufenderMedikamenteUseCase(dateiMedikamenteSpeicher);
        AbgelaufeneMedikamenteUseCase abgelaufeneMedikamentUseCase = new AbgelaufeneMedikamenteUseCase(dateiMedikamenteSpeicher);


        try {
            lagerorteVerwaltenUseCase.erstelleLagerort("Medikamentenlager", 1, "101");
            lagerorteVerwaltenUseCase.erstelleLagerort("Kühlschrank", 2, "202");
            lagerorteVerwaltenUseCase.erstelleLagerort("Notfalldepot", 3, "303");
        } catch(IllegalArgumentException ex) {
            System.out.println("Lagerorte bereits erstellt.");
        }

        try {
            erstellenUndLoeschenService.erstelleMedikament(21234567, "XAJRP332EG", "BATCH001",
                "Aspirin Plus C", "Bayer", "2023-11", "1-101");

            erstellenUndLoeschenService.erstelleMedikament(23414564, "IJKLV43WXZ", "BATCH004",
                "Paracetamol", "Ratiopharm", "2023-09", "2-202");

            erstellenUndLoeschenService.erstelleMedikament(23987654, "DEFTR09WEF", "BATCH007",
                "Ibuprofen", "Hexal", "2025-08", "3-303");
        } catch(Exception ex) {
            System.out.println("Medikamente bereits erstellt oder Fehler: " + ex.getMessage());
        }

        List<Medikament> alleMedikamente = findeMedikamenteUseCase.listeAllerMedikamente();
        System.out.println("Verfügbare Medikamente für Tests:");
        for (Medikament med : alleMedikamente) {
            System.out.println(" - " + med.getMedikamentenName() + " (Ablaufdatum: " + med.getAblaufDatum() + ")");
        }

        MedikamenteController medikamenteController = new MedikamenteController(
            erstellenUndLoeschenService,
            baldAblaufendeMedikamenteUseCase,
            findeMedikamenteUseCase,
            abgelaufeneMedikamentUseCase
        );
        
        LagerorteController lagerorteController = new LagerorteController(lagerorteVerwaltenUseCase);

        new MediTrackUI(medikamenteController, lagerorteController);

        
        NotionBenachrichtigung notionBenachrichtigung = new NotionBenachrichtigung();
        ErinnerungUndWarnungBeiAblaufUseCase sendeErinnerungMedikamentlaeuftAb = new ErinnerungUndWarnungBeiAblaufUseCase(dateiMedikamenteSpeicher,notionBenachrichtigung);
        //sendeErinnerungMedikamentlaeuftAb.sendeErinnerungen();
        AllgemeinerScheduler allgemeinerScheduler = new AllgemeinerScheduler(24, dateiMedikamenteSpeicher, notionBenachrichtigung);
    }
} 