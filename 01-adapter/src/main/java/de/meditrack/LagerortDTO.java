package de.meditrack;

public class LagerortDTO {
    public String bezeichnung;
    public String stockwerk;
    public String raumnummer;

    public LagerortDTO(String bezeichnung, String stockwerk, String raumnummer) {
        this.bezeichnung = bezeichnung;
        this.stockwerk = stockwerk;
        this.raumnummer = raumnummer;
    }
} 