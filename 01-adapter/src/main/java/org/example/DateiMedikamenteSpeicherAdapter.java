package org.example;

import java.time.YearMonth;
import java.util.List;

public class DateiMedikamenteSpeicherAdapter implements MedikamenteSpeicher {
    private final MedikamenteSpeicher dateiMedikamenteSpeicher;

    public DateiMedikamenteSpeicherAdapter(MedikamenteSpeicher dateiMedikamenteSpeicher) {
        this.dateiMedikamenteSpeicher = dateiMedikamenteSpeicher;
    }

    @Override
    public Medikament speichern(Medikament medikament) {
        return dateiMedikamenteSpeicher.speichern(medikament);
    }

    @Override
    public void entfernenViaUI(String ui) {
        dateiMedikamenteSpeicher.entfernenViaUI(ui);
    }

    @Override
    public Medikament findeMedikamentViaUI(UniqueIdentifier ui) {
        return dateiMedikamenteSpeicher.findeMedikamentViaUI(ui);
    }

    @Override
    public List<Medikament> findeViaMedikamentName(String name) {
        return dateiMedikamenteSpeicher.findeViaMedikamentName(name);
    }

    @Override
    public List<Medikament> findeViaWirkstoff(String wirkstoff) {
        return dateiMedikamenteSpeicher.findeViaWirkstoff(wirkstoff);
    }

    @Override
    public List<Medikament> findByVerfallsdatumBefore(YearMonth date) {
        return dateiMedikamenteSpeicher.findByVerfallsdatumBefore(date);
    }

    @Override
    public List<Medikament> findeAlleMedikamente() {
        return dateiMedikamenteSpeicher.findeAlleMedikamente();
    }
}
