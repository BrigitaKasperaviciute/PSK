package org.example.lab1.usecases;

import lombok.Getter;
import lombok.Setter;
import org.example.lab1.entities.Grupe;
import org.example.lab1.entities.PasirenkamasisDalykas;
import org.example.lab1.entities.Studentas;
import org.example.lab1.persistence.GrupeDAO;
import org.example.lab1.persistence.PasirenkamasisDalykasDAO;
import org.example.lab1.persistence.StudentasDAO;
import org.example.lab1.rest.contracts.StudentasDTO;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Model
public class Studentai {
    @Inject
    private StudentasDAO studentasDAO;
    @Inject
    private GrupeDAO grupeDAO;
    @Inject
    private PasirenkamasisDalykasDAO pasirenkamasisDalykasDAO;

    @Getter @Setter
    private StudentasDTO sukuriamasStudentasDTO = new StudentasDTO();
    @Getter @Setter
    private Integer pridedamasPasirenkamasisDalykas;

    @Getter
    private List<Studentas> visiStudentai;
    @Getter
    private List<PasirenkamasisDalykas> laisviPasirenkamiejiDalykai;

    @PostConstruct
    public void init() {
        this.visiStudentai = studentasDAO.loadAll();

        String studentasIdParam = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("studentasId");

        if(studentasIdParam==null) {
            return;
        }

        Integer studId = Integer.parseInt(studentasIdParam);
        Studentas studentasIsParametru = studentasDAO.findOne(studId);
        List<PasirenkamasisDalykas> pasirinktiDalykai = studentasIsParametru.getPasirenkamiejiDalykai();
        List<PasirenkamasisDalykas> visiDalykai = pasirenkamasisDalykasDAO.loadAll();
        laisviPasirenkamiejiDalykai = visiDalykai.stream()
                .filter(dalykas -> !pasirinktiDalykai.contains(dalykas))
                .collect(Collectors.toList());
    }

    @Transactional
    public String pridetiPasirenkamajiDalyka(){
        // getting studentasId
        Map<String, String> requestParameters =
                FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        Integer studentasIsParametru = Integer.parseInt(requestParameters.get("studentasId"));

        System.out.println("Metodas: pridetiPasirenkamajiDalyka");
        System.out.println("Studentas: " + studentasIsParametru);
        System.out.println("Dalyko ID: " + pridedamasPasirenkamasisDalykas);

        studentasDAO.addPasirenkamasisDalykas(studentasIsParametru, pridedamasPasirenkamasisDalykas);
        return "pasirenkamiejiDalykai.xhtml?studentasId=" + requestParameters.get("studentasId")+"&faces-redirect=true";
    }

    @Transactional
    public void sukurtiStudenta() {
        Grupe grupe = grupeDAO.findOne(sukuriamasStudentasDTO.getGrupeId());
        Studentas s = new Studentas();

        s.setGrupe(grupe);
        s.setVardas(sukuriamasStudentasDTO.getVardas());
        this.studentasDAO.persist(s);
    }
}
