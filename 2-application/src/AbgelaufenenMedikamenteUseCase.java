import java.util.ArrayList;
import java.util.List;

public class AbgelaufenenMedikamenteUseCase {
    private MedikamenteSpeicher medikamenteSpeicher;

    public AbgelaufenenMedikamenteUseCase(MedikamenteSpeicher medikamenteSpeicher) {
        this.medikamenteSpeicher = medikamenteSpeicher;
    }

    public List<Medikament> abgelaufeneMedikamente(){
        List<Medikament> medikamentListe = medikamenteSpeicher.findeAlleMedikamente();
        List<Medikament> abgelaufeneMedikamenteListe = new ArrayList<Medikament>();
        for (Medikament medikament : medikamentListe) {
            if(medikament.isExpired()) {
                abgelaufeneMedikamenteListe.add(medikament);
            }
        }
        return abgelaufeneMedikamenteListe;
    }
}
