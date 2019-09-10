package project.graduation.atturaif_application.Objectes;

public class Tour {
    String dayEN , dayAR , time , guide , typeAR , typeEN;
    int duration ;

    public Tour(String dayEN, String dayAR, String time, String guide, String typeAR, String typeEN, int duration) {
        this.dayEN = dayEN;
        this.dayAR = dayAR;
        this.time = time;
        this.guide = guide;
        this.typeAR = typeAR;
        this.typeEN = typeEN;
        this.duration = duration;
    }

    public String getDayEN() {
        return dayEN;
    }

    public void setDayEN(String dayEN) {
        this.dayEN = dayEN;
    }

    public String getDayAR() {
        return dayAR;
    }

    public void setDayAR(String dayAR) {
        this.dayAR = dayAR;
    }

    public String getTypeAR() {
        return typeAR;
    }

    public void setTypeAR(String typeAR) {
        this.typeAR = typeAR;
    }

    public String getTypeEN() {
        return typeEN;
    }

    public void setTypeEN(String typeEN) {
        this.typeEN = typeEN;
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

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
} // class
