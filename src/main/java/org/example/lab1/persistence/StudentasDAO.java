package org.example.lab1.persistence;

import org.example.lab1.entities.Grupe;
import org.example.lab1.entities.PasirenkamasisDalykas;
import org.example.lab1.entities.Studentas;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class StudentasDAO {

    @Inject
    private EntityManager em;

    public void persist(Studentas studentas){
        this.em.persist(studentas);
    }

    public Studentas findOne(Integer id){
        return em.find(Studentas.class, id);
    }

    public Studentas update(Studentas studentas){
        return em.merge(studentas);
    }

    public List<Studentas> loadAll() {
        return em.createNamedQuery("Studentas.findAll", Studentas.class).getResultList();
    }

    public void addPasirenkamasisDalykas(Integer studentasId, Integer dalykasId) {
        if (studentasId == null || dalykasId == null) {
            throw new IllegalArgumentException("Both studentasId and dalykasId must not be null");
        }
        Studentas studentas = em.find(Studentas.class, studentasId);
        PasirenkamasisDalykas dalykas = em.find(PasirenkamasisDalykas.class, dalykasId);

        if (studentas != null && dalykas != null) {
            studentas.getPasirenkamiejiDalykai().add(dalykas);

            if (dalykas.getStudentai() == null) {
                dalykas.setStudentai(new ArrayList<>());
            }
            dalykas.getStudentai().add(studentas);

            em.merge(studentas);
            em.merge(dalykas);
        }
    }
}
