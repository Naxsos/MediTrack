package de.meditrack;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests für die Seriennummer-Klasse.
 */
class SeriennummerTest {

    @Test
    @DisplayName("Sollte eine gültige Seriennummer erstellen")
    void sollGueltigeSeriennummerErstellen() {
        // Gegeben (Arrange)
        String validSeriennummer = "SN12345";
        
        // Ausführen (Act)
        Seriennummer seriennummer = new Seriennummer(validSeriennummer);
        
        // Überprüfen (Assert)
        assertEquals(validSeriennummer, seriennummer.getNummer(),
                "Die erstellte Seriennummer sollte den angegebenen Wert haben");
    }
    
    @Test
    @DisplayName("Sollte eine Exception werfen, wenn die Seriennummer null oder zu lang ist")
    void sollExceptionWerfenWennSeriennummerNullIst() {
        // Gegeben (Arrange)
        String invalidSeriennummer = null;
        String invalidSeriennummer2 = "SN12345123425463574685AGHFLSHJFLKDAHFUJILEUFGLBVHNJH";

        // Ausführen (Act)
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> new Seriennummer(invalidSeriennummer)
        );

        IllegalArgumentException exception2 = assertThrows(
            IllegalArgumentException.class,
            () -> new Seriennummer(invalidSeriennummer2)
        );

        System.out.println(exception.getMessage());
        System.out.println(exception2.getMessage());
        
        // Überprüfen (Assert)
        assertTrue(exception.getMessage().contains("Ungültige Seriennummer"),
                "Die Fehlermeldung sollte auf die null Seriennummer hinweisen");
        assertTrue(exception2.getMessage().contains("Ungültige Seriennummer"),
                "Die Fehlermeldung sollte auf die zu lange Seriennummer hinweisen");
    }
    
    @Test
    @DisplayName("Sollte eine Exception werfen, wenn die Seriennummer zu lang ist")
    void sollExceptionWerfenWennSeriennummerZuLangIst() {
        // Gegeben (Arrange)
        String tooLongSeriennummer = "12345678901234567890";    
        // Ausführen (Act)
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> new Seriennummer(tooLongSeriennummer)
        );
        
        // Überprüfen (Assert)
        assertTrue(exception.getMessage().contains("Ungültige Seriennummer"),
                "Die Fehlermeldung sollte auf die ungültige Seriennummer hinweisen");
    }
    
    @Test
    @DisplayName("Sollte gültige Seriennummer akzeptieren, die genau unter dem Grenzwert liegt")
    void sollGueltigeSeriennummerAkzeptierenDieGenauUnterDemGrenzwertLiegt() {
        // Gegeben (Arrange)
        String validButLongSeriennummer = "12345678901234567"; // 17 Zeichen (knapp unter dem Limit)
        
        // Ausführen (Act)
        Seriennummer seriennummer = new Seriennummer(validButLongSeriennummer);
        
        // Überprüfen (Assert)
        assertEquals(validButLongSeriennummer, seriennummer.getNummer(),
                "Die erstellte Seriennummer sollte den angegebenen Wert haben");
    }
    
    @Test
    @DisplayName("Sollte korrekte Gleichheit für identische Seriennummern zurückgeben")
    void sollKorrekteGleichheitFurIdentischeSeriennummernZuruckgeben() {
        // Gegeben (Arrange)                
        Seriennummer seriennummer1 = new Seriennummer("ABC123");
        Seriennummer seriennummer2 = new Seriennummer("ABC123");
        Seriennummer seriennummer3 = new Seriennummer("XYZ789");
        
        // Ausführen (Act)
        boolean areEqual = seriennummer1.equals(seriennummer2);
        boolean areNotEqual = !seriennummer1.equals(seriennummer3);
        int hashCode1 = seriennummer1.hashCode();
        int hashCode2 = seriennummer2.hashCode();
        
        // Überprüfen (Assert)  
        assertTrue(areEqual, "Seriennummern mit gleichem Wert sollten gleich sein");
        assertTrue(areNotEqual, "Seriennummern mit unterschiedlichen Werten sollten nicht gleich sein");
        assertEquals(hashCode1, hashCode2,
                "Hash-Codes für gleiche Seriennummern sollten identisch sein");
    }
    
    @Test
    @DisplayName("toString sollte die Seriennummer als String zurückgeben")
    void sollSeriennummerAlsStringZuruckgeben() {
        // Gegeben (Arrange)          
        String nummer = "ABC123";
        Seriennummer seriennummer = new Seriennummer(nummer);
        
        // Ausführen (Act)
        String result = seriennummer.toString();
        
        // Überprüfen (Assert)  
        assertEquals(nummer, result, "toString sollte die Seriennummer als String zurückgeben");
    }
} 