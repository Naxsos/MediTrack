package de.meditrack;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests für die UniqueIdentifier-Klasse.
 */
class UniqueIdentifierTest {

    @Test
    @DisplayName("Sollte einen gültigen UniqueIdentifier erstellen")
    void sollGueltigenUniqueIdentifierErstellen() {
        // Gegeben (Arrange)
        int pzn = 12345678;
        String serienNummer = "SN12345";
        String chargenNummer = "CH123";
        YearMonth ablaufDatum = YearMonth.of(2025, 12);
        
        // Ausführen (Act)  
        UniqueIdentifier ui = new UniqueIdentifier(pzn, serienNummer, chargenNummer, ablaufDatum);
        
        // Überprüfen (Assert)
        assertEquals(pzn, ui.getPzn(), "PZN sollte übereinstimmen");
        assertEquals(serienNummer, ui.getSerienNummer(), "Seriennummer sollte übereinstimmen");
        assertEquals(chargenNummer, ui.getChargenNummer(), "Chargennummer sollte übereinstimmen");
        assertEquals(ablaufDatum, ui.getAblaufDatum(), "Ablaufdatum sollte übereinstimmen");
    }
    
    @Test
    @DisplayName("fromString sollte einen gültigen UniqueIdentifier aus einem korrekten String erstellen")
    void sollGueltigenUniqueIdentifierAusGueltigemStringErstellen() {
        // Gegeben (Arrange)  
        String validString = "01 12345678 21 SN12345 10 CH123 17 2025-12";
        
        // Ausführen (Act)
        UniqueIdentifier ui = UniqueIdentifier.fromString(validString);
        
        // Überprüfen (Assert)
        assertEquals(12345678, ui.getPzn(), "PZN sollte übereinstimmen");
        assertEquals("SN12345", ui.getSerienNummer(), "Seriennummer sollte übereinstimmen");
        assertEquals("CH123", ui.getChargenNummer(), "Chargennummer sollte übereinstimmen");
        assertEquals(YearMonth.of(2025, 12), ui.getAblaufDatum(), "Ablaufdatum sollte übereinstimmen");
    }

    @Test
    @DisplayName("fromString sollte eine Exception werfen, wenn der String null oder leer ist")
    void sollExceptionWerfenWennStringNullOderLeerIst() {
        // Gegeben (Arrange)
        String nullString = null;
        String emptyString = "";

        // Ausführen (Act)
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> UniqueIdentifier.fromString(nullString)
        );
        IllegalArgumentException exception2 = assertThrows(
            IllegalArgumentException.class,
            () -> UniqueIdentifier.fromString(emptyString)
        );

        // Überprüfen (Assert)      
        assertTrue(exception.getMessage().contains("Eingabe darf nicht null oder leer sein"),
                "Die Fehlermeldung sollte auf den ungültigen String hinweisen");
        assertTrue(exception2.getMessage().contains("Eingabe darf nicht null oder leer sein"),
                "Die Fehlermeldung sollte auf den ungültigen String hinweisen");
    }
    
    @Test
    @DisplayName("fromString sollte eine Exception werfen, wenn der String ein falsches Format hat")
    void sollExceptionWerfenWennStringFalschesFormatHat() {
        // Gegeben (Arrange)        
        String invalidString = "01 12345678 21 SN12345 10 CH123";
        String invalidString2 = "01 12345678 21 SN12345 10";
        String invalidString3 = "01 12345678 21";
        String invalidString4 = "01";
        String invalidString5 = "21 SN12345 10 CH123 17 2025-12";
        String invalidString6 = "02 12345678 21 SN12345 10 CH123 17 2025-12";

        // Ausführen (Act)
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> UniqueIdentifier.fromString(invalidString)
        );
        IllegalArgumentException exception2 = assertThrows(
            IllegalArgumentException.class,
            () -> UniqueIdentifier.fromString(invalidString2)
        );
        IllegalArgumentException exception3 = assertThrows(
            IllegalArgumentException.class,
            () -> UniqueIdentifier.fromString(invalidString3)
        );
        IllegalArgumentException exception4 = assertThrows(
            IllegalArgumentException.class,
            () -> UniqueIdentifier.fromString(invalidString4)
        );
        IllegalArgumentException exception5 = assertThrows(
            IllegalArgumentException.class,
            () -> UniqueIdentifier.fromString(invalidString5)
        );
        IllegalArgumentException exception6 = assertThrows(
            IllegalArgumentException.class,
            () -> UniqueIdentifier.fromString(invalidString6)
        );

        // Überprüfen (Assert)
        assertTrue(exception.getMessage().contains("Falsches Format: Es werden nur 8 Tokens erwartet, gefunden"),
                "Die Fehlermeldung sollte auf das ungültige Format hinweisen");
        assertTrue(exception2.getMessage().contains("Falsches Format: Es werden nur 8 Tokens erwartet, gefunden"),
                "Die Fehlermeldung sollte auf das ungültige Format hinweisen");
        assertTrue(exception3.getMessage().contains("Falsches Format: Es werden nur 8 Tokens erwartet, gefunden"),
                "Die Fehlermeldung sollte auf das ungültige Format hinweisen");
        assertTrue(exception4.getMessage().contains("Falsches Format: Es werden nur 8 Tokens erwartet, gefunden"),
                "Die Fehlermeldung sollte auf das ungültige Format hinweisen");
        assertTrue(exception5.getMessage().contains("Falsches Format: Es werden nur 8 Tokens erwartet, gefunden"),
                "Die Fehlermeldung sollte auf den falschen Ablaufdatum-Präfix hinweisen");
        assertTrue(exception6.getMessage().contains("Falscher Präfix für PNZ"),
                "Die Fehlermeldung sollte auf den falschen PZN-Präfix hinweisen");
    }
    
    @Test
    @DisplayName("fromString sollte eine Exception werfen, wenn PZN keine Zahl ist")
    void sollExceptionWerfenWennPZNKeineZahlIst() {
        // Gegeben (Arrange)
        String invalidString = "01 ABCDEFG 21 SN12345 10 CH123 17 2025-12"; // PZN ist keine Zahl
        
        // Ausführen (Act)
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> UniqueIdentifier.fromString(invalidString)
        );
        
        // Überprüfen (Assert)
        assertTrue(exception.getMessage().contains("Die PZN muss eine Zahl sein"),
                "Die Fehlermeldung sollte auf die ungültige PZN hinweisen");
    }
    
    @Test
    @DisplayName("fromString sollte eine Exception werfen, wenn das Ablaufdatum ein falsches Format hat")
    void sollExceptionWerfenWennAblaufdatumFalschesFormatHat() {
        // Gegeben (Arrange)
        String invalidString = "01 12345678 21 SN12345 10 CH123 17 25-12"; // Falsches Datumsformat
        
        // Ausführen (Act)
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> UniqueIdentifier.fromString(invalidString)
        );
        
        // Überprüfen (Assert)
        assertTrue(exception.getMessage().contains("Ablaufdatum muss im Format"),
                "Die Fehlermeldung sollte auf das ungültige Datumsformat hinweisen");
    }
    
    @Test
    @DisplayName("toString sollte einen korrekten String zurückgeben")
    void sollKorrektenStringZuruckgeben() {
        // Gegeben (Arrange)        
        int pzn = 12345678;
        String serienNummer = "SN12345";
        String chargenNummer = "CH123";
        YearMonth ablaufDatum = YearMonth.of(2025, 12);
        UniqueIdentifier ui = new UniqueIdentifier(pzn, serienNummer, chargenNummer, ablaufDatum);
        
        // Ausführen (Act)              
        String result = ui.toString();
        
        // Überprüfen (Assert)
        String expected = String.format("01 %s 21 %s 10 %s 17 %s",
                pzn, serienNummer, chargenNummer, 
                ablaufDatum.format(DateTimeFormatter.ofPattern(Konstanten.ABLAUF_DATUM_FORMAT)));
        assertEquals(expected, result, "toString sollte den korrekten formatierten String zurückgeben");
    }
    
} 