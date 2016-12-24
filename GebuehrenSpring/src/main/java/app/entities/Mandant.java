package app.entities;

import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "MANDANT")
@SequenceGenerator(name = "MANDANT_SEQ", sequenceName = "MANDANT_SEQ")
public class Mandant extends MandantUser {

    public Mandant() {
        super();
    }

    @Override
    @Basic
    @Column(name = "MANDANTID")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MANDANT_SEQ")
    public java.lang.Long getMandantId() {
        return super.getMandantId();
    };

    @Override
    @Basic
    @Column(name = "NAME")
    public String getName() {
        return super.getName();
    };

    @Override
    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "mandant")
    public List<Abrechnung> getAbrechnung() {
        return super.getAbrechnung();
    };

}
