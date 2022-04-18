package com.example.trafficscotland;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public  class DateHelper {

    public static long numberOfDays(Date startDate, Date endDate) {
        long end = endDate.getTime();
        long start = startDate.getTime();
        // add 1 for the start date
        return TimeUnit.MILLISECONDS.toDays(Math.abs(end - start)) + 1;
    }

    public static Calendar convertRssDateToCalendarObj(String date) {
        String shortDate = convertLongDateToShort(date);
        return convertStringToDate(shortDate);
    }

    public static String convertLongDateToShort(String dateString) {
        String regex = "\\d+";
        StringBuilder result = new StringBuilder();
        String[] words = dateString.split(" ");
        for (String word : words) {
            if (word.matches(regex)) {
                result.append(word).append("/");
            } else if (!getNumOfMonth(word).isEmpty()) {
                result.append(getNumOfMonth(word)).append("/");
            }
        }
        return result.substring(0, result.length() - 1); // take the last / off the end
    }

    public static Calendar convertStringToDate(String dateString) {
        String[] numbers = "/".split(dateString);
        int day = Integer.parseInt(numbers[0]);
        int month = Integer.parseInt(numbers[1]);
        int year = Integer.parseInt(numbers[2]);
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        return calendar;
    }

    public static String getNumOfMonth(String month) {
        if ("January".equals(month)) {
            return "01";
        } else if ("February".equals(month)) {
            return "02";
        } else if ("March".equals(month)) {
            return "03";
        } else if ("April".equals(month)) {
            return "04";
        } else if ("May".equals(month)) {
            return "05";
        } else if ("June".equals(month)) {
            return "06";
        } else if ("July".equals(month)) {
            return "07";
        } else if ("August".equals(month)) {
            return "08";
        } else if ("September".equals(month)) {
            return "09";
        } else if ("October".equals(month)) {
            return "10";
        } else if ("November".equals(month)) {
            return "11";
        } else if ("December".equals(month)) {
            return "12";
        }
        return "";
    }
}
