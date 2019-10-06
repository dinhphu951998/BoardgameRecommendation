/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phund.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author PhuNDSE63159
 */
public class DateUtils {
    public static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    
    public static String getTodayString(){
        Date date = Calendar.getInstance().getTime();
        return dateFormat.format(date);
    }
    
}
