package project.graduation.atturaif_application.Objectes;

import java.util.List;

public class Guide {
    int id;
    Long nationality_id;
    String nameAR, nameEN, nationalityAR, nationalityEN;
    List<String> languges;
    List<Work_Days> working_days;

    public Guide(int id, Long nationality_id, String nameAR, String nameEN, String nationalityAR, String nationalityEN, List<String> languges, List<Work_Days> working_days) {
        this.id = id;
        this.nationality_id = nationality_id;
        this.nameAR = nameAR;
        this.nameEN = nameEN;
        this.nationalityAR = nationalityAR;
        this.nationalityEN = nationalityEN;
        this.languges = languges;
        this.working_days = working_days;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Long getNationality_id() {
        return nationality_id;
    }

    public void setNationality_id(Long nationality_id) {
        this.nationality_id = nationality_id;
    }

    public List<String> getLanguges() {
        return languges;
    }

    public void setLanguges(List<String> languges) {
        this.languges = languges;
    }

    public List<Work_Days> getWorking_days() {
        return working_days;
    }

    public void setWorking_days(List<Work_Days> working_days) {
        this.working_days = working_days;
    }

    public String getNameAR() {
        return nameAR;
    }

    public void setNameAR(String nameAR) {
        this.nameAR = nameAR;
    }

    public String getNameEN() {
        return nameEN;
    }

    public void setNameEN(String nameEN) {
        this.nameEN = nameEN;
    }

    public String getNationalityAR() {
        return nationalityAR;
    }

    public void setNationalityAR(String nationalityAR) {
        this.nationalityAR = nationalityAR;
    }

    public String getNationalityEN() {
        return nationalityEN;
    }

    public void setNationalityEN(String nationalityEN) {
        this.nationalityEN = nationalityEN;
    }
} // class
