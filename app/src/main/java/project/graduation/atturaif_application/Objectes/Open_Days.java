package project.graduation.atturaif_application.Objectes;

public class Open_Days {
    String day;
    String openAt, closeAt;

    public Open_Days(String day, String openAt, String closeAt) {
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

    public String getOpenAt() {
        return openAt;
    }

    public void setOpenAt(String openAt) {
        this.openAt = openAt;
    }

    public String getCloseAt() {
        return closeAt;
    }

    public void setCloseAt(String closeAt) {
        this.closeAt = closeAt;
    }
} // class
