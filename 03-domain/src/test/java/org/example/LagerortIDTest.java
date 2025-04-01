package org.example;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests für die LagerortID-Klasse.
 */
class LagerortIDTest {

    @Test
    @DisplayName("Sollte eine gültige LagerortID aus einem korrekten String erstellen")
    void sollteGueltigeLagerortIDAusEinemKorrektenStringErstellen() {
        // Gegeben (Arrange)
        String validID = "3-A101";
        
        // Ausführen (Act)
        LagerortID lagerortID = LagerortID.fromString(validID);
        
        // Überprüfen (Assert)
        assertEquals(validID, lagerortID.toString(),
                "Die erstellte LagerortID sollte den angegebenen Wert haben");
    }
    
    @Test
    @DisplayName("Sollte eine Exception werfen, wenn die LagerortID ein ungültiges Format hat")
    void sollteEineExceptionWerfenWennDieLagerortIDAusEinemUngueltigenFormatHat() {
        // Gegeben (Arrange)
        String invalidID1 = "A-101";
        String invalidID2 = "101";
        String invalidID3 = "-101";
        String invalidID4 = "1-";
        String invalidID5 = "1.101";
        String invalidID6 = "One-OneOOne";
        String invalidID7 = "";
        String invalidID8 = null;

        // Ausführen (Act)      
        IllegalArgumentException exception1 = assertThrows(
            IllegalArgumentException.class,
            () -> LagerortID.fromString(invalidID1)
        );
        IllegalArgumentException exception2 = assertThrows(
            IllegalArgumentException.class,
            () -> LagerortID.fromString(invalidID2)
        );
        IllegalArgumentException exception3 = assertThrows(
            IllegalArgumentException.class,
            () -> LagerortID.fromString(invalidID3)
        );
        IllegalArgumentException exception4 = assertThrows(
            IllegalArgumentException.class,
            () -> LagerortID.fromString(invalidID4)
        );
        IllegalArgumentException exception5 = assertThrows(
            IllegalArgumentException.class,
            () -> LagerortID.fromString(invalidID5)
        );
        IllegalArgumentException exception6 = assertThrows(
            IllegalArgumentException.class,
            () -> LagerortID.fromString(invalidID6)
        );
        IllegalArgumentException exception7 = assertThrows(
            IllegalArgumentException.class,
            () -> LagerortID.fromString(invalidID7)
        );
        IllegalArgumentException exception8 = assertThrows(
            IllegalArgumentException.class,
            () -> LagerortID.fromString(invalidID8)
        );

        // Überprüfen (Assert)
        assertTrue(exception1.getMessage().contains("Falsches Format für LagerortID."),
                "Die Fehlermeldung sollte auf das ungültige Format hinweisen");
        assertTrue(exception2.getMessage().contains("Falsches Format für LagerortID."),
                "Die Fehlermeldung sollte auf das ungültige Format hinweisen");
        assertTrue(exception3.getMessage().contains("Falsches Format für LagerortID."),
                "Die Fehlermeldung sollte auf das ungültige Format hinweisen");
        assertTrue(exception4.getMessage().contains("Falsches Format für LagerortID."),
                    "Die Fehlermeldung sollte auf das ungültige Format hinweisen");
        assertTrue(exception5.getMessage().contains("Falsches Format für LagerortID."),
                "Die Fehlermeldung sollte auf das ungültige Format hinweisen");
        assertTrue(exception6.getMessage().contains("Falsches Format für LagerortID."),
                "Die Fehlermeldung sollte auf das ungültige Format hinweisen");
        assertTrue(exception7.getMessage().contains("Falsches Format für LagerortID."),
                "Die Fehlermeldung sollte auf das ungültige Format hinweisen");
        assertTrue(exception8.getMessage().contains("Falsches Format für LagerortID."),
                "Die Fehlermeldung sollte auf das ungültige Format hinweisen");
    }
    
    @Test
    @DisplayName("Sollte verschiedene gültige Formate akzeptieren")
    void sollteGueltigeFormateAkzeptieren() {
        // Gegeben (Arrange)
        String validID = "1-A";
        String validID2 = "10-123";
        String validID3 = "2-A123";
        String validID4 = "3-123A";
        String validID5 = "42-X42Y";
        // Ausführen (Act)
        LagerortID lagerortID = LagerortID.fromString(validID);
        LagerortID lagerortID2 = LagerortID.fromString(validID2);
        LagerortID lagerortID3 = LagerortID.fromString(validID3);
        LagerortID lagerortID4 = LagerortID.fromString(validID4);
        LagerortID lagerortID5 = LagerortID.fromString(validID5);

        // Überprüfen (Assert)
        assertEquals(validID, lagerortID.toString(),
                "Die erstellte LagerortID sollte den angegebenen Wert haben");
        assertEquals(validID2, lagerortID2.toString(),
                "Die erstellte LagerortID sollte den angegebenen Wert haben");
        assertEquals(validID3, lagerortID3.toString(),
                "Die erstellte LagerortID sollte den angegebenen Wert haben");
        assertEquals(validID4, lagerortID4.toString(),
                "Die erstellte LagerortID sollte den angegebenen Wert haben");
        assertEquals(validID5, lagerortID5.toString(),
                "Die erstellte LagerortID sollte den angegebenen Wert haben");
    }
    
    @Test
    @DisplayName("Sollte korrekte Gleichheit für identische LagerortIDs zurückgeben")
    void sollteKorrekteGleichheitFuerIdentischeLagerortIDsZurueckgeben() {
        // Gegeben (Arrange)
        LagerortID id1 = LagerortID.fromString("1-A101");
        LagerortID id2 = LagerortID.fromString("1-A101");
        LagerortID id3 = LagerortID.fromString("2-B202");
        
        // Ausführen (Act)
        boolean areEqual = id1.equals(id2);
        boolean areNotEqual = !id1.equals(id3);
        int hashCode1 = id1.hashCode();
        int hashCode2 = id2.hashCode();
        
        // Überprüfen (Assert)
        assertTrue(areEqual, "LagerortIDs mit gleichem Wert sollten gleich sein");
        assertTrue(areNotEqual, "LagerortIDs mit unterschiedlichen Werten sollten nicht gleich sein");
        assertEquals(hashCode1, hashCode2,
                "Hash-Codes für gleiche LagerortIDs sollten identisch sein");
    }
    
    @Test
    @DisplayName("toString sollte die LagerortID als String zurückgeben")
    void sollteLagerortIDAlsStringZurueckgeben() {
        // Gegeben (Arrange)
        String idString = "1-A101";
        LagerortID lagerortID = LagerortID.fromString(idString);
        
        // Ausführen (Act)
        String result = lagerortID.toString();
        
        // Überprüfen (Assert)
        assertEquals(idString, result, "toString sollte die LagerortID als String zurückgeben");
    }
} 