package org.example.lab1.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@NamedQueries({
        @NamedQuery(name = "Studentas.findAll", query = "select s from Studentas as s")
})
@Table(name = "STUDENTAS")
@Getter @Setter
public class Studentas {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "STUDENTAS_ID")
    private Integer id;

    @Size(max = 50)
    @Column(name = "VARDAS")
    private String vardas;

    @ManyToOne()
    @JoinColumn(name="GRUPE_ID")
    private Grupe grupe;

    @ManyToMany(mappedBy = "studentai")
    private List<PasirenkamasisDalykas> pasirenkamiejiDalykai = new ArrayList<>();

    @Version
    @Column(name = "OPT_LOCK_VERSION")
    private Integer version;

    public Studentas() {
    }
}
