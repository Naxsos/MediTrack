import java.time.LocalDate;
import java.time.YearMonth;

public class UniqueIdentifier {
    // ProduktCode
    private int pzn;
    private String serienNummer;
    private String chargenNummer;
    private YearMonth ablaufDatum;

    public UniqueIdentifier(int pzn, String serienNummer, String chargenNummer, YearMonth ablaufDatum) {
        this.pzn = pzn;
        this.serienNummer = serienNummer;
        this.chargenNummer = chargenNummer;
        this.ablaufDatum = ablaufDatum;
    }

    public int getPzn() {
        return pzn;
    }

    public String getSerienNummer() {
        return serienNummer;
    }

    public String getChargenNummer() {
        return chargenNummer;
    }

    public YearMonth getAblaufDatum() {
        return ablaufDatum;
    }


    @Override
    public String toString() {
        return String.format("01 %s 21 %s 10 %s 17 %s",
                pzn,
                    serienNummer,
                    chargenNummer,
                    ablaufDatum.format(java.time.format.DateTimeFormatter.ofPattern(Konstanten.ABLAUF_DATUM_FORMAT)));
    }
}


