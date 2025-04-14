package org.example.lab1.persistence;

import org.example.lab1.entities.Grupe;
import org.example.lab1.entities.Studentas;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

@ApplicationScoped
public class GrupeDAO {
    @Inject
    private EntityManager em;

    public void persist(Grupe grupe){
        this.em.persist(grupe);
    }

    public Grupe findOne(Integer id){
        return em.find(Grupe.class, id);
    }

    public List<Grupe> loadAll() {
        return em.createNamedQuery("Grupe.findAll", Grupe.class).getResultList();
    }
}
