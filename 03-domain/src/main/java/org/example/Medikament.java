package org.example;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Objects;

public class Medikament {

    private final UniqueIdentifier ui;
    private final Seriennummer serienNummer;
    private final PZN pzn;
    private final Chargennummer chargenNummer;
    private final String medikamentenName;
    private final String wirkstoffBezeichnung;
    private final String hersteller;
    private final Darreichungsform darreichungsform;
    private final Dosierung dosierung;
    private final YearMonth ablaufDatum;

    private Medikament(Builder builder) {
        this.ui = builder.ui;
        this.serienNummer = builder.serienNummer;
        this.pzn = builder.pzn;
        this.chargenNummer = builder.chargenNummer;
        this.medikamentenName = builder.medikamentenName;
        this.wirkstoffBezeichnung = builder.wirkstoffBezeichnung;
        this.hersteller = builder.hersteller;
        this.darreichungsform = builder.darreichungsform;
        this.dosierung = builder.dosierung;
        this.ablaufDatum = builder.ablaufDatum;
    }

    public UniqueIdentifier getUi() {
        return ui;
    }
    public Seriennummer getSerienNummer() {
        return serienNummer;
    }
    public PZN getPzn() {
        return pzn;
    }
    public Chargennummer getChargenNummer() {
        return chargenNummer;
    }
    public String getMedikamentenName() {
        return medikamentenName;
    }
    public String getWirkstoffBezeichnung() {
        return wirkstoffBezeichnung;
    }
    public String getHersteller() {
        return hersteller;
    }
    public Darreichungsform getDarreichungsform() {
        return darreichungsform;
    }
    public Dosierung getDosierung() {
        return dosierung;
    }
    public YearMonth getAblaufDatum() {return ablaufDatum;}

    public boolean istAbgelaufen() { return YearMonth.now().isAfter(this.ablaufDatum); }

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

    public static class Builder {
        private final UniqueIdentifier ui;
        private Seriennummer serienNummer;
        private PZN pzn;
        private Chargennummer chargenNummer;
        private String medikamentenName;
        private String wirkstoffBezeichnung;
        private String hersteller;
        private Darreichungsform darreichungsform;
        private Dosierung dosierung;
        private YearMonth ablaufDatum;

        public Builder(UniqueIdentifier ui) {
            if (ui == null) {
                throw new IllegalArgumentException("Pharmazeutische Kennung darf nicht null sein");
            }
            this.ui = ui;
        }

        public Builder serienNummer(Seriennummer serienNummer) {
            this.serienNummer = serienNummer;
            return this;
        }

        public Builder pzn(PZN pzn) {
            this.pzn = pzn;
            return this;
        }

        public Builder chargenNummer(Chargennummer chargenNummer) {
            this.chargenNummer = chargenNummer;
            return this;
        }

        public Builder medikamentenName(String medikamentenName) {
            this.medikamentenName = medikamentenName;
            return this;
        }

        public Builder wirkstoffBezeichnung(String wirkstoffBezeichnung) {
            this.wirkstoffBezeichnung = wirkstoffBezeichnung;
            return this;
        }

        public Builder hersteller(String hersteller) {
            this.hersteller = hersteller;
            return this;
        }

        public Builder darreichungsform(Darreichungsform darreichungsform) {
            this.darreichungsform = darreichungsform;
            return this;
        }

        public Builder dosierung(Dosierung dosierung) {
            this.dosierung = dosierung;
            return this;
        }

        public Builder ablaufDatum(YearMonth ablaufDatum) {
            this.ablaufDatum = ablaufDatum;
            return this;
        }

        public Medikament build() {
            if (serienNummer == null) {
                throw new IllegalStateException("Seriennummer darf nicht null sein!");
            }
            if (pzn == null) {
                throw new IllegalStateException("PZN darf nicht null sein!");
            }
            if (chargenNummer == null) {
                throw new IllegalStateException("Chargennummer darf nicht null sein!");
            }
            if (ablaufDatum == null) {
                throw new IllegalStateException("Ablaufdatum muss gesetzt sein!");
            }
            return new Medikament(this);
        }
    }
}
