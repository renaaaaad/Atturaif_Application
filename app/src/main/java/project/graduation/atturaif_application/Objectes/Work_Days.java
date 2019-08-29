package project.graduation.atturaif_application.Objectes;

import java.util.List;

public class Work_Days {
    String day;
    String start, end;

    public Work_Days(String day, String start, String end) {
        this.day = day;
        this.start = start;
        this.end = end;
    } // constructor

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }
} // class
