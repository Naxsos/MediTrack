package de.meditrack;

public enum Darreichungsform {

    TABLETTE("Tablette"),
    KAPSEL("Kapsel"),
    PULVER("Pulver"),
    SALBE("Salbe"),
    CREME("Creme"),
    GEL("Gel"),
    INJEKTION("Injection"),
    LOESUNG("Lösung"),
    INFUSION("Infusion"),
    INHALATION("Inhalation"),
    PFLASTER("Pflaster"),
    TROPFEN("Tropfen"),
    UNDEFINIERT("-");

    final String text;

    Darreichungsform(String text){
        this.text = text;
    }
}
