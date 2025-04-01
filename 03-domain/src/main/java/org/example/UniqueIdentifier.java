package org.example;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class UniqueIdentifier {
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

    public static UniqueIdentifier fromString(String identifierStr) {
        if (identifierStr == null || identifierStr.trim().isEmpty()) {
            throw new IllegalArgumentException("Eingabe darf nicht null oder leer sein.");
        }

        String[] tokens = identifierStr.trim().split("\\s+");

        if (tokens.length != 8) {
            throw new IllegalArgumentException("Falsches Format: Es werden nur 8 Tokens erwartet, gefunden: " + tokens.length);
        }

        try {
            if (!Konstanten.PREFIX_PZN.equals(tokens[0])) {
                throw new IllegalArgumentException("Falscher Präfix für PNZ. Erwartet '" + Konstanten.PREFIX_PZN + "', gefunden '" + tokens[0] + "'");
            }
            int pzn = Integer.parseInt(tokens[1]);

            if (!Konstanten.PREFIX_SERIENNUMMER.equals(tokens[2])) {
                throw new IllegalArgumentException("Falscher Präfix für Seriennummer. Erwartet '" + Konstanten.PREFIX_SERIENNUMMER + "', gefunden '" + tokens[2] + "'");
            }
            String serienNummer = tokens[3];

            if (!Konstanten.PREFIX_CHARGENNUMMER.equals(tokens[4])) {
                throw new IllegalArgumentException("Falscher Präfix für Chargennummer. Erwartet '" + Konstanten.PREFIX_CHARGENNUMMER + "', gefunden '" + tokens[4] + "'");
            }
            String chargenNummer = tokens[5];

            if (!Konstanten.PREFIX_ABLAUF_DATUM.equals(tokens[6])) {
                throw new IllegalArgumentException("Falscher Präfix für Ablaufdatum. Erwartet '" + Konstanten.PREFIX_ABLAUF_DATUM + "', gefunden '" + tokens[6] + "'");
            }
            YearMonth ablaufDatum = YearMonth.parse(tokens[7], DateTimeFormatter.ofPattern(Konstanten.ABLAUF_DATUM_FORMAT));

            return new UniqueIdentifier(pzn, serienNummer, chargenNummer, ablaufDatum);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Die PZN muss eine Zahl sein.", e);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Ablaufdatum muss im Format " + Konstanten.ABLAUF_DATUM_FORMAT + " vorliegen. ", e);
        }
    }

    @Override
    public String toString() {
        return String.format("01 %s 21 %s 10 %s 17 %s",
                pzn,
                    serienNummer,
                    chargenNummer,
                    ablaufDatum.format(DateTimeFormatter.ofPattern(Konstanten.ABLAUF_DATUM_FORMAT)));
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        UniqueIdentifier that = (UniqueIdentifier) o;
        
        if (pzn != that.pzn) return false;
        if (!serienNummer.equals(that.serienNummer)) return false;
        if (!chargenNummer.equals(that.chargenNummer)) return false;
        return ablaufDatum.equals(that.ablaufDatum);
    }
    
    @Override
    public int hashCode() {
        int result = pzn;
        result = 31 * result + serienNummer.hashCode();
        result = 31 * result + chargenNummer.hashCode();
        result = 31 * result + ablaufDatum.hashCode();
        return result;
    }
}


