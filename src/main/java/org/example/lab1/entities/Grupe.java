package org.example.lab1.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(name = "Grupe.findAll", query = "select g from Grupe as g")
})
@Table(name = "GRUPE")
@Getter @Setter
public class Grupe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "GRUPE_ID")
    private Integer id;

    @Column(unique = true, name = "PAVADINIMAS")
    private String pavadinimas;

    @OneToMany(mappedBy = "grupe")
    @Column(name = "STUDENTAI")
    private List<Studentas> studentai = new ArrayList<>();

    public Grupe() {
    }
}
