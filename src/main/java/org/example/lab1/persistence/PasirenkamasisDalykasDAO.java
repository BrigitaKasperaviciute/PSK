package org.example.lab1.persistence;

import org.example.lab1.entities.Grupe;
import org.example.lab1.entities.PasirenkamasisDalykas;
import org.example.lab1.entities.Studentas;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

@ApplicationScoped
public class PasirenkamasisDalykasDAO {
    @Inject
    private EntityManager em;

    public void persist(PasirenkamasisDalykas pasirenkamasisDalykas){
        this.em.persist(pasirenkamasisDalykas);
    }

    public PasirenkamasisDalykas findOne(Integer id){
        return em.find(PasirenkamasisDalykas.class, id);
    }

    public List<PasirenkamasisDalykas> loadAll() {
        return em.createNamedQuery("PasirenkamasisDalykas.findAll", PasirenkamasisDalykas.class).getResultList();
    }

    public List<PasirenkamasisDalykas> getCourseByStudentId(Integer studentasId) {
        return em.createQuery(
                        "SELECT p FROM PasirenkamasisDalykas p JOIN p.studentai s WHERE s.id = :studentasId",
                        PasirenkamasisDalykas.class)
                .setParameter("studentasId", studentasId)
                .getResultList();
    }

    public List<Studentas> getStudentsByCourseId(Integer dalykasId) {
        String jpql = "SELECT s FROM Studentas s JOIN s.pasirenkamiejiDalykai p WHERE p.id = :dalykasId";
        return em.createQuery(jpql, Studentas.class)
                .setParameter("dalykasId", dalykasId)
                .getResultList();
    }


}
