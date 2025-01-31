package org.example;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

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

    public static UniqueIdentifier fromString(String identifierStr) {
        if (identifierStr == null || identifierStr.trim().isEmpty()) {
            throw new IllegalArgumentException("Input string is null or empty.");
        }

        String[] tokens = identifierStr.trim().split("\\s+");

        if (tokens.length != 8) {
            throw new IllegalArgumentException("Invalid identifier format: Expected 8 tokens, found " + tokens.length);
        }

        try {
            // Validate and parse PZN
            if (!Konstanten.PREFIX_PZN.equals(tokens[0])) {
                throw new IllegalArgumentException("Invalid prefix for PZN. Expected '" + Konstanten.PREFIX_PZN + "', found '" + tokens[0] + "'");
            }
            int pzn = Integer.parseInt(tokens[1]);

            // Validate and parse Seriennummer
            if (!Konstanten.PREFIX_SERIENNUMMER.equals(tokens[2])) {
                throw new IllegalArgumentException("Invalid prefix for Seriennummer. Expected '" + Konstanten.PREFIX_SERIENNUMMER + "', found '" + tokens[2] + "'");
            }
            String serienNummer = tokens[3];

            // Validate and parse Chargennummer
            if (!Konstanten.PREFIX_CHARGENNUMMER.equals(tokens[4])) {
                throw new IllegalArgumentException("Invalid prefix for Chargennummer. Expected '" + Konstanten.PREFIX_CHARGENNUMMER + "', found '" + tokens[4] + "'");
            }
            String chargenNummer = tokens[5];

            // Validate and parse Ablaufdatum
            if (!Konstanten.PREFIX_ABLAUF_DATUM.equals(tokens[6])) {
                throw new IllegalArgumentException("Invalid prefix for Ablaufdatum. Expected '" + Konstanten.PREFIX_ABLAUF_DATUM + "', found '" + tokens[6] + "'");
            }
            YearMonth ablaufDatum = YearMonth.parse(tokens[7], DateTimeFormatter.ofPattern(Konstanten.ABLAUF_DATUM_FORMAT));

            return new UniqueIdentifier(pzn, serienNummer, chargenNummer, ablaufDatum);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("PZN must be an integer. Error: " + e.getMessage(), e);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Ablaufdatum must be in the format " + Konstanten.ABLAUF_DATUM_FORMAT + ". Error: " + e.getMessage(), e);
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
}


