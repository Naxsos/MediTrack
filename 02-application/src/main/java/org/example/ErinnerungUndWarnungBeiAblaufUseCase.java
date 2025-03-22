package org.example;

import java.util.List;

public class ErinnerungUndWarnungBeiAblaufUseCase {
    private Nachrichtenformat nachrichtenformat;
    private final MedikamenteSpeicher medikamenteSpeicher;
    private ListeInZeitraumAblaufenderMedikamenteUseCase listeInZeitraumAblaufenderMedikamenteUseCase;
    private AbgelaufeneMedikamenteUseCase abgelaufeneMedikamenteUseCase;

    public ErinnerungUndWarnungBeiAblaufUseCase(MedikamenteSpeicher medikamenteSpeicher, Nachrichtenformat nachrichtenformat) {
        this.medikamenteSpeicher = medikamenteSpeicher;
        this.listeInZeitraumAblaufenderMedikamenteUseCase = new ListeInZeitraumAblaufenderMedikamenteUseCase(this.medikamenteSpeicher);
        this.abgelaufeneMedikamenteUseCase = new AbgelaufeneMedikamenteUseCase(this.medikamenteSpeicher);
        this.nachrichtenformat = nachrichtenformat;
    }

    public void sendeErinnerungen() {
        List<Medikament> ablaufendeMedikamente = listeInZeitraumAblaufenderMedikamenteUseCase.findeMedikamenteDieInXWochenAblaufen(2);
        List<Medikament> abgelaufeneMedikamente = abgelaufeneMedikamenteUseCase.abgelaufeneMedikamente();
        
        // Prüfe, ob ein Nachrichtenformat vorhanden ist
        if (nachrichtenformat != null) {
            for (Medikament med: ablaufendeMedikamente) {
                nachrichtenformat.sendeNachricht(med);
            }
            for (Medikament med: abgelaufeneMedikamente) {
                nachrichtenformat.sendeNachricht(med);
            }
        } else {
            System.out.println("Kein Nachrichtenformat konfiguriert - keine Benachrichtigungen gesendet.");
        }
    }
}
