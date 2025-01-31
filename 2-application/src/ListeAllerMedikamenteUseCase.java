import java.util.List;

public class ListeAllerMedikamenteUseCase {
    private MedikamenteSpeicher medikamenteSpeicher;

    public ListeAllerMedikamenteUseCase(MedikamenteSpeicher medikamenteSpeicher) {
        this.medikamenteSpeicher = medikamenteSpeicher;
    }

    public List<Medikament> listeAllerMedikamente() {
        return medikamenteSpeicher.findeAlleMedikamente();
    }
}
