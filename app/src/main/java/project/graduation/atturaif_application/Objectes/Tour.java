package project.graduation.atturaif_application.Objectes;

public class Tour {
    String day , time , guide , type;
    int duration ;

    public Tour(String day, String time, String guide, String type, int duration) {
        this.day = day;
        this.time = time;
        this.guide = guide;
        this.type = type;
        this.duration = duration;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getGuide() {
        return guide;
    }

    public void setGuide(String guide) {
        this.guide = guide;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
} // class
