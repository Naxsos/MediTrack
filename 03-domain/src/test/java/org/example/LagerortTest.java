package org.example;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests für die Lagerort-Klasse.
 */
class LagerortTest {

    @Test
    @DisplayName("Sollte einen gültigen Lagerort erstellen")
    void sollteEinenGueltigenLagerortErstellen() {
        // Gegeben (Arrange)
        String bezeichnung = "Medikamentenlager";
        int stockwerk = 2;
        String raumnummer = "A101";
        
        // Ausführen (Act)
        Lagerort lagerort = new Lagerort(bezeichnung, stockwerk, raumnummer);
        
        // Überprüfen (Assert)
        assertEquals(bezeichnung, lagerort.getBezeichnung(),
                "Die Bezeichnung sollte übereinstimmen");
        assertEquals(stockwerk, lagerort.getStockwerk(),
                "Das Stockwerk sollte übereinstimmen");
        assertEquals(raumnummer, lagerort.getRaumnummer(),
                "Die Raumnummer sollte übereinstimmen");
        
        LagerortID expectedId = LagerortID.fromString(stockwerk + "-" + raumnummer);
        assertEquals(expectedId.toString(), lagerort.getLagerortId().toString(),
                "Die LagerortID sollte korrekt aus Stockwerk und Raumnummer erstellt werden");
    }
    
    @Test
    @DisplayName("Sollte eine Exception werfen, wenn die Raumnummer null oder leer ist")
    void sollteEineExceptionWerfenWennDieRaumnummerNullOderLeerIst() {
        // Gegeben (Arrange)    
        String bezeichnung = "Medikamentenlager";
        int stockwerk = 2;
        String invalidRaumnummer = " ";
        String invalidRaumnummer2 = "\t";
        String invalidRaumnummer3 = "\n";
        
        // Ausführen (Act)
        Exception exception = assertThrows(
            IllegalArgumentException.class,
            () -> new Lagerort(bezeichnung, stockwerk, invalidRaumnummer)
        );
        Exception exception2 = assertThrows(
            IllegalArgumentException.class,
            () -> new Lagerort(bezeichnung, stockwerk, invalidRaumnummer2)
        );
        Exception exception3 = assertThrows(
            IllegalArgumentException.class,
            () -> new Lagerort(bezeichnung, stockwerk, invalidRaumnummer3)
        );
        // Überprüfen (Assert)
        assertTrue(exception.getMessage().contains("Raumnummer darf nicht leer sein"),
                "Die Fehlermeldung sollte auf die ungültige Raumnummer hinweisen");
        assertTrue(exception2.getMessage().contains("Raumnummer darf nicht leer sein"),
                "Die Fehlermeldung sollte auf die ungültige Raumnummer hinweisen");
        assertTrue(exception3.getMessage().contains("Raumnummer darf nicht leer sein"),
                "Die Fehlermeldung sollte auf die ungültige Raumnummer hinweisen");
    }
    
    @Test
    @DisplayName("Sollte die Bezeichnung ändern können")
    void sollteDieBezeichnungAendernKoennen() {
        // Gegeben (Arrange)
        Lagerort lagerort = new Lagerort("Alte Bezeichnung", 2, "A101");
        String neueBezeichnung = "Neue Bezeichnung";
        
        // Ausführen (Act)
        lagerort.setBezeichnung(neueBezeichnung);
        
        // Überprüfen (Assert)
        assertEquals(neueBezeichnung, lagerort.getBezeichnung(),
                "Die Bezeichnung sollte aktualisiert werden");
    }

} 