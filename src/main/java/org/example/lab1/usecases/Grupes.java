package org.example.lab1.usecases;

import lombok.Getter;
import lombok.Setter;
import org.example.lab1.entities.Grupe;
import org.example.lab1.entities.Studentas;
import org.example.lab1.interceptors.LoggedInvocation;
import org.example.lab1.persistence.GrupeDAO;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

@Model
public class Grupes {

    @Getter @Setter
    private Grupe naujaGrupe = new Grupe();

    @Getter
    private List<Grupe> visosGrupes;

    @Getter
    private List<Studentas> grupesStudentai;

    @Inject
    private GrupeDAO grupeDAO;

    @Transactional
    @LoggedInvocation
    public void createGrupe(){
        this.grupeDAO.persist(naujaGrupe);
        loadAllGrupes();
    }

    @PostConstruct
    public void init() {

        loadAllGrupes();

        String grupeIdParam = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("grupeId");
        if (grupeIdParam != null) {
            Integer grupeId = Integer.parseInt(grupeIdParam);
            Grupe g = grupeDAO.findOne(grupeId);
            if (g != null) {
                grupesStudentai = g.getStudentai();
            }
        }
    }

    public void loadAllGrupes() {
        this.visosGrupes = grupeDAO.loadAll();
    }
}
