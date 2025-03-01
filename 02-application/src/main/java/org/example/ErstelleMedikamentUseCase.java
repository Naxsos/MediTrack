package org.example;

import java.time.YearMonth;

public class ErstelleMedikamentUseCase {
    private final MedikamenteSpeicher medikamenteSpeicher;

    public ErstelleMedikamentUseCase(MedikamenteSpeicher medikamenteSpeicher) {
        this.medikamenteSpeicher = medikamenteSpeicher;
    }

    public Medikament erstelleMedikament(int pzn, String serienNummer, String chargenNummer, String medikamentenName, String wirkstoffBezeichnung, String ablaufDatum){
        YearMonth formatiertesAblaufDatum = LocalDateParser.parseDate(ablaufDatum, Konstanten.ABLAUF_DATUM_FORMAT);
        UniqueIdentifier ui = erstelleUI(pzn,serienNummer, chargenNummer,formatiertesAblaufDatum);

        //Medikament medikament = new Medikament(ui,pzn,serienNummer, chargenNummer, medikamentenName,wirkstoffBezeichnung,formatiertesAblaufDatum);
        Medikament medikament = new Medikament.Builder(ui)
                .serienNummer(new Seriennummer(serienNummer))
                .pzn(new PZN(pzn))
                .chargenNummer(new Chargennummer(chargenNummer))
                .medikamentenName(medikamentenName)
                .wirkstoffBezeichnung(wirkstoffBezeichnung)
                .ablaufDatum(formatiertesAblaufDatum)
                .build();
        System.out.println(medikament);
        return medikamenteSpeicher.speichern(medikament);
    }

    public UniqueIdentifier erstelleUI(int pzn, String serienNummer, String chargenNummer, YearMonth ablaufdatum){
        return new UniqueIdentifier(pzn, serienNummer, chargenNummer, ablaufdatum);
    }
}
