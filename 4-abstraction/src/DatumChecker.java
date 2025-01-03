import java.time.LocalDate;
import java.time.YearMonth;

public class DatumChecker {
    // Subtrahiert jeweils Wochen -> Sprich 7xAnzahl der Wochen -> Ein Monat == 28 Tage
    public static boolean istDatumheuteInXWochen(YearMonth datum, int weeks) {
        LocalDate ersterDesMonats = datum.atDay(1); // Get first day of the month
        LocalDate xWocheVorher = ersterDesMonats.minusWeeks(weeks);
        LocalDate heute = LocalDate.now();
        return heute.equals(xWocheVorher);
    }
    public static boolean istDatumHeuteInXMonaten(YearMonth datum, int monat) {
        LocalDate ersterDesMonats = datum.atDay(1); // Get first day of the month
        LocalDate xMonateVorher = ersterDesMonats.minusMonths(monat);
        LocalDate heute = LocalDate.now();
        return heute.equals(xMonateVorher);
    }
}
