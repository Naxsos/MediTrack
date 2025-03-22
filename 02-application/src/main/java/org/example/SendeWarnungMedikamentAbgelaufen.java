package org.example;

import java.util.List;

public class SendeWarnungMedikamentAbgelaufen {

    private final MedikamenteSpeicher medikamenteSpeicher;
    AbgelaufeneMedikamenteUseCase abgelaufeneMedikamenteUseCase;

    public SendeWarnungMedikamentAbgelaufen(MedikamenteSpeicher medikamenteSpeicher) {
        this.medikamenteSpeicher = medikamenteSpeicher;
        this.abgelaufeneMedikamenteUseCase = new AbgelaufeneMedikamenteUseCase(medikamenteSpeicher);
    }

    public void sendeWarnung() {
        List<Medikament> ablaufendeMedikamente = abgelaufeneMedikamenteUseCase.abgelaufeneMedikamente();
        // (Wer "wirklich" E‑Mails verschicken will, kann hier ein Notification-Interface ansprechen)
        for (Medikament med : ablaufendeMedikamente) {
            System.out.println("[CORE] Achtung! '"
                    + med.getMedikamentenName() + "' läuft am " + med.getAblaufDatum() + " ab.");
        }
    }
}
