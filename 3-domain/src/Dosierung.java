public class Dosierung {

    Masseinheit masseinheit;
    int wertMasseinheit;
    Intervall intervall;
    int wertIntervall;
    Zeitperiode zeitperiode;
    int Wertzeitperiode;

    public Dosierung(Masseinheit masseinheit, int wertMasseinheit, Intervall intervall, int wertIntervall, Zeitperiode zeitperiode, int wertzeitperiode) {
        this.masseinheit = masseinheit;
        this.wertMasseinheit = wertMasseinheit;
        this.intervall = intervall;
        this.wertIntervall = wertIntervall;
        this.zeitperiode = zeitperiode;
        Wertzeitperiode = wertzeitperiode;
    }
}
