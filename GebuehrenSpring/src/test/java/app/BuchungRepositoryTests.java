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

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import app.entities.Buchung;
import app.entities.KontoBewegung;
import app.repositories.BuchungRepository;
import betrag.Geld;

@RunWith(SpringRunner.class)
@DataJpaTest
@EnableTransactionManagement
public class BuchungRepositoryTests {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private BuchungRepository buchungen;

    @Test
    public void testFindByLastName() {
        Buchung buch = new Buchung();
        buch.setText("Testbuchung");
        buch.setArt(42);

        KontoBewegung bew1 = new KontoBewegung();
        bew1.setArt(2);
        bew1.setBetrag(Geld.createAmount(2.20));

        buch.getBewegungen().add(bew1);

        KontoBewegung bew2 = new KontoBewegung();
        bew2.setArt(2);
        bew2.setBetrag(Geld.createAmount(-2.20));

        buch.getBewegungen().add(bew2);
        entityManager.persist(buch);

        List<Buchung> findByText = buchungen.findByText(buch.getText());

        assertThat(findByText).extracting(Buchung::getText).containsOnly(
                buch.getText());
    }
}