package com.demo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class zhengze {

    public static void main(String[] args) {
        String s1 = "[SID:631164598]";
        String replace = s1.replace("]", "");
        System.out.println("=replace=" + replace);
        Matcher matcher = Pattern.compile("\\d+").matcher(s1);
        System.out.println("" + matcher);
    }
}
