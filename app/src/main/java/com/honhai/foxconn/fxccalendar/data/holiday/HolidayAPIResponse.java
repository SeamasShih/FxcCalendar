package com.honhai.foxconn.fxccalendar.data.holiday;


import java.util.List;

/**
 * This class represents the complete JSON response of the API <code>/v1/holidays</code>
 */

public class HolidayAPIResponse {
    public final String LABEL_STATUS = "status";
    public final String LABEL_HOLIDAYS = "holidays";
    public final String LABEL_HOLIDAYS_HOLIDAY = "holiday";
    public final String LABEL_HOLIDAYS_HOLIDAY_NAME = "name";
    public final String LABEL_HOLIDAYS_HOLIDAY_DATE = "date";
    public final String LABEL_HOLIDAYS_HOLIDAY_OBSERVED = "observed";
    public final String LABEL_HOLIDAYS_HOLIDAY_PUBLIC = "public";

    private Integer status;
    private List<Holiday> holidays;
    private String error;

    public HolidayAPIResponse() {
    }

    public String toString() {
        return "HolidayResponse{status=" + this.status + ", holidays=" + this.holidays + ", error='" + this.error + '\'' + '}';
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<Holiday> getHolidays() {
        return this.holidays;
    }

    public void setHolidays(List<Holiday> holidays) {
        this.holidays = holidays;
    }

    public String getError() {
        return this.error;
    }

    public void setError(String error) {
        this.error = error;
    }
}