package org.example.lab1.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@NamedQueries({
        @NamedQuery(name = "PasirenkamasisDalykas.findAll", query = "select d from PasirenkamasisDalykas as d")
})
@Table(name = "PASIRENKAMASIS_DALYKAS")
@Getter @Setter
public class PasirenkamasisDalykas {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "PASIRENKAMASIS_DALYKAS_ID")
    private Integer id;

    @Column(name = "PAVADINIMAS")
    private String pavadinimas;

    @ManyToMany
    @JoinTable(
            name = "STUDENTAS_DALYKAS",
            joinColumns = @JoinColumn(name = "PASIRENKAMASIS_DALYKAS_ID"),
            inverseJoinColumns = @JoinColumn(name = "STUDENTAS_ID")
    )
    private List<Studentas> studentai;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PasirenkamasisDalykas pasirenkamasisDalykas = (PasirenkamasisDalykas) o;
        return Objects.equals(pavadinimas, pasirenkamasisDalykas.pavadinimas);
    }
    @Override
    public int hashCode() {
        return Objects.hash(pavadinimas);
    }
}
