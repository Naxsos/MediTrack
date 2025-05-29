package de.meditrack;

public enum Masseinheit {

    MIKROGRAMM("µg", "Sehr geringe Mengen, z. B. für Hormone"),
    MILLIGRAMM("mg", "Übliche Einheit für Tabletten oder Kapseln"),
    GRAMM("g", "Größere Mengen, z. B. bei Infusionen"),
    MILLILITER("ml", "Häufig für Flüssigkeiten wie Sirup"),
    LITER("l", "Für größere Volumina, z. B. Infusionen"),
    STUECK("Stück", "Feste Darreichungsformen wie Tabletten"),
    EINHEITEN("I.E.", "Wird zum beispiel für Haparinspritzen genutzt"),
    UNBEKANNT("-", "-");


    private String kurzform;
    private String beschreibung;

    Masseinheit(String kurzform, String beschreibung){
        this.kurzform = kurzform;
        this.beschreibung = beschreibung;
    }
}
