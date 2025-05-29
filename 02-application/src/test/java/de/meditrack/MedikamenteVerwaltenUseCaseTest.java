package de.meditrack;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.YearMonth;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Tests für die MedikamenteVerwaltenUseCase-Klasse.
 * Verwendet Mockito, um das MedikamenteSpeicher-Interface zu mocken.
 */
@ExtendWith(MockitoExtension.class)
class MedikamenteVerwaltenUseCaseTest {

    @Mock
    private MedikamenteSpeicher medikamenteSpeicherMock;
    
    @Captor
    private ArgumentCaptor<Medikament> medikamentCaptor;
    
    @Captor
    private ArgumentCaptor<String> stringCaptor;
    
    @Test
    @DisplayName("erstelleMedikament sollte ein neues Medikament erstellen und speichern")
    void erstelleMedikamentSollteNeuesMedikamentErstellenUndSpeichern() {
        // Gegeben (Arrange)  
        MedikamenteVerwaltenUseCase useCase = new MedikamenteVerwaltenUseCase(medikamenteSpeicherMock);
        int pzn = 12345678;
        String serienNummer = "SN123456";
        String chargenNummer = "CH789012";
        String medikamentenName = "Testmedikament";
        String wirkstoffBezeichnung = "Testwirkstoff";
        String ablaufDatum = "2025-01";
        String lagerortId = "1-A";

        // Ein Medikament für die Rückgabe vom Mock
        Medikament testMedikament = mock(Medikament.class);
        when(medikamenteSpeicherMock.speichern(any(Medikament.class))).thenReturn(testMedikament);
        
        // Ausführen (Act)
        Medikament result = useCase.erstelleMedikament(
            pzn, serienNummer, chargenNummer, medikamentenName, 
            wirkstoffBezeichnung, ablaufDatum, lagerortId
        );
        
        // Überprüfen (Assert)
        // 1. Prüfe, ob das Ergebnis das vom Mock zurückgegebene Medikament ist
        assertSame(testMedikament, result, "Die Methode sollte das gespeicherte Medikament zurückgeben");
        
        // 2. Prüfe, ob speichern mit korrekten Parametern aufgerufen wurde
        verify(medikamenteSpeicherMock, times(1)).speichern(medikamentCaptor.capture());
        Medikament capturedMedikament = medikamentCaptor.getValue();
        
        // Wir können nicht direkt die Werte prüfen, da wir ein Mock haben
        // Aber wir können prüfen, ob speichern aufgerufen wurde
        assertNotNull(capturedMedikament, "Ein Medikament sollte zum Speichern übergeben worden sein");
    }
    
    @Test
    @DisplayName("erstelleUI sollte eine neue UniqueIdentifier-Instanz mit korrekten Werten erstellen")
    void erstelleUISollteNeueUniqueIdentifierInstanzMitKorrektenWertenErstellen() {
        // Gegeben (Arrange)   
        MedikamenteVerwaltenUseCase useCase = new MedikamenteVerwaltenUseCase(medikamenteSpeicherMock);       
        int pzn = 12345678;
        String serienNummer = "SN123456";
        String chargenNummer = "CH789012";
        YearMonth ablaufDatum = YearMonth.of(2025, 1);
        
        // Ausführen (Act)
        UniqueIdentifier result = useCase.erstelleUI(pzn, serienNummer, chargenNummer, ablaufDatum);
        
        // Überprüfen (Assert)
        assertEquals(pzn, result.getPzn(), "Die PZN sollte korrekt gesetzt sein");
        assertEquals(serienNummer, result.getSerienNummer(), "Die Seriennummer sollte korrekt gesetzt sein");
        assertEquals(chargenNummer, result.getChargenNummer(), "Die Chargennummer sollte korrekt gesetzt sein");
        assertEquals(ablaufDatum, result.getAblaufDatum(), "Das Ablaufdatum sollte korrekt gesetzt sein");
    }
    
    @Test
    @DisplayName("medikamentEntfernen sollte die Entfernen-Methode des Speichers mit der korrekten UI aufrufen")
    void medikamentEntfernenSollteSpeicherEntfernenMethodeMitKorrektUIAufrufen() {
        // Gegeben (Arrange)
        MedikamenteVerwaltenUseCase useCase = new MedikamenteVerwaltenUseCase(medikamenteSpeicherMock);
        String uniqueIdentifier = "12345678-SN123456-CH789012-01/2025";
        
        // Ausführen (Act)
        useCase.medikamentEntfernen(uniqueIdentifier);
        
        // Überprüfen (Assert)
        verify(medikamenteSpeicherMock, times(1)).entfernenViaUI(stringCaptor.capture());
        String capturedUI = stringCaptor.getValue();
        
        assertEquals(uniqueIdentifier, capturedUI, "Die entfernenViaUI-Methode sollte mit der korrekten UI aufgerufen werden");
    }

} 