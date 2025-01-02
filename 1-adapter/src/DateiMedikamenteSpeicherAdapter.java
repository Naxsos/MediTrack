
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
}
