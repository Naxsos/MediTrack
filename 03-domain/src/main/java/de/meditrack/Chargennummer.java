package de.meditrack;

import java.util.Objects;

public final class Chargennummer {
    private final String nummer;

    public Chargennummer(String nummer) {
        if (nummer == null || nummer.isEmpty() || nummer.length() >= 9) {
            throw new IllegalArgumentException("Ungültige Chargennummer: " + nummer);
        }
        this.nummer = nummer;
    }

    public String getNummer() { return nummer;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Chargennummer that = (Chargennummer) o;
        return Objects.equals(nummer, that.nummer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nummer);
    }

    @Override
    public String toString() {
        return nummer;
    }
}