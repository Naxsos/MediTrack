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
    private final LagerortID lagerortId;

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
        this.lagerortId = builder.lagerortId;
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
    public LagerortID getLagerortId() {return lagerortId;}
    

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
        return String.format("%s, %s, %s, %s, %s, %s, %s, %s, %s",
                    ui,
                    chargenNummer,
                    medikamentenName,
                    wirkstoffBezeichnung,
                    hersteller,
                    darreichungsform,
                    dosierung,
                    ablaufDatum.toString(),
                    lagerortId.toString());
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
        private LagerortID lagerortId;

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
            if (dosierung == null) {
                throw new IllegalArgumentException("Dosierung darf nicht null sein!");
            }
            this.dosierung = dosierung;
            return this;
        }

        public Builder ablaufDatum(YearMonth ablaufDatum) {
            this.ablaufDatum = ablaufDatum;
            return this;
        }

        public Builder lagerortId(LagerortID lagerortId) {
            this.lagerortId = lagerortId;
            return this;
        }

        public Medikament build() {
            if (serienNummer == null) {
                throw new IllegalStateException("Seriennummer muss gesetzt sein!");
            }
            if (pzn == null) {
                throw new IllegalStateException("PZN muss gesetzt sein!");
            }
            if (chargenNummer == null) {
                throw new IllegalStateException("Chargennummer muss gesetzt sein!");
            }
            if (medikamentenName == null || medikamentenName.trim().isEmpty()) {
                throw new IllegalStateException("Medikamentename muss gesetzt sein!");
            }
            if (wirkstoffBezeichnung == null || wirkstoffBezeichnung.trim().isEmpty()) {
                throw new IllegalStateException("Wirkstoffbezeichnung muss gesetzt sein!");
            }
            if (dosierung == null ) {
                Dosierung d = new Dosierung(Masseinheit.UNBEKANNT, 0, Intervall.UNBEKANNT, 1, Zeitperiode.UNBEKANNT, 0);
                this.dosierung = d;
            }
            if (darreichungsform == null) {
                this.darreichungsform = Darreichungsform.UNDEFINIERT;
            }
            if (hersteller == null || hersteller.trim().isEmpty()) {
                this.hersteller = "Unbekannt";
            }
            if (ablaufDatum == null) {
                throw new IllegalStateException("Ablaufdatum muss gesetzt sein!");
            }
            if (lagerortId == null || lagerortId == null) {
                throw new IllegalStateException("Lagerort muss angegeben sein!");
            }
            return new Medikament(this);
        }
    }
}
