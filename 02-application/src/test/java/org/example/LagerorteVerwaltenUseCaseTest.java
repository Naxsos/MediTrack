package org.example;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Tests für die LagerorteVerwaltenUseCase-Klasse.
 * Verwendet Mockito, um das LagerortSpeicher-Interface zu mocken.
 */
@ExtendWith(MockitoExtension.class)
class LagerorteVerwaltenUseCaseTest {

    @Mock
    private LagerortSpeicher lagerortSpeicherMock;
    
    @Captor
    private ArgumentCaptor<Lagerort> lagerortCaptor;
    
    @Captor
    private ArgumentCaptor<String> stringCaptor;
    
    @Test
    @DisplayName("erstelleLagerort sollte neuen Lagerort erstellen und speichern")
    void sollteNeuenLagerortErstellenUndSpeichern() {
        // Gegeben (Arrange)
        LagerorteVerwaltenUseCase useCase = new LagerorteVerwaltenUseCase(lagerortSpeicherMock);
        String bezeichnung = "Hauptlager";
        int stockwerk = 2;
        String raumnummer = "A123";
        String lagerortId = "2-A123";
        
        when(lagerortSpeicherMock.existiert(lagerortId)).thenReturn(false);
        
        Lagerort testLagerort = new Lagerort(bezeichnung, stockwerk, raumnummer);
        when(lagerortSpeicherMock.speichern(any(Lagerort.class))).thenReturn(testLagerort);
        
        // Ausführen (Act)
        Lagerort result = useCase.erstelleLagerort(bezeichnung, stockwerk, raumnummer);
        
        // Überprüfen (Assert)
        verify(lagerortSpeicherMock, times(1)).existiert(lagerortId);
        verify(lagerortSpeicherMock, times(1)).speichern(lagerortCaptor.capture());
        
        Lagerort capturedLagerort = lagerortCaptor.getValue();
        assertEquals(bezeichnung, capturedLagerort.getBezeichnung(), "Die Bezeichnung sollte korrekt gesetzt sein");
        assertEquals(stockwerk, capturedLagerort.getStockwerk(), "Das Stockwerk sollte korrekt gesetzt sein");
        assertEquals(raumnummer, capturedLagerort.getRaumnummer(), "Die Raumnummer sollte korrekt gesetzt sein");
        
        assertSame(testLagerort, result, "Die Methode sollte den gespeicherten Lagerort zurückgeben");
    }
    
    @Test
    @DisplayName("erstelleLagerort sollte Exception werfen, wenn Lagerort bereits existiert")
    void sollteExceptionWerfenWennLagerortBereitsExistiert() {
        // Gegeben (Arrange)
        LagerorteVerwaltenUseCase useCase = new LagerorteVerwaltenUseCase(lagerortSpeicherMock);
        String bezeichnung = "Hauptlager";
        int stockwerk = 2;
        String raumnummer = "A123";
        String lagerortId = "2-A123";
        
        when(lagerortSpeicherMock.existiert(lagerortId)).thenReturn(true);
        
        // Ausführen (Act)
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> useCase.erstelleLagerort(bezeichnung, stockwerk, raumnummer)
        );
        
        // Überprüfen (Assert)
        assertTrue(exception.getMessage().contains("existiert bereits"), 
                "Die Fehlermeldung sollte angeben, dass der Lagerort bereits existiert");
        verify(lagerortSpeicherMock, times(1)).existiert(lagerortId);
        verify(lagerortSpeicherMock, never()).speichern(any(Lagerort.class));
    }
    
    @Test
    @DisplayName("loescheLagerort sollte Lagerort löschen, wenn er existiert")
    void sollteLagerortLoeschenWennErExistiert() {
        // Gegeben (Arrange)
        LagerorteVerwaltenUseCase useCase = new LagerorteVerwaltenUseCase(lagerortSpeicherMock);
        String lagerortId = "2-A123";
        
        when(lagerortSpeicherMock.existiert(lagerortId)).thenReturn(true);
        when(lagerortSpeicherMock.loeschen(lagerortId)).thenReturn(true);
        
        // Ausführen (Act)
        boolean result = useCase.loescheLagerort(lagerortId);
        
        // Überprüfen (Assert)
        verify(lagerortSpeicherMock, times(1)).existiert(lagerortId);
        verify(lagerortSpeicherMock, times(1)).loeschen(lagerortId);
        assertTrue(result, "Sollte true zurückgeben, wenn Lagerort erfolgreich gelöscht wurde");
    }
    
    @Test
    @DisplayName("loescheLagerort sollte false zurückgeben, wenn Lagerort nicht existiert")
    void sollteFalseZurueckgebenWennLagerortNichtExistiert() {
        // Gegeben (Arrange)
        LagerorteVerwaltenUseCase useCase = new LagerorteVerwaltenUseCase(lagerortSpeicherMock);
        String lagerortId = "2-A123";
        
        when(lagerortSpeicherMock.existiert(lagerortId)).thenReturn(false);
        
        // Ausführen (Act)
        boolean result = useCase.loescheLagerort(lagerortId);
        
        // Überprüfen (Assert)
        verify(lagerortSpeicherMock, times(1)).existiert(lagerortId);
        verify(lagerortSpeicherMock, never()).loeschen(anyString());
        assertFalse(result, "Sollte false zurückgeben, wenn Lagerort nicht existiert");
    }
    
    @Test
    @DisplayName("loescheLagerort sollte false zurückgeben, wenn Löschen fehlschlägt")
    void sollteFalseZurueckgebenWennLoeschungFehlschlaegt() {
        // Gegeben (Arrange)
        LagerorteVerwaltenUseCase useCase = new LagerorteVerwaltenUseCase(lagerortSpeicherMock);
        String lagerortId = "2-A123";
        
        when(lagerortSpeicherMock.existiert(lagerortId)).thenReturn(true);
        when(lagerortSpeicherMock.loeschen(lagerortId)).thenReturn(false);
        
        // Ausführen (Act)
        boolean result = useCase.loescheLagerort(lagerortId);
        
        // Überprüfen (Assert)
        verify(lagerortSpeicherMock, times(1)).existiert(lagerortId);
        verify(lagerortSpeicherMock, times(1)).loeschen(lagerortId);
        assertFalse(result, "Sollte false zurückgeben, wenn Löschen fehlschlägt");
    }
    
    @Test
    @DisplayName("aktualisiereBezeichnung sollte Bezeichnung aktualisieren, wenn Lagerort existiert")
    void sollteBezeichnungAktualisierenWennLagerortExistiert() {
        // Gegeben (Arrange)
        LagerorteVerwaltenUseCase useCase = new LagerorteVerwaltenUseCase(lagerortSpeicherMock);
        String lagerortId = "2-A123";
        String alteBezeichnung = "Hauptlager";
        String neueBezeichnung = "Nebenlager";
        
        Lagerort lagerort = new Lagerort(alteBezeichnung, 2, "A123");
        Lagerort aktualisierterLagerort = new Lagerort(neueBezeichnung, 2, "A123");
        
        when(lagerortSpeicherMock.findeViaId(lagerortId)).thenReturn(Optional.of(lagerort));
        when(lagerortSpeicherMock.speichern(any(Lagerort.class))).thenReturn(aktualisierterLagerort);
        
        // Ausführen (Act)
        Optional<Lagerort> result = useCase.aktualisiereBezeichnung(lagerortId, neueBezeichnung);
        
        // Überprüfen (Assert)
        verify(lagerortSpeicherMock, times(1)).findeViaId(lagerortId);
        verify(lagerortSpeicherMock, times(1)).speichern(lagerortCaptor.capture());
        
        Lagerort capturedLagerort = lagerortCaptor.getValue();
        assertEquals(neueBezeichnung, capturedLagerort.getBezeichnung(), "Die Bezeichnung sollte aktualisiert sein");
        assertTrue(result.isPresent(), "Sollte ein gefülltes Optional zurückgeben");
        assertEquals(neueBezeichnung, result.get().getBezeichnung(), "Die Bezeichnung im Ergebnis sollte aktualisiert sein");
    }
    
    @Test
    @DisplayName("findeAlleLagerorte sollte alle Lagerorte zurückgeben")
    void sollteAlleLagerorteZurueckgeben() {
        // Gegeben (Arrange)
        LagerorteVerwaltenUseCase useCase = new LagerorteVerwaltenUseCase(lagerortSpeicherMock);
        Lagerort lagerort1 = new Lagerort("Hauptlager", 2, "A123");
        Lagerort lagerort2 = new Lagerort("Nebenlager", 3, "B456");
        List<Lagerort> lagerorte = Arrays.asList(lagerort1, lagerort2);
        
        when(lagerortSpeicherMock.findeAlle()).thenReturn(lagerorte);
        
        // Ausführen (Act)
        List<Lagerort> result = useCase.findeAlleLagerorte();
        
        // Überprüfen (Assert)
        verify(lagerortSpeicherMock, times(1)).findeAlle();
        assertEquals(lagerorte, result, "Sollte die Liste aller Lagerorte zurückgeben");
    }
    
    @Test
    @DisplayName("findeLagerortViaId sollte Lagerort zurückgeben, wenn er existiert")
    void sollteLagerortViaIdZurueckgebenWennErExistiert() {
        // Gegeben (Arrange)
        LagerorteVerwaltenUseCase useCase = new LagerorteVerwaltenUseCase(lagerortSpeicherMock);
        String lagerortId = "2-A123";
        Lagerort lagerort = new Lagerort("Hauptlager", 2, "A123");
        
        when(lagerortSpeicherMock.findeViaId(lagerortId)).thenReturn(Optional.of(lagerort));
        
        // Ausführen (Act)
        Optional<Lagerort> result = useCase.findeLagerortViaId(lagerortId);
        
        // Überprüfen (Assert)
        verify(lagerortSpeicherMock, times(1)).findeViaId(lagerortId);
        assertTrue(result.isPresent(), "Sollte ein gefülltes Optional zurückgeben");
        assertEquals(lagerort, result.get(), "Sollte den gefundenen Lagerort zurückgeben");
    }
    
    @Test
    @DisplayName("findeLagerortViaId sollte leeres Optional zurückgeben, wenn Lagerort nicht existiert")
    void sollteLeeresOptionalZurueckgebenWennLagerortNichtExistiert() {
        // Gegeben (Arrange)
        LagerorteVerwaltenUseCase useCase = new LagerorteVerwaltenUseCase(lagerortSpeicherMock);
        String lagerortId = "2-A123";
        
        when(lagerortSpeicherMock.findeViaId(lagerortId)).thenReturn(Optional.empty());
        
        // Ausführen (Act)
        Optional<Lagerort> result = useCase.findeLagerortViaId(lagerortId);
        
        // Überprüfen (Assert)
        verify(lagerortSpeicherMock, times(1)).findeViaId(lagerortId);
        assertTrue(result.isEmpty(), "Sollte ein leeres Optional zurückgeben, wenn Lagerort nicht existiert");
    }
    
    @Test
    @DisplayName("findeLagerorteViaBezeichnung sollte Lagerorte mit passender Bezeichnung zurückgeben")
    void sollteLagerorteMitPassenderBezeichnungZurueckgeben() {
        // Gegeben (Arrange)
        LagerorteVerwaltenUseCase useCase = new LagerorteVerwaltenUseCase(lagerortSpeicherMock);
        String bezeichnung = "Hauptlager";
        Lagerort lagerort1 = new Lagerort(bezeichnung, 2, "A123");
        Lagerort lagerort2 = new Lagerort(bezeichnung, 3, "B456");
        List<Lagerort> lagerorte = Arrays.asList(lagerort1, lagerort2);
        
        when(lagerortSpeicherMock.findeViaBezeichnung(bezeichnung)).thenReturn(lagerorte);
        
        // Ausführen (Act)
        List<Lagerort> result = useCase.findeLagerorteViaBezeichnung(bezeichnung);
        
        // Überprüfen (Assert)
        verify(lagerortSpeicherMock, times(1)).findeViaBezeichnung(bezeichnung);
        assertEquals(lagerorte, result, "Sollte die Liste der Lagerorte mit passender Bezeichnung zurückgeben");
    }
    
    @Test
    @DisplayName("findeLagerorteViaBezeichnung sollte leere Liste zurückgeben, wenn keine Lagerorte gefunden werden")
    void sollteLeereListeZurueckgebenWennKeineLagerorteGefundenWerden() {
        // Gegeben (Arrange)
        LagerorteVerwaltenUseCase useCase = new LagerorteVerwaltenUseCase(lagerortSpeicherMock);
        String bezeichnung = "Nicht existierende Bezeichnung";
        
        when(lagerortSpeicherMock.findeViaBezeichnung(bezeichnung)).thenReturn(Collections.emptyList());
        
        // Ausführen (Act)
        List<Lagerort> result = useCase.findeLagerorteViaBezeichnung(bezeichnung);
        
        // Überprüfen (Assert)
        verify(lagerortSpeicherMock, times(1)).findeViaBezeichnung(bezeichnung);
        assertTrue(result.isEmpty(), "Sollte eine leere Liste zurückgeben, wenn keine Lagerorte gefunden werden");
    }
} 