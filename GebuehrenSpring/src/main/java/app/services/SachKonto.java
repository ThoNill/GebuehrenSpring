package app.services;

import javax.money.MonetaryAmount;

import buchung.Bewegungen;
import buchung.Konto;

public enum SachKonto implements Konto {
    
    GEB�HR(10,"Geb�hr"),MWST(20,"Mwst"),GEHALT(30,"Gehalt");
    
    
    private int nummer;
    private String name;

    private SachKonto(int nummer, String name) {
        this.nummer = nummer;
        this.name = name;
    }

    @Override
    public int getNummer() {
        return nummer;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Bewegungen erg�nzen(MonetaryAmount amount) {
        Bewegungen w = new Bewegungen();
        w.put(this, amount);
        return w;
    }

    
    @Override
    public String toString() {
        return "TestKonto [nummer=" + nummer + ", name=" + name + "]";
    }

    @Override
    public boolean hasErg�nzung() {
        return false;
    }

}
