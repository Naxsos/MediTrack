import java.time.YearMonth;
import java.util.List;

public interface MedikamenteSpeicher {
    Medikament speichern(Medikament medikament);
    void entfernenViaUI(String ui);
    Medikament findeMedikamentViaUI(UniqueIdentifier ui);
    List<Medikament> findeViaMedikamentName(String name);             // Suche nach Medikamentennamen
    List<Medikament> findeViaWirkstoff(String wirkstoff);
    List<Medikament> findByVerfallsdatumBefore(YearMonth date);
    List<Medikament> findeAlleMedikamente();

}
