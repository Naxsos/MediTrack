public enum Intervall {

    PRO_TAG("Pro Tag"),
    PRO_WOCHE("pro Woche"),
    PRO_MONAT("Pro Monat"),
    NACH_BEDARF("Nach Bedarf");

    final String beschreibung;

    Intervall(String beschreibung){
        this.beschreibung = beschreibung;
    }
}
