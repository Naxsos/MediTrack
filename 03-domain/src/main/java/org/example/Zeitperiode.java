package org.example;

public enum Zeitperiode {
    TAGE("Tage"),
    WOCHEN("Wochen"),
    MONATE("Monat"),
    EINMALIG("Einmalig");

    String beschreibung;

    Zeitperiode(String beschreibung) {
        this.beschreibung = beschreibung;
    }
}
