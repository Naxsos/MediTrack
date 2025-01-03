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
        return null;
    }

    @Override
    public List<Medikament> findeViaMedikamentName(String name) {
        return List.of();
    }

    @Override
    public List<Medikament> findeViaWirkstoff(String wirkstoff) {
        return List.of();
    }

    @Override
    public List<Medikament> findByVerfallsdatumBefore(YearMonth date) {
        return List.of();
    }

    @Override
    public List<Medikament> findeAlleMedikamente() {
        return List.of();
    }
}
