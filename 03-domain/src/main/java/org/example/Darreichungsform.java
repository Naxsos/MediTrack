package org.example;

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
    TROPFEN("Tropfen");

    final String text;

    Darreichungsform(String text){
        this.text = text;
    }
}
