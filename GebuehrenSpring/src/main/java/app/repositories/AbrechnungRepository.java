package app.repositories;

import org.springframework.data.repository.CrudRepository;

import app.entities.Abrechnung;

public interface AbrechnungRepository extends CrudRepository<Abrechnung, Long> {

}
