package app.entities;

import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "ABRECHNUNG")
@SequenceGenerator(name = "ABRECHNUNG_SEQ", sequenceName = "ABRECHNUNG_SEQ")
public class Abrechnung extends AbrechnungUser {

    public Abrechnung() {
        super();
    }

    @Override
    @Basic
    @Column(name = "ABRECHNUNGID")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ABRECHNUNG_SEQ")
    public java.lang.Long getAbrechnungId() {
        return super.getAbrechnungId();
    };

    @Override
    @Basic
    @Column(name = "MONAT")
    public int getMonat() {
        return super.getMonat();
    };

    @Override
    @Basic
    @Column(name = "JAHR")
    public int getJahr() {
        return super.getJahr();
    };

    @Override
    @ManyToOne
    @JoinColumn(name = "MandantId")
    public Mandant getMandant() {
        return super.getMandant();
    };

    @Override
    @OneToMany(mappedBy = "abrechnung")
    public List<Buchung> getBuchung() {
        return super.getBuchung();
    };

}
