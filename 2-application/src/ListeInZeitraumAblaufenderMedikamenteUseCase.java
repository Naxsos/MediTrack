import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

public class ListeInZeitraumAblaufenderMedikamenteUseCase {

    MedikamenteSpeicher medikamenteSpeicher;

    public ListeInZeitraumAblaufenderMedikamenteUseCase(MedikamenteSpeicher medikamenteSpeicher) {
        this.medikamenteSpeicher = medikamenteSpeicher;
    }

    public List<Medikament> wirdIn1WochenAblaufen(){
        List<Medikament> alleMedikamenteListe = medikamenteSpeicher.findeAlleMedikamente();
        List<Medikament> wirdAblaufenListe = new ArrayList<Medikament>();
        for (Medikament medikament : alleMedikamenteListe) {
            if (DatumChecker.istDatumheuteInXWochen(medikament.getAblaufDatum(), 1) ) {
                wirdAblaufenListe.add(medikament);
            }
        }
        return wirdAblaufenListe;
    }

    // HIER WURDE DRY VERLETZT... DAS MUSS ANGEPASST WERDEN
    public List<Medikament> wirdIn2WochenAblaufen(){
        List<Medikament> alleMedikamenteListe = medikamenteSpeicher.findeAlleMedikamente();
        List<Medikament> wirdAblaufenListe = new ArrayList<Medikament>();
        for (Medikament medikament : alleMedikamenteListe) {
            if (DatumChecker.istDatumheuteInXWochen(medikament.getAblaufDatum(), 2) ) {
                wirdAblaufenListe.add(medikament);
            }
        }
        return wirdAblaufenListe;
    }

    public List<Medikament> wirdIn4WochenAblaufen(){
        List<Medikament> alleMedikamenteListe = medikamenteSpeicher.findeAlleMedikamente();
        List<Medikament> wirdAblaufenListe = new ArrayList<Medikament>();
        for (Medikament medikament : alleMedikamenteListe) {
            if (DatumChecker.istDatumHeuteInXMonaten(medikament.getAblaufDatum(), 1) ) {
                wirdAblaufenListe.add(medikament);
            }
        }
        return wirdAblaufenListe;
    }

    public List<Medikament> wirdAblaufenzuBestimmtemDatum(YearMonth datum){
        List<Medikament> alleMedikamenteListe = medikamenteSpeicher.findeAlleMedikamente();
        List<Medikament> wirdAblaufenListe = new ArrayList<Medikament>();
        for (Medikament medikament : alleMedikamenteListe) {
            if (medikament.getAblaufDatum().equals(datum)) {
                wirdAblaufenListe.add(medikament);
            }
        }
        return wirdAblaufenListe;
    }
}
