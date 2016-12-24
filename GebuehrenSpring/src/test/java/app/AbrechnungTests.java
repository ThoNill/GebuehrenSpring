/*
 * Copyright 2002-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package app;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import app.entities.Abrechnung;
import app.entities.Buchung;
import app.entities.Mandant;
import app.repositories.Abfragen;
import app.repositories.AbrechnungRepository;
import app.repositories.BuchungRepository;
import app.repositories.MandantRepository;
import app.services.SachKonto;
import betrag.Geld;
import buchung.Bewegungen;
import buchung.BuchungsAuftrag;

@RunWith(SpringRunner.class)
@ComponentScan("app")
@DataJpaTest
public class AbrechnungTests {

    enum Buchungsyarten {
        FIRST, A, B
    }

    @Autowired
    private AbrechnungRepository abrechnungRepo;

    @Autowired
    private BuchungRepository buchungen;

    @Autowired
    private MandantRepository mandanten;

    @Autowired
    Abfragen abfragen;

    @Test
    public void testFindByLastName() {

        Mandant mandant = new Mandant();
        mandant.setName("TestMandant");
        mandant = mandanten.save(mandant);

        Abrechnung abrechnung = new Abrechnung();
        abrechnung.setMonat(11);
        abrechnung.setJahr(2016);
        abrechnung.setMandant(mandant);

        mandant.addAbrechnung(abrechnung);

        mandant = mandanten.save(mandant);
        abrechnung = abrechnungRepo.save(abrechnung);

        Bewegungen bewegungen = new Bewegungen();
        bewegungen.add(SachKonto.GEBÜHR, Geld.createAmount(1.12));
        bewegungen.add(SachKonto.MWST, Geld.createAmount(21.12));
        BuchungsAuftrag auftrag = new BuchungsAuftrag(Buchungsyarten.A,
                "Buchngsart A", bewegungen);

        abrechnung.insertBuchung(auftrag);

        bewegungen = new Bewegungen();
        bewegungen.add(SachKonto.GEBÜHR, Geld.createAmount(10.12));
        bewegungen.add(SachKonto.MWST, Geld.createAmount(22.12));
        auftrag = new BuchungsAuftrag(Buchungsyarten.A, "Buchngsart A",
                bewegungen);

        abrechnung.insertBuchung(auftrag);

        Iterable<Buchung> alleBuchungen = buchungen.findAll();
        for (Buchung b : alleBuchungen) {
            System.out.println(b);
        }

        Bewegungen summen = abrechnung.getAktuelleWerte(Buchungsyarten.A);

        assertEquals(Geld.createAmount(1.12 + 10.12),
                summen.get(SachKonto.GEBÜHR));
    }
}