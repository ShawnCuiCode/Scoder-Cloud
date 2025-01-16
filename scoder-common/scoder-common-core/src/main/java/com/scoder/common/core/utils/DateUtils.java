package com.scoder.common.core.utils;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.lang.management.ManagementFactory;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Utility class for date and time operations.
 * <p>
 * This class provides various methods to handle date and time formatting,
 * parsing, and calculations. It extends Apache Commons Lang's DateUtils.
 *
 * @author Shawn Cui
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {
    // Common date formats
    public static String YYYY = "yyyy";
    public static String YYYY_MM = "yyyy-MM";
    public static String YYYY_MM_DD = "yyyy-MM-dd";
    public static String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
    public static String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    // Supported date parsing patterns
    private static String[] parsePatterns = {
            "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM",
            "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
            "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM"
    };

    /**
     * Gets the current date as a Date object.
     *
     * @return the current date
     */
    public static Date getNowDate() {
        return new Date();
    }

    /**
     * Gets the current date as a string in the format "yyyy-MM-dd".
     *
     * @return the current date string
     */
    public static String getDate() {
        return dateTimeNow(YYYY_MM_DD);
    }

    /**
     * Gets the current time as a string in the format "yyyy-MM-dd HH:mm:ss".
     *
     * @return the current time string
     */
    public static String getTime() {
        return dateTimeNow(YYYY_MM_DD_HH_MM_SS);
    }

    /**
     * Gets the current date and time as a string in the format "yyyyMMddHHmmss".
     *
     * @return the current date-time string
     */
    public static String dateTimeNow() {
        return dateTimeNow(YYYYMMDDHHMMSS);
    }

    /**
     * Formats the current date and time using the specified format.
     *
     * @param format the desired date format
     * @return the formatted date-time string
     */
    public static String dateTimeNow(final String format) {
        return parseDateToStr(format, new Date());
    }

    /**
     * Formats the given date as a string in the "yyyy-MM-dd" format.
     *
     * @param date the date to format
     * @return the formatted date string
     */
    public static String dateTime(final Date date) {
        return parseDateToStr(YYYY_MM_DD, date);
    }

    /**
     * Formats the given date using the specified format.
     *
     * @param format the desired date format
     * @param date   the date to format
     * @return the formatted date string
     */
    public static String parseDateToStr(final String format, final Date date) {
        return new SimpleDateFormat(format).format(date);
    }

    /**
     * Parses a date string using the specified format.
     *
     * @param format the format of the date string
     * @param ts     the date string to parse
     * @return the parsed date
     */
    public static Date dateTime(final String format, final String ts) {
        try {
            return new SimpleDateFormat(format).parse(ts);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Returns the current date path in "yyyy/MM/dd" format.
     *
     * @return the date path
     */
    public static String datePath() {
        return DateFormatUtils.format(new Date(), "yyyy/MM/dd");
    }

    /**
     * Returns the current date as a string in "yyyyMMdd" format.
     *
     * @return the formatted date string
     */
    public static String dateTime() {
        return DateFormatUtils.format(new Date(), "yyyyMMdd");
    }

    /**
     * Parses a string into a Date object using predefined formats.
     *
     * @param str the date string to parse
     * @return the parsed date or null if parsing fails
     */
    public static Date parseDate(Object str) {
        if (str == null) {
            return null;
        }
        try {
            return parseDate(str.toString(), parsePatterns);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * Retrieves the server's start time as a Date object.
     *
     * @return the server's start time
     */
    public static Date getServerStartDate() {
        long time = ManagementFactory.getRuntimeMXBean().getStartTime();
        return new Date(time);
    }

    /**
     * Calculates the time difference between two dates and formats it as a human-readable string.
     *
     * @param endDate the end date
     * @param nowDate the start date
     * @return a string representing the time difference in days, hours, and minutes
     */
    public static String getDatePoor(Date endDate, Date nowDate) {
        long nd = 1000 * 24 * 60 * 60; // milliseconds in a day
        long nh = 1000 * 60 * 60; // milliseconds in an hour
        long nm = 1000 * 60; // milliseconds in a minute

        long diff = endDate.getTime() - nowDate.getTime(); // time difference in milliseconds
        long day = diff / nd; // days
        long hour = diff % nd / nh; // hours
        long min = diff % nd % nh / nm; // minutes

        return day + " days " + hour + " hours " + min + " minutes";
    }
}