package app;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import app.entities.Abrechnung;
import app.entities.Buchung;
import app.entities.KontoBewegung;
import app.repositories.Abfragen;
import app.repositories.AbrechnungRepository;
import app.repositories.BuchungRepository;
import betrag.Geld;

@SpringBootApplication
public class Application {

    private static final Logger log = LoggerFactory
            .getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

    @Bean
    public CommandLineRunner demo(BuchungRepository repository,
            AbrechnungRepository abrechnungen, Abfragen ar) {
        return (args) -> {
            Abrechnung abr = new Abrechnung();

            Buchung buch1 = generateBuchung("Testbuchung", 200);
            buch1.setAbrechnung(abr);
            abr.addBuchung(buch1);

            Buchung buch2 = generateBuchung("weitere Buchung", 300);
            buch2.setAbrechnung(abr);
            abr.addBuchung(buch2);

            Buchung buch3 = generateBuchung("weitere Buchung", 500);

            buch3.setAbrechnung(abr);
            abr.addBuchung(buch3);

            abrechnungen.save(abr);
            repository.save(buch1);
            repository.save(buch2);
            repository.save(buch3);

            long id = 0L;
            log.info("Buchungen found with findAll():");
            log.info("-------------------------------");
            for (Buchung buchung : repository.findAll()) {
                log.info(buchung.toString());
                id = buchung.getBuchungId();
                /*
                 * for (KontoBewegung bewegung : buchung.getBewegungen()) {
                 * log.info(bewegung.toString()); }
                 */
            }
            log.info("");

            {
                Buchung buchung = repository.findOne(id);
                if (buchung != null) {
                    log.info("Buchung found with findOne(" + id + "):");
                    log.info("--------------------------------");
                    log.info(buchung.toString());
                    log.info("");
                }
            }

            log.info("Buchung found with findByText('Testbuchung'):");
            log.info("--------------------------------------------");
            for (Buchung buchung : repository.findByText("Testbuchung")) {
                log.info(buchung.toString());
            }
            log.info("");

            List<KontoBewegung> sList = ar.getBewegungen(abr);

            for (KontoBewegung b : sList) {
                log.info(b.toString());

            }

            Object res = ar.getSaldo(abr);
            log.info("Object: " + res);

            List<?> resList = ar.getSumBewegungen(abr);
            log.info("Liste: " + resList);
            for (Object ob : resList) {
                log.info("ListeObj: " + ob + " "
                        + ob.getClass().getCanonicalName());
                log.info("0: " + ((Object[]) ob)[0]);
                log.info("1: " + ((Object[]) ob)[1]);
            }

        };
    }

    protected Buchung generateBuchung(String text, long betrag) {
        Buchung buch = new Buchung();
        buch.setText(text);
        buch.setArt(42);

        KontoBewegung bew1 = new KontoBewegung();
        bew1.setArt(1);
        bew1.setKonto(11);
        bew1.setBetrag(Geld.createAmount(betrag));

        buch.getBewegungen().add(bew1);
        bew1.setBuchung(buch);

        KontoBewegung bew2 = new KontoBewegung();
        bew2.setArt(2);
        bew1.setKonto(13);
        bew2.setBetrag(Geld.createAmount(-betrag));

        buch.getBewegungen().add(bew2);
        bew2.setBuchung(buch);
        return buch;
    }

}
