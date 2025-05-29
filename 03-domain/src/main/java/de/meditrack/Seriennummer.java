package de.meditrack;

import java.util.Objects;

public final class Seriennummer {

    private final String nummer;

    public Seriennummer(String nummer) {
        if (nummer == null || nummer.length() >= 18) {
            throw new IllegalArgumentException("Ungültige Seriennummer: " + nummer);
        }
        this.nummer = nummer;
    }

    public String getNummer() { return nummer; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Seriennummer that = (Seriennummer) o;
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