package project.graduation.atturaif_application.Objectes;

import java.util.List;

public class Shops {
    String nameEn, nameAr, openTime, closeTime, image, descriptionAR, descriptionEN;
    List<Open_Days> days;

    public Shops() {

    }

    public Shops(String nameEn, String nameAr, String openTime, String closeTime, String image, String descriptionAR, String descriptionEN, List<Open_Days> days) {
        this.nameEn = nameEn;
        this.nameAr = nameAr;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.image = image;
        this.descriptionAR = descriptionAR;
        this.descriptionEN = descriptionEN;
        this.days = days;
    } // shops

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getNameAr() {
        return nameAr;
    }

    public void setNameAr(String nameAr) {
        this.nameAr = nameAr;
    }

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    public String getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(String closeTime) {
        this.closeTime = closeTime;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescriptionAR() {
        return descriptionAR;
    }

    public void setDescriptionAR(String descriptionAR) {
        this.descriptionAR = descriptionAR;
    }

    public String getDescriptionEN() {
        return descriptionEN;
    }

    public void setDescriptionEN(String descriptionEN) {
        this.descriptionEN = descriptionEN;
    }

    public List<Open_Days> getDays() {
        return days;
    }

    public void setDays(List<Open_Days> days) {
        this.days = days;
    }
} //class
