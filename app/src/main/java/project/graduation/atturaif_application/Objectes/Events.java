package project.graduation.atturaif_application.Objectes;

public class Events {

    private String EventTime, Eventname, image, description;
    int year,day,month;

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getEventTime() {
        return EventTime;
    }

    public String getEventname() {
        return Eventname;
    }

    public Events() {
    }

    public String getImage() {
        return image;
    }

    public void setEventTime(String eventTime) {
        EventTime = eventTime;
    }

    public void setEventname(String eventname) {
        Eventname = eventname;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public String getDescription() {
        return description;
    }

    public Events(String eventTime, String eventname, String image, int month, int day, String description,int year) {
        EventTime = eventTime;
        Eventname = eventname;
        this.image = image;
        this.month = month;
        this.day = day;
        this.description = description;
        this.year=year;
    }
}
