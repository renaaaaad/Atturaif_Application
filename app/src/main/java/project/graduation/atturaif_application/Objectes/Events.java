package project.graduation.atturaif_application.Objectes;

import java.util.Calendar;

public class Events {

    private String EventTime,EventnameAR, EventnameEN, image, descriptionAR,descriptionEN;
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

    public String getEventnameAR() {
        return EventnameAR;
    }

    public String getDescriptionAR() {
        return descriptionAR;
    }

    public void setEventnameAR(String eventnameAR) {
        EventnameAR = eventnameAR;
    }

    public void setDescriptionAR(String descriptionAR) {
        this.descriptionAR = descriptionAR;
    }

    public String getEventnameEN() {
        return EventnameEN;
    }

    public Events(Calendar calendar, int circle) {
    }

    public String getImage() {
        return image;
    }

    public void setEventTime(String eventTime) {
        EventTime = eventTime;
    }

    public void setEventnameEN(String eventnameEN) {
        EventnameEN = eventnameEN;
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

    public void setDescriptionEN(String descriptionEN) {
        this.descriptionEN = descriptionEN;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public String getDescriptionEN() {
        return descriptionEN;
    }

    public Events(String eventTime,String eventnameAR, String eventnameEN, String image, int month, int day,String descriptionAR,  String descriptionEN,int year) {
        EventTime = eventTime;
        EventnameEN = eventnameEN;
        EventnameAR=eventnameAR;
        this.image = image;
        this.month = month;
        this.day = day;
        this.descriptionEN = descriptionEN;
        this.descriptionAR = descriptionAR;
        this.year=year;
    }

    public Events(){

    }
}
