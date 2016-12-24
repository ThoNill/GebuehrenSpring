package app.entities;

import java.util.ArrayList;
import java.util.List;

public class AbrechnungBasis {

    public AbrechnungBasis() {
        super();
    }

    private java.lang.Long AbrechnungId;
    private int monat;
    private int Jahr;
    private Mandant mandant;
    private List<Buchung> buchung = new ArrayList<>();

    public java.lang.Long getAbrechnungId() {
        return this.AbrechnungId;
    };

    public int getMonat() {
        return this.monat;
    };

    public int getJahr() {
        return this.Jahr;
    };

    public Mandant getMandant() {
        return this.mandant;
    };

    public List<Buchung> getBuchung() {
        return this.buchung;
    };

    public void setAbrechnungId(java.lang.Long AbrechnungId) {
        this.AbrechnungId = AbrechnungId;
    };

    public void setMonat(int monat) {
        this.monat = monat;
    };

    public void setJahr(int Jahr) {
        this.Jahr = Jahr;
    };

    public void setMandant(Mandant mandant) {
        this.mandant = mandant;
    };

    public void setBuchung(List<Buchung> buchung) {
        this.buchung = buchung;
    };

    public void addBuchung(Buchung buchung) {
        this.buchung.add(buchung);
    };

    public void removeBuchung(Buchung buchung) {
        this.buchung.remove(buchung);
    };

    @Override
    public String toString() {
        return "AbrechnungBasis " + " AbrechnungId =" + this.getAbrechnungId()
                + " Monat =" + this.getMonat() + " Jahr =" + this.getJahr()
                + " Mandant =" + this.getMandant()

        ;
    }

}
