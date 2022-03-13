package com.demo.strdemo;

public class substrdemo {
    public static void main(String[] args) {

        String s1 = "SIP/2.0 487 Request Terminated";
        String substring = s1.substring(s1.indexOf(" ") + 1);
        System.out.println("==" + substring);

    }
}
