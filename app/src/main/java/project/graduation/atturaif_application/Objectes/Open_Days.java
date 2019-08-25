package project.graduation.atturaif_application.Objectes;

public class Open_Days {
    String day;
    Long openAt, closeAt;

    public Open_Days(String day, Long openAt, Long closeAt) {
        this.day = day;
        this.openAt = openAt;
        this.closeAt = closeAt;
    } // constructor

    public Open_Days() {

    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public Long getOpenAt() {
        return openAt;
    }

    public void setOpenAt(Long openAt) {
        this.openAt = openAt;
    }

    public Long getCloseAt() {
        return closeAt;
    }

    public void setCloseAt(Long closeAt) {
        this.closeAt = closeAt;
    }
} // class
