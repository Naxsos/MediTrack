package org.example;

import java.time.YearMonth;

public class MedikamenteVerwaltenUseCase {
    private MedikamenteSpeicher medikamenteSpeicher;

    public MedikamenteVerwaltenUseCase(MedikamenteSpeicher medikamenteSpeicher) {
        this.medikamenteSpeicher = medikamenteSpeicher;
    }

    public Medikament erstelleMedikament(int pzn, String serienNummer, String chargenNummer, String medikamentenName, String wirkstoffBezeichnung, String ablaufDatum, String lagerortId){
        YearMonth formatiertesAblaufDatum = LocalDateParser.parseDate(ablaufDatum, Konstanten.ABLAUF_DATUM_FORMAT);
        UniqueIdentifier ui = erstelleUI(pzn,serienNummer, chargenNummer,formatiertesAblaufDatum);
        LagerortID lagerortID = LagerortID.fromString(lagerortId);

        Medikament medikament = new Medikament.Builder(ui)
                .serienNummer(new Seriennummer(serienNummer))
                .pzn(new PZN(pzn))
                .chargenNummer(new Chargennummer(chargenNummer))
                .medikamentenName(medikamentenName)
                .wirkstoffBezeichnung(wirkstoffBezeichnung)
                .ablaufDatum(formatiertesAblaufDatum)
                .lagerortId(lagerortID)
                .build();
        System.out.println(medikament);
        return medikamenteSpeicher.speichern(medikament);
    }

    public UniqueIdentifier erstelleUI(int pzn, String serienNummer, String chargenNummer, YearMonth ablaufdatum){
        return new UniqueIdentifier(pzn, serienNummer, chargenNummer, ablaufdatum);
    }

    public void medikamentEntfernen(String ui) {
        medikamenteSpeicher.entfernenViaUI(ui);
    }
}
