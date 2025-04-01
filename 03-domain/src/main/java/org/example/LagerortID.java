package org.example;

public class LagerortID {
    private final String id;

    private LagerortID(String id) {
        this.id = id;
    }

    public static LagerortID fromString(String id) {
        if (id == null || !isValidFormat(id)) {
            throw new IllegalArgumentException("Falsches Format für LagerortID. Erwartetes Format: stockwerk-raumnummer");
        }
        return new LagerortID(id);
    }

    private static boolean isValidFormat(String id) {
        return id.matches("\\d+-[A-Za-z0-9]+");
    }

    @Override
    public String toString() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LagerortID lagerortID = (LagerortID) o;
        return id.equals(lagerortID.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
} 