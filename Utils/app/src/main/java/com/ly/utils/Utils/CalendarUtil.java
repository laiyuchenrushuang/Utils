package com.ly.utils.Utils;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by ly on 2019/9/11 17:35
 * <p>
 * Copyright is owned by chengdu haicheng technology
 * co., LTD. The code is only for learning and sharing.
 * It is forbidden to make profits by spreading the code.
 */
public class CalendarUtil {
    static Calendar dayTotal = Calendar.getInstance();
    static SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM");
    public static int getTotalDaysByMonth(int year,int month){
        try {
            dayTotal.setTime(simpleDate.parse(year+"-"+month));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dayTotal.getActualMaximum(Calendar.DAY_OF_MONTH);
    }
}
