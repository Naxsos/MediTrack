package org.example;

import java.util.Objects;

public final class PZN {
    private final int nummer;

    public PZN(int nummer) {
        if (nummer < 1000000 || nummer > 99999999) {
            throw new IllegalArgumentException("Ungültige PZN: " + nummer);
        }
        this.nummer = nummer;
    }

    public int getNummer() {
        return nummer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PZN pzn = (PZN) o;
        return nummer == pzn.nummer;
    }

    @Override
    public int hashCode() {
        return Objects.hash(nummer);
    }

    @Override
    public String toString() {
        return String.valueOf(nummer);
    }
}