package org.example.lab1.usecases;

import lombok.Getter;
import lombok.Setter;
import org.example.lab1.entities.Grupe;
import org.example.lab1.entities.PasirenkamasisDalykas;
import org.example.lab1.entities.Studentas;
import org.example.lab1.persistence.PasirenkamasisDalykasDAO;
import org.example.lab1.persistence.StudentasDAO;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

@Model
public class PasirenkamiejiDalykai {
    @Inject
    private PasirenkamasisDalykasDAO pasirenkamasisDalykasDAO;

    @Getter @Setter
    private PasirenkamasisDalykas sukuriamasPasirenkamasisDalykas = new PasirenkamasisDalykas();
    @Inject
    private StudentasDAO studentasDAO;

    @Getter
    private List<PasirenkamasisDalykas> studentoPasirenkamiejiDalykai;

    @Getter
    private String studentoVardas;

    @Transactional
    public void createPasirenkamasisDalykas(){
        this.pasirenkamasisDalykasDAO.persist(sukuriamasPasirenkamasisDalykas);
    }

    @PostConstruct
    public void init(){
        String studentasIdParam = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("studentasId");
        if (studentasIdParam != null) {
            Integer studentasId = Integer.parseInt(studentasIdParam);
            Studentas s = studentasDAO.findOne(studentasId);
            if (s != null) {
                studentoVardas = s.getVardas();
                studentoPasirenkamiejiDalykai = s.getPasirenkamiejiDalykai();
            }
        }

    }
}
