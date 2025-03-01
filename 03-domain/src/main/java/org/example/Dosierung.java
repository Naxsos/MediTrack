package org.example;

import java.util.Objects;

public class Dosierung {

    private Masseinheit masseinheit;
    private int wertMasseinheit;
    private Intervall intervall;
    private int wertIntervall;
    private Zeitperiode zeitperiode;
    private int Wertzeitperiode;

    public Dosierung(Masseinheit masseinheit, int wertMasseinheit, Intervall intervall, int wertIntervall, Zeitperiode zeitperiode, int wertzeitperiode) {
        this.masseinheit = masseinheit;
        this.wertMasseinheit = wertMasseinheit;
        this.intervall = intervall;
        this.wertIntervall = wertIntervall;
        this.zeitperiode = zeitperiode;
        Wertzeitperiode = wertzeitperiode;
    }

    public Masseinheit getMasseinheit() {
        return masseinheit;
    }

    public int getWertMasseinheit() {
        return wertMasseinheit;
    }

    public Intervall getIntervall() {
        return intervall;
    }

    public int getWertIntervall() {
        return wertIntervall;
    }

    public Zeitperiode getZeitperiode() {
        return zeitperiode;
    }

    public int getWertzeitperiode() {
        return Wertzeitperiode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dosierung dosierung = (Dosierung) o;
        return wertMasseinheit == dosierung.wertMasseinheit && wertIntervall == dosierung.wertIntervall && Wertzeitperiode == dosierung.Wertzeitperiode && masseinheit == dosierung.masseinheit && intervall == dosierung.intervall && zeitperiode == dosierung.zeitperiode;
    }

    @Override
    public int hashCode() {
        return Objects.hash(masseinheit, wertMasseinheit, intervall, wertIntervall, zeitperiode, Wertzeitperiode);
    }
}
