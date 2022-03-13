package com.demo.strdemo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class splitStr {
    public static void main(String[] args) {

        System.out.println("==============空格后的字符start==================");
        String s_null = "20 a1 b1 c1";
        String sub = s_null.substring(0,4);
        System.out.println("substring2==" + sub);
        String substring1 = s_null.substring(s_null.lastIndexOf(" ") + 1);
        String substring2 = s_null.substring(0,s_null.lastIndexOf(" "));
        System.out.println("s_null.lastIndexOf(\" \")==" + s_null.lastIndexOf(" "));
        System.out.println("substring2==" + substring2);
        System.out.println("substring1==" + substring1);
        System.out.println("==============空格后的字符end==================");
        String s1 = "202102150650-a1-b1-c1";
        //小时
        System.out.println("hours1=" + s1.substring(1, 2));
        long hours1 = Long.parseLong(s1.split("_")[0].substring(8, 10));
        System.out.println("hours1=" + hours1);
        long minuter = Long.parseLong(s1.split("-")[0]) + 9l;
        System.out.println("minuter=" + minuter);
        String substring = ((Long.parseLong(s1.split("-")[0]) + 9l) / 10 + "0").substring(10, 12);
        System.out.println("substring=" + substring);
        System.out.println("==============================================================================");
        String times = s1.split("_")[0];
        String year = s1.split("_")[0].substring(0, 4);
        String ymonths = s1.split("_")[0].substring(0, 6);
        String ymdays = s1.split("_")[0].substring(0, 8);
        String hours = s1.split("_")[0].substring(8, 10);
        String minute = s1.split("_")[0].substring(10, 12);
        System.out.println("times=" + times
                + ",year=" + year + ",ymonths=" + ymonths
                + ",ymdays=" + ymdays
                + ",hours=" + hours
                + ",minute=" + minute
        );

        System.out.println("==========================多空格====================================================");

        String str = "hello            song";
        Pattern p = Pattern.compile("\\s+");
        Matcher m = p.matcher(str);
        String w = m.replaceAll(" ");

        System.out.println(w);

    }
}
