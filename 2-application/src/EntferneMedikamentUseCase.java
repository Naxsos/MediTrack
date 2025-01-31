
public class EntferneMedikamentUseCase {

    private MedikamenteSpeicher medikamenteSpeicher;

    public EntferneMedikamentUseCase(MedikamenteSpeicher medikamenteSpeicher) {
        this.medikamenteSpeicher = medikamenteSpeicher;
    }

    public void medikamentEntfernen(String ui) {
        medikamenteSpeicher.entfernenViaUI(ui);
    }
}
