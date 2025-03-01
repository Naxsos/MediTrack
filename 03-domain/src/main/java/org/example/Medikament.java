package org.example;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Objects;

public class Medikament {

    // Eindeutige Identifikation
    private UniqueIdentifier ui;

    private String serienNummer;

    private int pzn;

    private String chargenNummer;

    private String medikamentenName;

    private String wirkstoffBezeichnung;

    private String hersteller;

    private Darreichungsform darreichungsform;

    private Dosierung dosierung;

    private YearMonth ablaufDatum;

    public Medikament(UniqueIdentifier ui, int pzn, String serienNummer, String chargenNummer, String medikamentenName, String wirkstoffBezeichnung, String hersteller, Darreichungsform darreichungsform, Dosierung dosierung, YearMonth ablaufDatum) {
        if (ui == null) {
            throw new IllegalArgumentException("Pharmazeutische Kennung darf nicht leer sein");
        }
        this.ui = ui;
        this.serienNummer = serienNummer;
        this.chargenNummer = chargenNummer;
        this.pzn = pzn;
        this.medikamentenName = medikamentenName;
        this.wirkstoffBezeichnung = wirkstoffBezeichnung;
        this.hersteller = hersteller;
        this.darreichungsform = darreichungsform;
        this.dosierung = dosierung;
        this.ablaufDatum = ablaufDatum;
    }

    public Medikament(UniqueIdentifier ui, int pzn, String serienNummer, String chargenNummer, String medikamentenName, String wirkstoffBezeichnung, YearMonth ablaufDatum) {
        if (ui == null) {
            throw new IllegalArgumentException("Pharmazeutische Kennung darf nicht leer sein");
        }
        this.ui = ui;
        this.serienNummer = serienNummer;
        this.chargenNummer = chargenNummer;
        this.pzn = pzn;
        this.medikamentenName = medikamentenName;
        this.wirkstoffBezeichnung = wirkstoffBezeichnung;
        this.ablaufDatum = ablaufDatum;
    }

    public UniqueIdentifier getUi() {
        return ui;
    }

    public String getSerienNummer() {
        return serienNummer;
    }

    public void setSerienNummer(String serienNummer) {
        this.serienNummer = serienNummer;
    }

    public int getPzn() {
        return pzn;
    }

    public void setPzn(int pzn) {
        this.pzn = pzn;
    }

    public String getChargenNummer() {
        return chargenNummer;
    }

    public void setChargenNummer(String chargenNummer) {
        this.chargenNummer = chargenNummer;
    }

    public String getMedikamentenName() {
        return medikamentenName;
    }

    public void setMedikamentenName(String medikamentenName) {
        this.medikamentenName = medikamentenName;
    }

    public String getWirkstoffBezeichnung() {
        return wirkstoffBezeichnung;
    }

    public void setWirkstoffBezeichnung(String wirkstoffBezeichnung) {
        this.wirkstoffBezeichnung = wirkstoffBezeichnung;
    }

    public String getHersteller() {
        return hersteller;
    }

    public void setHersteller(String hersteller) {
        this.hersteller = hersteller;
    }

    public Darreichungsform getDarreichungsform() {
        return darreichungsform;
    }

    public void setDarreichungsform(Darreichungsform darreichungsform) {
        this.darreichungsform = darreichungsform;
    }

    public Dosierung getDosierung() {
        return dosierung;
    }

    public void setDosierung(Dosierung dosierung) {
        this.dosierung = dosierung;
    }

    public YearMonth getAblaufDatum() {
        return ablaufDatum;
    }

    public void setAblaufDatum(YearMonth ablaufDatum) {
        this.ablaufDatum = ablaufDatum;
    }

    // Domänenlogik
    public boolean istAbgelaufen() {
        return YearMonth.now().isAfter(this.ablaufDatum);
    }

    public boolean laeuftInXWochenAb(int wochen) {
        LocalDate heute = LocalDate.now();
        LocalDate inXWochen = heute.plusWeeks(wochen);
        LocalDate ablaufDatum = LocalDateParser.parseJahrMonatZuVollemDatum(this.ablaufDatum);
        return !ablaufDatum.isBefore(heute) && ablaufDatum.isBefore(inXWochen);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ui.toString());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Medikament that = (Medikament) obj;
        return Objects.equals(ui.toString(), that.ui.toString());
    }

    @Override
    public String toString() {
        return String.format("%s, %s, %s, %s, %s, %s, %s, %s",
                    ui,
                    chargenNummer,
                    medikamentenName,
                    wirkstoffBezeichnung,
                    hersteller,
                    darreichungsform,
                    dosierung,
                    ablaufDatum.toString());
    }
}
