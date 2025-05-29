package de.meditrack;

public class MedikamentDTO {
    public String pzn;
    public String seriennummer;
    public String chargennummer;
    public String name;
    public String wirkstoff;
    public String ablaufdatum;
    public String lagerort;

    public MedikamentDTO(String pzn, String seriennummer, String chargennummer, String name, String wirkstoff, String ablaufdatum, String lagerort) {
        this.pzn = pzn;
        this.seriennummer = seriennummer;
        this.chargennummer = chargennummer;
        this.name = name;
        this.wirkstoff = wirkstoff;
        this.ablaufdatum = ablaufdatum;
        this.lagerort = lagerort;
    }
}