package org.example;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.YearMonth;
import java.util.Optional;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Tests für die Medikament-Klasse.
 */
class MedikamentTest {
    

    @Test
    @DisplayName("Medikament sollte korrekt erstellt werden")
    void medikamentSollteKorrektErstelltWerden() {
         // Gegeben (Arrange)
         String name = "Aspirin";
         String wirkstoffBezeichnung = "Acetylsalicylsäure";
         String hersteller = "Bayer";
         int pznNummer = 12345678;
         String seriennummerWert = "SN12345";
         String chargennummerWert = "CH789";
         YearMonth ablaufDatum = YearMonth.of(2025, 12);
         UniqueIdentifier ui = new UniqueIdentifier(pznNummer, seriennummerWert, chargennummerWert, ablaufDatum);

         LagerortID lagerortID = LagerortID.fromString("2-A101");
         Darreichungsform darreichungsform = Darreichungsform.TABLETTE;
         Masseinheit masseinheit = Masseinheit.MILLIGRAMM;
         int wertMasseinheit = 100;
         Intervall intervall = Intervall.PRO_TAG;
         int wertIntervall = 1;
         Zeitperiode zeitperiode = Zeitperiode.TAGE;
         int wertZeitperiode = 1;
         Dosierung dosierung = new Dosierung(masseinheit, wertMasseinheit, intervall, wertIntervall, zeitperiode, wertZeitperiode);

         // Ausführen (Act)
         Medikament medikament = new Medikament.Builder(ui)
                 .serienNummer(new Seriennummer(seriennummerWert))
                 .pzn(new PZN(pznNummer))
                 .chargenNummer(new Chargennummer(chargennummerWert))
                 .medikamentenName(name)
                 .wirkstoffBezeichnung(wirkstoffBezeichnung)
                 .hersteller(hersteller)
                 .darreichungsform(darreichungsform)
                 .dosierung(dosierung)
                 .ablaufDatum(ablaufDatum)
                 .lagerortId(lagerortID)
                 .build();

        // Überprüfen (Assert)
         assertEquals(name, medikament.getMedikamentenName(), "Der Name sollte übereinstimmen");
         assertEquals(wirkstoffBezeichnung, medikament.getWirkstoffBezeichnung(), "Die Wirkstoffbezeichnung sollte übereinstimmen");
         assertEquals(hersteller, medikament.getHersteller(), "Der Hersteller sollte übereinstimmen");
         assertEquals(pznNummer, medikament.getPzn().getNummer(), "Die PZN sollte übereinstimmen");
         assertEquals(seriennummerWert, medikament.getSerienNummer().toString(), "Die Seriennummer sollte übereinstimmen");
         assertEquals(chargennummerWert, medikament.getChargenNummer().toString(), "Die Chargennummer sollte übereinstimmen");
         assertEquals(ablaufDatum, medikament.getAblaufDatum(), "Das Ablaufdatum sollte übereinstimmen");
         assertEquals(darreichungsform, medikament.getDarreichungsform(), "Die Darreichungsform sollte übereinstimmen");
         assertEquals(dosierung, medikament.getDosierung(), "Die Dosierung sollte übereinstimmen");
         assertEquals(lagerortID, medikament.getLagerortId(), "Die LagerortID sollte übereinstimmen");
    }

    @Test
    @DisplayName("Sollte eine Exception werfen, wenn beim Builder zwingend benötigte Felder fehlen")
    void sollteAusnahmeWerfenWennPflichtfelderFehlen() {
        // Gegeben (Arrange)
        UniqueIdentifier ui = new UniqueIdentifier(
                12345678,
                "SN12345",
                "CH789",
                YearMonth.of(2025, 12)
        );

        // Ausführen (Act)
        Exception exception = assertThrows(
                IllegalStateException.class,
                () -> new Medikament.Builder(ui).build()
        );

        // Überprüfen (Assert)
        assertTrue(exception.getMessage().contains("muss gesetzt sein"),
                "Die Fehlermeldung sollte einen Hinweis auf fehlende Felder enthalten");
    }


    @Test
    @DisplayName("getUniqueIdentifier sollte korrekten UniqueIdentifier zurückgeben")
    void getUniqueIdentifierSollteKorrektenIdentifierZurueckgeben() {
        // Gegeben (Arrange)
        YearMonth ablaufDatum = YearMonth.of(2025, 12);
        UniqueIdentifier ui = new UniqueIdentifier(12345678, "SN12345", "CH12345", ablaufDatum);
        
        Medikament medikament = new Medikament.Builder(ui)
                .serienNummer(new Seriennummer("SN12345"))
                .pzn(new PZN(12345678))
                .chargenNummer(new Chargennummer("CH12345"))
                .medikamentenName("Aspirin")
                .wirkstoffBezeichnung("Testwirkstoff")
                .hersteller("Testhersteller")
                .darreichungsform(Darreichungsform.TABLETTE)
                .dosierung(new Dosierung(Masseinheit.MILLIGRAMM, 100, Intervall.PRO_TAG, 1, Zeitperiode.TAGE, 1))
                .ablaufDatum(ablaufDatum)
                .lagerortId(LagerortID.fromString("1-A"))
                .build();

        // Ausführen (Act)
        UniqueIdentifier uniqueId = medikament.getUi();

        // Überprüfen (Assert)
        assertEquals(12345678, uniqueId.getPzn(), "Die PZN im UniqueIdentifier sollte korrekt sein");
        assertEquals("SN12345", uniqueId.getSerienNummer(), "Die Seriennummer im UniqueIdentifier sollte korrekt sein");
        assertEquals("CH12345", uniqueId.getChargenNummer(), "Die Chargennummer im UniqueIdentifier sollte korrekt sein");
        assertEquals(ablaufDatum, uniqueId.getAblaufDatum(), "Das Ablaufdatum im UniqueIdentifier sollte korrekt sein");
    }

    @Test
    @DisplayName("istAbgelaufen sollte true zurückgeben, wenn das Medikament abgelaufen ist")
    void istAbgelaufenSollteWahrZurueckgebenWennMedikamentAbgelaufenIst() {
        // Gegeben (Arrange)
        YearMonth abgelaufenesDatum = YearMonth.of(2022, 12);
        UniqueIdentifier ui = new UniqueIdentifier(12345678, "SN12345", "CH12345", abgelaufenesDatum);
        
        Medikament medikament = new Medikament.Builder(ui)
                .serienNummer(new Seriennummer("SN12345"))
                .pzn(new PZN(12345678))
                .chargenNummer(new Chargennummer("CH12345"))
                .medikamentenName("Aspirin")
                .wirkstoffBezeichnung("Testwirkstoff")
                .hersteller("Testhersteller")
                .darreichungsform(Darreichungsform.TABLETTE)
                .dosierung(new Dosierung(Masseinheit.MILLIGRAMM, 100, Intervall.PRO_TAG, 1, Zeitperiode.TAGE, 1))
                .ablaufDatum(abgelaufenesDatum)
                .lagerortId(LagerortID.fromString("1-A"))
                .build();

        // Ausführen (Act)
        boolean ergebnis = medikament.istAbgelaufen();

        // Überprüfen (Assert)
        assertTrue(ergebnis, "Ein Medikament mit einem Ablaufdatum in der Vergangenheit sollte als abgelaufen erkannt werden");
    }

    @Test
    @DisplayName("toString sollte eine sinnvolle Stringrepräsentation zurückgeben")
    void toStringSollteEineSinnvolleStringRepraesentationZurueckgeben() {
        // Gegeben (Arrange)
        YearMonth ablaufDatum =  YearMonth.of(2025, 12);
        UniqueIdentifier ui = new UniqueIdentifier(12345678, "SN12345", "CH12345", ablaufDatum);
        
        Medikament medikament = new Medikament.Builder(ui)
                .serienNummer(new Seriennummer("SN12345"))
                .pzn(new PZN(12345678))
                .chargenNummer(new Chargennummer("CH12345"))
                .medikamentenName("Aspirin")
                .wirkstoffBezeichnung("Testwirkstoff")
                .hersteller("Testhersteller")
                .darreichungsform(Darreichungsform.TABLETTE)
                .dosierung(new Dosierung(Masseinheit.MILLIGRAMM, 100, Intervall.PRO_TAG, 1, Zeitperiode.TAGE, 1))
                .ablaufDatum(ablaufDatum)
                .lagerortId(LagerortID.fromString("1-A"))
                .build();

        // Ausführen (Act)
        String result = medikament.toString();

        // Überprüfen (Assert)
        assertTrue(result.contains("Aspirin"), "Der Name sollte in der Stringrepräsentation enthalten sein");
        assertTrue(result.contains("12345678"), "Die PZN sollte in der Stringrepräsentation enthalten sein");
        assertTrue(result.contains(ablaufDatum.toString()), "Das Ablaufdatum sollte in der Stringrepräsentation enthalten sein");
    }

    @Test
    @DisplayName("Sollte eine Exception werfen, wenn der Name null oder leer ist")
    void sollteExceptionBeiUngueltigemNamenWerfen() {
        // Gegeben (Arrange)        
        UniqueIdentifier ui = new UniqueIdentifier(12345678, "SN12345", "CH12345", YearMonth.of(2025, 12));
        String invalidName = null;
        String invalidName2 = "";
        String invalidName3 = "  ";

        // Ausführen (Act)
        IllegalStateException exception = assertThrows(
            IllegalStateException.class,
            () -> new Medikament.Builder(ui)
                .medikamentenName(invalidName)
                .wirkstoffBezeichnung("Testwirkstoff")
                .serienNummer(new Seriennummer("SN12345"))
                .pzn(new PZN(12345678))
                .chargenNummer(new Chargennummer("CH12345"))
                .ablaufDatum(YearMonth.of(2025, 12))
                .lagerortId(LagerortID.fromString("1-A"))
                .build()
        );

        IllegalStateException exception2 = assertThrows(
                IllegalStateException.class,
                () -> new Medikament.Builder(ui)
                        .medikamentenName(invalidName2)
                        .wirkstoffBezeichnung("Testwirkstoff")
                        .serienNummer(new Seriennummer("SN12345"))
                        .pzn(new PZN(12345678))
                        .chargenNummer(new Chargennummer("CH12345"))
                        .ablaufDatum(YearMonth.of(2025, 12))
                        .lagerortId(LagerortID.fromString("1-A"))
                        .build()
        );

        IllegalStateException exception3 = assertThrows(
                IllegalStateException.class,
                () -> new Medikament.Builder(ui)
                        .medikamentenName(invalidName3)
                        .wirkstoffBezeichnung("Testwirkstoff")
                        .serienNummer(new Seriennummer("SN12345"))
                        .pzn(new PZN(12345678))
                        .chargenNummer(new Chargennummer("CH12345"))
                        .ablaufDatum(YearMonth.of(2025, 12))
                        .lagerortId(LagerortID.fromString("1-A"))
                        .build()
        );

        // Überprüfen (Assert)
        assertTrue(exception.getMessage().contains("Medikamentename muss gesetzt sein!"),
                "Die Fehlermeldung sollte auf den ungültigen Namen hinweisen");
        assertTrue(exception2.getMessage().contains("Medikamentename muss gesetzt sein!"),
                "Die Fehlermeldung sollte auf den ungültigen Namen hinweisen");
        assertTrue(exception3.getMessage().contains("Medikamentename muss gesetzt sein!"),
                "Die Fehlermeldung sollte auf den ungültigen Namen hinweisen");
        
    }

    @DisplayName("Sollte eine Exception werfen, wenn die Wirkstoffbezeichnung null oder leer ist")
    @Test
    void sollteExceptionBeiInvaliderWirkstoffBezeichnungWerfen() {
        // Gegeben (Arrange)
        UniqueIdentifier ui = new UniqueIdentifier(12345678, "SN12345", "CH12345", YearMonth.of(2025, 12));
        String invalidWirkstoff = null;
        String invalidWirkstoff2 = "  ";

        // Ausführen (Act)
        IllegalStateException exception = assertThrows(
            IllegalStateException.class,
            () -> new Medikament.Builder(ui)
                .medikamentenName("Aspirin")
                .wirkstoffBezeichnung(invalidWirkstoff)
                .serienNummer(new Seriennummer("SN12345"))
                .pzn(new PZN(12345678))
                .chargenNummer(new Chargennummer("CH12345"))
                .ablaufDatum(YearMonth.of(2025, 12))
                .lagerortId(LagerortID.fromString("1-A"))
                .build()
        );
        IllegalStateException exception2 = assertThrows(
            IllegalStateException.class,
            () -> new Medikament.Builder(ui)
                .medikamentenName("Aspirin")
                .wirkstoffBezeichnung(invalidWirkstoff2)
                .serienNummer(new Seriennummer("SN12345"))
                .pzn(new PZN(12345678))
                .chargenNummer(new Chargennummer("CH12345"))
                .ablaufDatum(YearMonth.of(2025, 12))
                .lagerortId(LagerortID.fromString("1-A"))
                .build()
        );
        
        // Überprüfen (Assert)
        assertTrue(exception.getMessage().contains("Wirkstoffbezeichnung muss gesetzt sein!"),
                "Die Fehlermeldung sollte auf die ungültige Wirkstoffbezeichnung hinweisen");
        assertTrue(exception2.getMessage().contains("Wirkstoffbezeichnung muss gesetzt sein!"),
                "Die Fehlermeldung sollte auf die ungültige Wirkstoffbezeichnung hinweisen");
    }

    @Test
    @DisplayName("Sollte eine Exception werfen, wenn die Seriennummer null ist")
    void sollteExceptionBeiNullSeriennummerWerfen() {
        // Gegeben (Arrange)
        UniqueIdentifier ui = new UniqueIdentifier(12345678, "SN12345", "CH12345", YearMonth.of(2025, 12));
        
        // Ausführen (Act)
        IllegalStateException exception = assertThrows(
            IllegalStateException.class,
            () -> new Medikament.Builder(ui)
                .pzn(new PZN(12345678))
                .chargenNummer(new Chargennummer("CH789"))
                .medikamentenName("Aspirin")
                .wirkstoffBezeichnung("Acetylsalicylsäure")
                .ablaufDatum(YearMonth.of(2025, 12))
                .lagerortId(LagerortID.fromString("1-A"))
                .build()
        );
        
        // Überprüfen (Assert)
        assertTrue(exception.getMessage().contains("Seriennummer muss gesetzt sein!"), 
                "Die Fehlermeldung sollte auf die fehlende Seriennummer hinweisen");
    }

    @Test
    @DisplayName("Sollte eine Exception werfen, wenn die PZN null ist")
    void sollteExceptionBeiNullPZNWerfen() {
        // Gegeben (Arrange)
        UniqueIdentifier ui = new UniqueIdentifier(12345678, "SN12345", "CH789", YearMonth.of(2025, 12));
        
        // Ausführen (Act)
        IllegalStateException exception = assertThrows(
            IllegalStateException.class,
            () -> new Medikament.Builder(ui)
                .serienNummer(new Seriennummer("SN12345"))
                .chargenNummer(new Chargennummer("CH789"))
                .medikamentenName("Aspirin")
                .wirkstoffBezeichnung("Acetylsalicylsäure")
                .ablaufDatum(YearMonth.of(2025, 12))
                .lagerortId(LagerortID.fromString("1-A"))
                .build()
        );

        // Überprüfen (Assert)
        assertTrue(exception.getMessage().contains("PZN muss gesetzt sein!"),
                "Die Fehlermeldung sollte auf die fehlende PZN hinweisen");
    }

    @Test
    @DisplayName("Sollte eine Exception werfen, wenn die Chargennummer null ist")
    void sollteExceptionBeiNullChargennummerWerfen() {
        // Gegeben (Arrange)
        UniqueIdentifier ui = new UniqueIdentifier(12345678, "SN12345", "CH789", YearMonth.of(2025, 12));
        
        // Ausführen (Act)
        IllegalStateException exception = assertThrows(
            IllegalStateException.class,
            () -> new Medikament.Builder(ui)
                .serienNummer(new Seriennummer("SN12345"))
                .pzn(new PZN(12345678))
                .medikamentenName("Aspirin")
                .wirkstoffBezeichnung("Acetylsalicylsäure")
                .ablaufDatum(YearMonth.of(2025, 12))
                .lagerortId(LagerortID.fromString("1-A"))
                .build()
        );
        
        // Überprüfen (Assert)
        assertTrue(exception.getMessage().contains("Chargennummer muss gesetzt sein!"),
                "Die Fehlermeldung sollte auf die fehlende Chargennummer hinweisen");
    }

    @Test
    @DisplayName("Sollte eine Exception werfen, wenn das Ablaufdatum null ist")
    void sollteExceptionBeiNullAblaufdatumWerfen() {
        // Gegeben (Arrange)
        UniqueIdentifier ui = new UniqueIdentifier(12345678, "SN12345", "CH789", YearMonth.of(2025, 12));
        
        // Ausführen (Act)
        IllegalStateException exception = assertThrows(
            IllegalStateException.class,
            () -> new Medikament.Builder(ui)
                .serienNummer(new Seriennummer("SN12345"))
                .pzn(new PZN(12345678))
                .chargenNummer(new Chargennummer("CH789"))
                .medikamentenName("Aspirin")
                .wirkstoffBezeichnung("Acetylsalicylsäure")
                .lagerortId(LagerortID.fromString("1-A"))
                .build()
        );
        
        // Überprüfen (Assert)
        assertTrue(exception.getMessage().contains("Ablaufdatum muss gesetzt sein"), 
                "Die Fehlermeldung sollte auf das fehlende Ablaufdatum hinweisen");
    }

    @Test
    @DisplayName("Sollte eine Exception werfen, wenn der Lagerort null ist")
    void sollteExceptionBeiNullLagerortWerfen() {
        // Gegeben (Arrange)
        UniqueIdentifier ui = new UniqueIdentifier(12345678, "SN12345", "CH789", YearMonth.of(2025, 12));
        
        // Ausführen (Act)
        IllegalStateException exception = assertThrows(
            IllegalStateException.class,
            () -> new Medikament.Builder(ui)
                .serienNummer(new Seriennummer("SN12345"))
                .pzn(new PZN(12345678))
                .chargenNummer(new Chargennummer("CH789"))
                .medikamentenName("Aspirin")
                .wirkstoffBezeichnung("Acetylsalicylsäure")
                .ablaufDatum(YearMonth.of(2025, 12))
                .build()
        );
        
        // Überprüfen (Assert)
        assertTrue(exception.getMessage().contains("Lagerort muss angegeben sein"), 
                "Die Fehlermeldung sollte auf den fehlenden Lagerort hinweisen");
    }

    @Test
    @DisplayName("Builder sollte Exception werfen, wenn UniqueIdentifier null ist")
    void builderSollteExceptionWerfenWennUniqueIdentifierNullIst() {
        // Gegeben (Arrange)      
        UniqueIdentifier ui = null;

        // Ausführen (Act)
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> new Medikament.Builder(ui)
        );

        // Überprüfen (Assert)
        assertEquals("Pharmazeutische Kennung darf nicht null sein", exception.getMessage(),
                "Die Fehlermeldung sollte auf den ungültigen UniqueIdentifier hinweisen");
    }

    @Test
    @DisplayName("Builder.dosierung sollte Exception werfen, wenn Dosierung null ist")
    void builderDosierungSollteExceptionWerfenWennDosierungNullIst() {
        // Gegeben (Arrange)
        UniqueIdentifier ui = new UniqueIdentifier(
                12345678,
                "SN12345",
                "CH789",
                YearMonth.of(2025, 12)
        );
        Dosierung dosierung = null;

        // Ausführen (Act)
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> new Medikament.Builder(ui)
                    .dosierung(dosierung)
        );

        // Überprüfen (Assert)
        assertEquals("Dosierung darf nicht null sein!", exception.getMessage(),
                "Die Fehlermeldung sollte auf die ungültige Dosierung hinweisen");
    }
} 