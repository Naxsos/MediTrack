package org.example;

public enum Zeitperiode {
    TAGE("Tage"),
    WOCHEN("Wochen"),
    MONATE("Monat"),
    EINMALIG("Einmalig"),
    UNBEKANNT("-");

    String beschreibung;

    Zeitperiode(String beschreibung) {
        this.beschreibung = beschreibung;
    }
}
