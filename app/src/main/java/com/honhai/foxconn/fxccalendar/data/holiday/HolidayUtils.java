package com.honhai.foxconn.fxccalendar.data.holiday;

import android.content.Context;

import com.honhai.foxconn.fxccalendar.R;

import java.util.ArrayList;
import java.util.Locale;

public class HolidayUtils {

    private static Table[] taiwanTable = new Table[]{
            new Table(R.string.stringHolidayNewYearsDay, "2016-01-01"),
            new Table(R.string.stringHolidayChineseNewYearsEve, "2016-02-07"),
            new Table(R.string.stringHolidayChineseNewYear, "2016-02-08"),
            new Table(R.string.stringHolidayChineseNewYear, "2016-02-09"),
            new Table(R.string.stringHolidayChineseNewYear, "2016-02-10"),
            new Table(R.string.stringHolidayObserved, "2016-02-11"),
            new Table(R.string.stringHolidayHoliday, "2016-02-12"),
            new Table(R.string.stringHolidayWorkday, "2016-01-30"),
            new Table(R.string.stringHoliday228PeaceMemorialDay, "2016-02-28"),
            new Table(R.string.stringHolidayObserved, "2016-02-29"),
            new Table(R.string.stringHolidayChingMingFestival, "2016-04-04"),
            new Table(R.string.stringHolidayChildrensDay, "2016-04-04"),
            new Table(R.string.stringHolidayObserved, "2016-04-05"),
            new Table(R.string.stringHolidayDragonBoatFestival, "2016-06-09"),
            new Table(R.string.stringHolidayHoliday, "2016-06-10"),
            new Table(R.string.stringHolidayWorkday, "2016-06-04"),
            new Table(R.string.stringHolidayMoonFestival, "2016-09-15"),
            new Table(R.string.stringHolidayHoliday, "2016-09-16"),
            new Table(R.string.stringHolidayWorkday, "2016-09-10"),
            new Table(R.string.stringHolidayNationalDay, "2016-10-10"),

            new Table(R.string.stringHolidayNewYearsDay, "2017-01-01"),
            new Table(R.string.stringHolidayObserved, "2017-01-02"),
            new Table(R.string.stringHolidayChineseNewYearsEve, "2017-01-27"),
            new Table(R.string.stringHolidayChineseNewYear, "2017-01-28"),
            new Table(R.string.stringHolidayChineseNewYear, "2017-01-29"),
            new Table(R.string.stringHolidayChineseNewYear, "2017-01-30"),
            new Table(R.string.stringHolidayChineseNewYear, "2017-01-31"),
            new Table(R.string.stringHolidayChineseNewYear, "2017-02-01"),
            new Table(R.string.stringHolidayHoliday, "2017-02-27"),
            new Table(R.string.stringHolidayWorkday, "2017-02-18"),
            new Table(R.string.stringHoliday228PeaceMemorialDay, "2017-02-28"),
            new Table(R.string.stringHolidayChildrensDay, "2017-04-04"),
            new Table(R.string.stringHolidayChingMingFestival, "2017-04-04"),
            new Table(R.string.stringHolidayObserved, "2017-04-03"),
            new Table(R.string.stringHolidayHoliday, "2017-05-29"),
            new Table(R.string.stringHolidayHoliday, "2017-06-03"),
            new Table(R.string.stringHolidayDragonBoatFestival, "2017-05-30"),
            new Table(R.string.stringHolidayMoonFestival, "2017-10-04"),
            new Table(R.string.stringHolidayHoliday, "2017-10-09"),
            new Table(R.string.stringHolidayWorkday, "2017-09-30"),
            new Table(R.string.stringHolidayNationalDay, "2017-10-10"),

            new Table(R.string.stringHolidayNewYearsDay, "2018-01-01"),
            new Table(R.string.stringHolidayChineseNewYearsEve, "2018-02-15"),
            new Table(R.string.stringHolidayChineseNewYear, "2018-02-16"),
            new Table(R.string.stringHolidayChineseNewYear, "2018-02-17"),
            new Table(R.string.stringHolidayChineseNewYear, "2018-02-18"),
            new Table(R.string.stringHolidayObserved, "2018-02-19"),
            new Table(R.string.stringHolidayObserved, "2018-02-20"),
            new Table(R.string.stringHoliday228PeaceMemorialDay, "2018-02-28"),
            new Table(R.string.stringHolidayChildrensDay, "2018-04-14"),
            new Table(R.string.stringHolidayChingMingFestival, "2018-04-05"),
            new Table(R.string.stringHolidayHoliday, "2018-04-06"),
            new Table(R.string.stringHolidayWorkday, "2018-03-31"),
            new Table(R.string.stringHolidayDragonBoatFestival, "2018-06-18"),
            new Table(R.string.stringHolidayMoonFestival, "2018-09-24"),
            new Table(R.string.stringHolidayNationalDay, "2018-10-10"),
            new Table(R.string.stringHolidayHoliday, "2018-12-31"),
            new Table(R.string.stringHolidayWorkday, "2018-12-22"),

            new Table(R.string.stringHolidayNewYearsDay, "2019-01-01"),
            new Table(R.string.stringHolidayChineseNewYearsEve, "2019-02-04"),
            new Table(R.string.stringHolidayChineseNewYear, "2019-02-05"),
            new Table(R.string.stringHolidayChineseNewYear, "2019-02-06"),
            new Table(R.string.stringHolidayChineseNewYear, "2019-02-07"),
            new Table(R.string.stringHolidayHoliday, "2019-02-08"),
            new Table(R.string.stringHolidayWorkday, "2019-01-19"),
            new Table(R.string.stringHoliday228PeaceMemorialDay, "2019-02-28"),
            new Table(R.string.stringHolidayHoliday, "2019-03-01"),
            new Table(R.string.stringHolidayWorkday, "2019-02-23"),
            new Table(R.string.stringHolidayChildrensDay, "2019-04-04"),
            new Table(R.string.stringHolidayChingMingFestival, "2019-04-05"),
            new Table(R.string.stringHolidayDragonBoatFestival, "2019-06-07"),
            new Table(R.string.stringHolidayMoonFestival, "2019-09-13"),
            new Table(R.string.stringHolidayNationalDay, "2019-10-10"),
            new Table(R.string.stringHolidayHoliday, "2019-10-11"),
    };

    private static Table[] chinaTable = new Table[]{
            new Table(R.string.stringHolidayNewYearsDay, "2016-01-01"),
            new Table(R.string.stringHolidayChineseNewYearsEve, "2016-02-07"),
            new Table(R.string.stringHolidayChineseNewYear, "2016-02-08"),
            new Table(R.string.stringHolidayChineseNewYear, "2016-02-09"),
            new Table(R.string.stringHolidayChineseNewYear, "2016-02-10"),
            new Table(R.string.stringHolidayChineseNewYear, "2016-02-11"),
            new Table(R.string.stringHolidayChineseNewYear, "2016-02-12"),
            new Table(R.string.stringHolidayChineseNewYear, "2016-02-13"),
            new Table(R.string.stringHolidayWorkday, "2016-02-06"),
            new Table(R.string.stringHolidayWorkday, "2016-02-14"),
            new Table(R.string.stringHolidayChingMingFestival, "2016-04-04"),
            new Table(R.string.stringHolidayLaborDay, "2016-05-01"),
            new Table(R.string.stringHolidayObserved, "2016-05-02"),
            new Table(R.string.stringHolidayDragonBoatFestival, "2016-06-09"),
            new Table(R.string.stringHolidayHoliday, "2016-06-10"),
            new Table(R.string.stringHolidayWorkday, "2016-06-12"),
            new Table(R.string.stringHolidayMoonFestival, "2016-09-15"),
            new Table(R.string.stringHolidayHoliday, "2016-09-16"),
            new Table(R.string.stringHolidayWorkday, "2016-09-18"),
            new Table(R.string.stringHolidayNationalDay, "2016-10-01"),
            new Table(R.string.stringHolidayNationalDay, "2016-10-02"),
            new Table(R.string.stringHolidayNationalDay, "2016-10-03"),
            new Table(R.string.stringHolidayNationalDay, "2016-10-04"),
            new Table(R.string.stringHolidayNationalDay, "2016-10-05"),
            new Table(R.string.stringHolidayNationalDay, "2016-10-06"),
            new Table(R.string.stringHolidayNationalDay, "2016-10-07"),
            new Table(R.string.stringHolidayWorkday, "2016-10-08"),
            new Table(R.string.stringHolidayWorkday, "2016-10-09"),

            new Table(R.string.stringHolidayNewYearsDay, "2017-01-01"),
            new Table(R.string.stringHolidayObserved, "2017-01-02"),
            new Table(R.string.stringHolidayChineseNewYearsEve, "2017-01-27"),
            new Table(R.string.stringHolidayChineseNewYear, "2017-01-28"),
            new Table(R.string.stringHolidayChineseNewYear, "2017-01-29"),
            new Table(R.string.stringHolidayChineseNewYear, "2017-01-30"),
            new Table(R.string.stringHolidayChineseNewYear, "2017-01-31"),
            new Table(R.string.stringHolidayChineseNewYear, "2017-02-01"),
            new Table(R.string.stringHolidayChineseNewYear, "2017-02-02"),
            new Table(R.string.stringHolidayWorkday, "2017-01-22"),
            new Table(R.string.stringHolidayWorkday, "2017-02-04"),
            new Table(R.string.stringHolidayChingMingFestival, "2017-04-04"),
            new Table(R.string.stringHolidayHoliday, "2017-04-03"),
            new Table(R.string.stringHolidayWorkday, "2017-04-01"),
            new Table(R.string.stringHolidayLaborDay, "2017-05-01"),
            new Table(R.string.stringHolidayDragonBoatFestival, "2017-05-30"),
            new Table(R.string.stringHolidayHoliday, "2017-05-29"),
            new Table(R.string.stringHolidayWorkday, "2017-05-27"),
            new Table(R.string.stringHolidayNationalDay, "2017-10-01"),
            new Table(R.string.stringHolidayNationalDay, "2017-10-02"),
            new Table(R.string.stringHolidayNationalDay, "2017-10-03"),
            new Table(R.string.stringHolidayMoonFestival, "2017-10-04"),
            new Table(R.string.stringHolidayNationalDay, "2017-10-05"),
            new Table(R.string.stringHolidayNationalDay, "2017-10-06"),
            new Table(R.string.stringHolidayNationalDay, "2017-10-07"),
            new Table(R.string.stringHolidayNationalDay, "2017-10-08"),
            new Table(R.string.stringHolidayWorkday, "2017-09-30"),

            new Table(R.string.stringHolidayNewYearsDay, "2018-01-01"),
            new Table(R.string.stringHolidayChineseNewYearsEve, "2018-02-15"),
            new Table(R.string.stringHolidayChineseNewYear, "2018-02-16"),
            new Table(R.string.stringHolidayChineseNewYear, "2018-02-17"),
            new Table(R.string.stringHolidayChineseNewYear, "2018-02-18"),
            new Table(R.string.stringHolidayChineseNewYear, "2018-02-19"),
            new Table(R.string.stringHolidayChineseNewYear, "2018-02-20"),
            new Table(R.string.stringHolidayChineseNewYear, "2018-02-21"),
            new Table(R.string.stringHolidayWorkday, "2018-02-11"),
            new Table(R.string.stringHolidayWorkday, "2018-02-24"),
            new Table(R.string.stringHolidayChingMingFestival, "2018-04-05"),
            new Table(R.string.stringHolidayHoliday, "2018-04-06"),
            new Table(R.string.stringHolidayWorkday, "2018-04-08"),
            new Table(R.string.stringHolidayLaborDay, "2018-05-01"),
            new Table(R.string.stringHolidayHoliday, "2018-04-30"),
            new Table(R.string.stringHolidayWorkday, "2018-04-28"),
            new Table(R.string.stringHolidayDragonBoatFestival, "2018-06-18"),
            new Table(R.string.stringHolidayMoonFestival, "2018-09-24"),
            new Table(R.string.stringHolidayNationalDay, "2018-10-01"),
            new Table(R.string.stringHolidayNationalDay, "2018-10-02"),
            new Table(R.string.stringHolidayNationalDay, "2018-10-03"),
            new Table(R.string.stringHolidayNationalDay, "2018-10-04"),
            new Table(R.string.stringHolidayNationalDay, "2018-10-05"),
            new Table(R.string.stringHolidayNationalDay, "2018-10-06"),
            new Table(R.string.stringHolidayNationalDay, "2018-10-07"),
            new Table(R.string.stringHolidayWorkday, "2018-09-29"),
            new Table(R.string.stringHolidayWorkday, "2018-09-30"),
            new Table(R.string.stringHolidayHoliday, "2018-12-31"),
            new Table(R.string.stringHolidayWorkday, "2018-12-29"),

            new Table(R.string.stringHolidayNewYearsDay, "2019-01-01"),
            new Table(R.string.stringHolidayChineseNewYearsEve, "2019-02-04"),
            new Table(R.string.stringHolidayChineseNewYear, "2019-02-05"),
            new Table(R.string.stringHolidayChineseNewYear, "2019-02-06"),
            new Table(R.string.stringHolidayChineseNewYear, "2019-02-07"),
            new Table(R.string.stringHolidayChineseNewYear, "2019-02-08"),
            new Table(R.string.stringHolidayChineseNewYear, "2019-02-09"),
            new Table(R.string.stringHolidayChineseNewYear, "2019-02-10"),
            new Table(R.string.stringHolidayWorkday, "2019-02-02"),
            new Table(R.string.stringHolidayWorkday, "2019-02-03"),
            new Table(R.string.stringHolidayHoliday, "2019-04-05"),
            new Table(R.string.stringHolidayLaborDay, "2019-05-01"),
            new Table(R.string.stringHolidayDragonBoatFestival, "2019-06-07"),
            new Table(R.string.stringHolidayMoonFestival, "2019-09-13"),
            new Table(R.string.stringHolidayNationalDay, "2019-10-01"),
            new Table(R.string.stringHolidayNationalDay, "2019-10-02"),
            new Table(R.string.stringHolidayNationalDay, "2019-10-03"),
            new Table(R.string.stringHolidayNationalDay, "2019-10-04"),
            new Table(R.string.stringHolidayNationalDay, "2019-10-05"),
            new Table(R.string.stringHolidayNationalDay, "2019-10-06"),
            new Table(R.string.stringHolidayNationalDay, "2019-10-07"),
            new Table(R.string.stringHolidayWorkday, "2019-09-29"),
            new Table(R.string.stringHolidayWorkday, "2019-10-12"),
    };

    public static ArrayList<Holiday> getHoliday(Context context, Locale locale) {
        String country = locale.getCountry();
        if (country.equals(Locale.CHINA.getCountry()))
            return getChinaHoliday(context);
        else if (country.equals(Locale.TAIWAN.getCountry()))
            return getTaiwanHoliday(context);
        else
            return getUSHoliday(context);
    }

    private static ArrayList<Holiday> getUSHoliday(Context context) {
        ArrayList<Holiday> holidays = new ArrayList<>();
        return holidays;
    }

    private static ArrayList<Holiday> getTaiwanHoliday(Context context) {
        ArrayList<Holiday> holidays = new ArrayList<>();
        for (Table aTaiwanTable : taiwanTable) {
            holidays.add(new Holiday(context.getResources().getString(aTaiwanTable.resId), aTaiwanTable.date));
        }
        return holidays;
    }

    private static ArrayList<Holiday> getChinaHoliday(Context context) {
        ArrayList<Holiday> holidays = new ArrayList<>();
        for (Table aChinaTable : chinaTable) {
            holidays.add(new Holiday(context.getResources().getString(aChinaTable.resId), aChinaTable.date));
        }
        return holidays;
    }

    private static class Table {
        Table(int resId, String date) {
            this.resId = resId;
            this.date = date;
        }

        public int resId;
        public String date;
    }
}
