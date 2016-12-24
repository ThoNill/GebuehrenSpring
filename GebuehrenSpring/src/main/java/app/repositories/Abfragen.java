package app.repositories;

import java.util.List;

import javax.money.MonetaryAmount;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import app.entities.Abrechnung;
import app.entities.KontoBewegung;

public interface Abfragen extends JpaRepository<Abrechnung, Long> {

    @Query("select bew from app.entities.KontoBewegung bew where bew.buchung.abrechnung = :abr ")
    public List<KontoBewegung> getBewegungen(
            @Param("abr") app.entities.Abrechnung abr);

    @Query("select bew.konto, sum(bew.betrag) from app.entities.KontoBewegung bew where bew.buchung.abrechnung = :abr group by bew.konto")
    public List<?> getSumBewegungen(@Param("abr") app.entities.Abrechnung abr);

    @Query("select bew.konto, sum(bew.betrag) from app.entities.KontoBewegung bew where bew.art = 1 and bew.buchung.abrechnung = :abr and bew.buchung.art = :art group by bew.konto")
    public List<?> getSumBewegungen(@Param("abr") app.entities.Abrechnung abr,
            @Param("art") int art);

    @Query("select sum(bew.betrag) from app.entities.KontoBewegung bew where bew.art = 1 and bew.buchung.abrechnung = :abr ")
    public MonetaryAmount getSaldo(@Param("abr") app.entities.Abrechnung abr);

}
