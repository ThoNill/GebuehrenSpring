package app.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import app.entities.Buchung;

public interface BuchungRepository extends CrudRepository<Buchung, Long> {

    List<Buchung> findByText(String lastName);
}
