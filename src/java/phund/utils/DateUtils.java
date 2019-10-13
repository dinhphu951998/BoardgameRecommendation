/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phund.utils;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author PhuNDSE63159
 */
public class DateUtils {

    public static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");

    public static Calendar calendar = Calendar.getInstance();

    public static String getTodayString() {
        Date date = Calendar.getInstance().getTime();
        return dateFormat.format(date);
    }

    public static long getMillisFromNow(long amount, ChronoUnit field) {
        Instant now = Instant.now();
        now = now.plus(amount, field);
        long millis = now.toEpochMilli();
        return millis;
    }

    public static boolean isBeforeNow(long millis) {
        long now = Instant.now().toEpochMilli();
        return millis < now;
    }
}
