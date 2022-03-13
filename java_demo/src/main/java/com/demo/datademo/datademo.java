package com.demo.datademo;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class datademo {
    public static void main(String[] args) throws ParseException {

        Date date = new Date();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("date==" + format.format(date));

        System.out.println("=====================================================");

        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, -24);
        String yesterdayDate = dateFormat.format(calendar.getTime());
        System.out.println("yesterdayDate=" + yesterdayDate);

        System.out.println("=====================================================");


        String inputdays = "2022012000";
        int hours = 1;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHH");
        Calendar cal = Calendar.getInstance();
        cal.setTime(sdf.parse(inputdays));
        cal.add(Calendar.HOUR, -hours);
        String startdate = sdf.format(cal.getTime());
        System.out.println("startdate=" + startdate + ",,inputdays=" + inputdays);

        System.out.println("=====================================================");

//        int days = -1;
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
//        Calendar cal = Calendar.getInstance();
//        cal.setTime(sdf.parse("20210101"));
//        cal.add(Calendar.DATE,days);
//        String startdate = sdf.format(cal.getTime());
//        System.out.println("startdate=" + startdate);


    }
}
