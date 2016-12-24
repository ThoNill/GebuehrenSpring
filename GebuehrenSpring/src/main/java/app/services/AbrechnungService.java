package app.services;

import java.util.List;

import javax.money.MonetaryAmount;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.entities.Abrechnung;
import app.entities.Buchung;
import app.entities.KontoBewegung;
import app.repositories.Abfragen;
import app.repositories.BuchungRepository;
import buchung.Bewegungen;
import buchung.BuchungsAuftrag;
import buchung.Konto;

@Service
public class AbrechnungService {

    private BuchungRepository repository;

    private Abfragen abfragen;

    public AbrechnungService(@Autowired BuchungRepository repository,
            @Autowired Abfragen abfragen) {
        this.repository = repository;
        this.abfragen = abfragen;
    }

    @Transactional
    public void insertBuchung(Abrechnung abrechnung, BuchungsAuftrag auftrag) {
        Buchung buch = generateBuchung(auftrag);
        buch.setAbrechnung(abrechnung);
        MonetaryAmount summe = auftrag.getWerte().summe().negate();
        if (!summe.isZero()) {
            KontoBewegung bew = new KontoBewegung();
            bew.setArt(2);
            bew.setKonto(abrechnung.getMandant().getMandantId().intValue());
            bew.setBetrag(summe);
            buch.getBewegungen().add(bew);
            bew.setBuchung(buch);
        }
        repository.save(buch);

    }

    protected Buchung generateBuchung(BuchungsAuftrag auftrag) {
        Buchung buch = new Buchung();
        buch.setText(auftrag.getBuchungsText());
        buch.setArt(auftrag.getArt().ordinal());

        Bewegungen bewegungen = auftrag.getWerte();
        for (Konto konto : bewegungen.keySet()) {
            MonetaryAmount betrag = bewegungen.get(konto);

            KontoBewegung bew = new KontoBewegung();
            bew.setArt(1);
            bew.setKonto(konto.getNummer());
            bew.setBetrag(betrag);
            buch.getBewegungen().add(bew);
            bew.setBuchung(buch);
        }

        return buch;
    }

    public Bewegungen getAktuelleWerte(Abrechnung abrechnung, Enum<?> art) {
        Bewegungen bewegungen = new Bewegungen();
        List<?> resultList = abfragen.getSumBewegungen(abrechnung,
                art.ordinal());
        for (Object ar : resultList) {
            Integer kontonr = (Integer) ((Object[]) ar)[0];
            MonetaryAmount betrag = (MonetaryAmount) ((Object[]) ar)[1];
            Konto konto = sucheKonto(kontonr);
            bewegungen.add(konto, betrag);
        }
        return bewegungen;
    }

    private Konto sucheKonto(int kontonr) {
        for(SachKonto k : SachKonto.values()) {
            if (k.getNummer() == kontonr) {
                return k;
            }
        }
        throw new IllegalArgumentException("Kein Sachkonto für " + kontonr + " gefunden");
    }
}
