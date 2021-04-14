package com.honhai.foxconn.fxccalendar.data.holiday;

public class Holiday {

    private String name;
    private String date;
    private String observed;
    private Boolean _public;

    Holiday() {
    }

    Holiday(String name, String date) {
        this.name = name;
        this.date = date;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        if (date != null) {
            String[] time = date.split("-");
            return time[0] + "-" + String.valueOf(Integer.valueOf(time[1]) - 1) + "-" + time[2];
        }
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getObserved() {
        return this.observed;
    }

    public void setObserved(String observed) {
        this.observed = observed;
    }

    public Boolean get_public() {
        return this._public;
    }

    public void set_public(Boolean _public) {
        this._public = _public;
    }

}