package de.meditrack;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests für die Chargennummer-Klasse.
 */
class ChargennummerTest {

    @Test
    @DisplayName("Sollte eine gültige Chargennummer erstellen")
    void sollteGueltigeChargennummerErstellen() {
        // Gegeben (Arrange)
        String validChargennummer = "ABC123";
        
        // Ausführen (Act)
        Chargennummer chargennummer = new Chargennummer(validChargennummer);
        
        // Überprüfen (Assert)
        assertEquals(validChargennummer, chargennummer.getNummer(),
                "Die erstellte Chargennummer sollte den angegebenen Wert haben");
    }
    
    @Test
    @DisplayName("Sollte eine Exception werfen, wenn die Chargennummer null oder zu lang ist")
    void sollteExceptionWerfenWennChargennummerUngueltig() {
        // Gegeben (Arrange)
        String invalidChargennummer = "ABCDEFGHIJK123456789";

        // Ausführen (Act)
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> new Chargennummer(invalidChargennummer)
        );
        
        // Überprüfen (Assert)
        assertTrue(exception.getMessage().contains("Ungültige Chargennummer"),
                "Die Fehlermeldung sollte auf die ungültige Chargennummer hinweisen");
    }
    
    @Test
    @DisplayName("toString sollte die Chargennummer als String zurückgeben")
    void sollteChargennummerAlsStringZurueckgeben() {
        // Gegeben (Arrange)
        String nummer = "ABC123";
        Chargennummer chargennummer = new Chargennummer(nummer);
        
        // Ausführen (Act)
        String result = chargennummer.toString();
        
        // Überprüfen (Assert)  
        assertEquals(nummer, result, "toString sollte die Chargennummer als String zurückgeben");
    }
} 