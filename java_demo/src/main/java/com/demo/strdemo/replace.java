package com.demo.strdemo;

public class replace {

    public static void main(String[] args) {
        String s1 = "2022-02-22 11:19:12.517";
        String substring = s1.replace("-", "").substring(0, 8);
        System.out.println(substring);
        System.out.println("s1==" + s1.length());




    }
}
