package de.meditrack;

public class Lagerort {
    private final LagerortID lagerortId;
    private String bezeichnung;
    private int stockwerk;
    private String raumnummer; // Raumnummern können auch Buchstaben enthalten


    public Lagerort(String bezeichnung, int stockwerk, String raumnummer) {
        if (raumnummer == null || raumnummer.trim().isEmpty()) {
            throw new IllegalArgumentException("Raumnummer darf nicht leer sein");
        }
        this.lagerortId = LagerortID.fromString(Integer.toString(stockwerk) + "-" + raumnummer); // Stockwerk und Ruamnummer sind ein natürlicher Schlüssel
        this.bezeichnung = bezeichnung;
        this.stockwerk = stockwerk;
        this.raumnummer = raumnummer;
    }

    public LagerortID getLagerortId() {
        return lagerortId;
    }

    public String getBezeichnung() {
        return bezeichnung;
    }

    public void setBezeichnung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }
    
    public int getStockwerk() {
        return stockwerk;
    }
    
    public String getRaumnummer() {
        return raumnummer;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lagerort lagerort = (Lagerort) o;
        // Gleichheit basiert nur auf der ID, nicht auf den Attributen
        return lagerortId.equals(lagerort.lagerortId);
    }

    @Override
    public int hashCode() {
        // Hash basiert nur auf der ID, nicht auf den Attributen
        return lagerortId.hashCode();
    }

    @Override
    public String toString() {
        return String.format("ID=%s, %s, Stockwerk %d, Raum %s",
                lagerortId, bezeichnung, stockwerk, raumnummer);
    }
} 