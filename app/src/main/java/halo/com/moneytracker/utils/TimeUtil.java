package halo.com.moneytracker.utils;
/**
 * @version 1.0
 * @author HoVanLy
 * @phone 0986305046
 * @nick halo
 */

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import halo.com.moneytracker.common.Constant;

public final class TimeUtil {
    private static TimeUtil sTimeUtil;
    private static String sFullStringTime = "HH:mm:ss dd/MM/yyyy";
    private static String sShortStringTime = "dd/MM/yyyy";

    public static TimeUtil getInstance() {
        if (sTimeUtil == null) {
            sTimeUtil = new TimeUtil();
        }
        return sTimeUtil;
    }

    public String getFullStringTime(Date date) {
        DateFormat dateFormat = new SimpleDateFormat(sFullStringTime);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return dateFormat.format(cal.getTime());
    }

    public String getShortStringTime(Date date) {
        DateFormat dateFormat = new SimpleDateFormat(sShortStringTime);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return dateFormat.format(cal.getTime());
    }

    public Date getDate(String dateString) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(sShortStringTime);
        try {
            Date date = simpleDateFormat.parse(dateString);
            return date;
        } catch (Exception e) {
            return null;
        }

    }

    public Date getDate(String dateString, Date dateHouse) {
        Calendar calendarHouse = Calendar.getInstance();
        calendarHouse.setTime(dateHouse);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(sShortStringTime);
        try {
            Date date = simpleDateFormat.parse(dateString);
            Calendar calendarDay = Calendar.getInstance();
            calendarDay.setTime(date);
            int year = calendarDay.get(Calendar.YEAR);
            int month = calendarDay.get(Calendar.MONTH) + 1;
            int day = calendarDay.get(Calendar.DAY_OF_MONTH);
            int house = calendarHouse.get(Calendar.HOUR_OF_DAY);
            int minute = calendarHouse.get(Calendar.MINUTE);
            int second = calendarHouse.get(Calendar.SECOND);
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month, day, house, minute, second);
            return calendar.getTime();
        } catch (Exception e) {
            return null;
        }
    }

    public Date getFullStringDate(String dateString) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(sFullStringTime);
        try {
            Date date = simpleDateFormat.parse(dateString);
            return date;
        } catch (Exception e) {
            return null;
        }
    }

    public boolean checkDateOfYear(Date date, int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR) == year;
    }

    public boolean checkDateOfMonthYear(Date date, int month, int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR) == year && (calendar.get(Calendar.MONTH) + 1) == month;
    }

    public boolean checkSameDay(Date date1, Date date2) {
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        calendar1.setTime(date1);
        calendar2.setTime(date2);
        return (calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR))
                && (calendar1.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH))
                && (calendar1.get(Calendar.DAY_OF_MONTH) == calendar2.get(Calendar.DAY_OF_MONTH));
    }

    public int getNumberDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    public int getNumberYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }

    public int getNumberMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH) + 1;
    }

    public String getNameMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int id = calendar.get(Calendar.MONTH) + 1;
        switch (id) {
            case 1:
                return "January";

            case 2:
                return "February";

            case 3:
                return "March";

            case 4:
                return "April";

            case 5:
                return "May";

            case 6:
                return "June";

            case 7:
                return "July";

            case 8:
                return "August";

            case 9:
                return "September";

            case 10:
                return "October";

            case 11:
                return "November";

            case 12:
                return "December";
        }
        return Constant.ERROR_VALUE;
    }

    public String getNameMonth(int numberMonth) {
        switch (numberMonth) {
            case 1:
                return "January";

            case 2:
                return "February";

            case 3:
                return "March";

            case 4:
                return "April";

            case 5:
                return "May";

            case 6:
                return "June";

            case 7:
                return "July";

            case 8:
                return "August";

            case 9:
                return "September";

            case 10:
                return "October";

            case 11:
                return "November";

            case 12:
                return "December";
        }
        return Constant.ERROR_VALUE;
    }
}
