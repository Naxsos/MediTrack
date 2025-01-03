public class FindeMedikamenteUseCase {

    MedikamenteSpeicher medikamenteSpeicher;

    public FindeMedikamenteUseCase(MedikamenteSpeicher medikamenteSpeicher) {
        this.medikamenteSpeicher = medikamenteSpeicher;
    }

    public void findeMedikamentViaUI(UniqueIdentifier ui){
        medikamenteSpeicher.findeMedikamentViaUI(ui);
    }
}
