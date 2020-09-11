package com.study;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Tl {

  private static ThreadLocal<DateFormat> localsdf = new ThreadLocal<DateFormat>() {
    @Override
    protected DateFormat initialValue() {
     return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
     }
   };

    public static Date parse(String dateStr) throws ParseException {
        return localsdf.get().parse(dateStr);
    }

    public static String format(Date date) {
        return localsdf.get().format(date);
    }

    @Override
    protected void finalize(){
        localsdf.remove();
    }
}

