package org.example;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests für die PZN (Pharmazentralnummer) Wertobjekt-Klasse.
 */
class PZNTest {

    @Test
    @DisplayName("Sollte eine gültige PZN erstellen")
    void shouldCreateValidPZN() {
        // Gegeben (Arrange)
        int validPZN = 12345678;

        // Ausführen (Act)
        PZN pzn = new PZN(validPZN);

        // Überprüfen (Assert)
        assertEquals(validPZN, pzn.getNummer(), "Die erstellte PZN sollte den angegebenen Wert haben");
    }

    @Test
    @DisplayName("Sollte eine Exception werfen, wenn die PZN zu klein ist")
    void shouldThrowExceptionWhenPZNTooSmall() {
        // Gegeben (Arrange)
        int invalidPZN = 999999;
        int invalidPZN2 = 0;
        int invalidPZN3 = -1;
        int invalidPZN4 = -1000000;

        // Ausführen (Act)
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> new PZN(invalidPZN)
        );
        IllegalArgumentException exception2 = assertThrows(
            IllegalArgumentException.class,
            () -> new PZN(invalidPZN2)
        );
        IllegalArgumentException exception3 = assertThrows(
            IllegalArgumentException.class,
            () -> new PZN(invalidPZN3)
        );
        IllegalArgumentException exception4 = assertThrows(
            IllegalArgumentException.class,
            () -> new PZN(invalidPZN4)
        );
        // Überprüfen (Assert)
        assertTrue(exception.getMessage().contains("Ungültige PZN"),
                "Die Fehlermeldung sollte auf die ungültige PZN hinweisen");
        assertTrue(exception2.getMessage().contains("Ungültige PZN"),
                "Die Fehlermeldung sollte auf die ungültige PZN hinweisen");
        assertTrue(exception3.getMessage().contains("Ungültige PZN"),
                "Die Fehlermeldung sollte auf die ungültige PZN hinweisen");
        assertTrue(exception4.getMessage().contains("Ungültige PZN"),
                "Die Fehlermeldung sollte auf die ungültige PZN hinweisen");    
    }

    @Test
    @DisplayName("Sollte eine Exception werfen, wenn die PZN zu groß ist")
    void shouldThrowExceptionWhenPZNTooLarge() {
        // Gegeben (Arrange)
        int invalidPZN = 100000000;
        int invalidPZN2 = 999999999;

        // Ausführen (Act)
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> new PZN(invalidPZN)
        );
        IllegalArgumentException exception2 = assertThrows(
            IllegalArgumentException.class,
            () -> new PZN(invalidPZN2)
        );
        
        // Überprüfen (Assert)  
        assertTrue(exception.getMessage().contains("Ungültige PZN"),
                "Die Fehlermeldung sollte auf die ungültige PZN hinweisen");
        assertTrue(exception2.getMessage().contains("Ungültige PZN"),
                "Die Fehlermeldung sollte auf die ungültige PZN hinweisen");
    }   

    @Test
    @DisplayName("toString sollte die PZN als String zurückgeben")
    void shouldReturnPZNAsString() {
        // Gegeben (Arrange)
        int number = 12345678;
        PZN pzn = new PZN(number);

        // Ausführen (Act)
        String result = pzn.toString();

        // Überprüfen (Assert)  
        assertEquals(String.valueOf(number), result, "toString sollte die PZN als String zurückgeben");
    }
} 