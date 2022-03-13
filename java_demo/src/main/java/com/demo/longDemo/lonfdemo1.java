package com.demo.longDemo;

public class lonfdemo1 {
    public static void main(String[] args) {
        long l = 999999999999l - 202101171336l;
        System.out.println("=l=" + l);
        long l1 = 202101171336l % 100;
        System.out.println("=l1=" + l1);

        String substring = "202101171336".substring(0, 11);
        System.out.println("=substring=" + substring);
        long l2 = Long.parseLong(substring);
        System.out.println("=substring=" + substring);

        long l3 = (202101171336l / 10) + 1;
        System.out.println("=l3=" + l3);

        String s = ((202101171336l / 10) + 1) + "0";
        System.out.println("=s=" + s);
        long l4 = Long.parseLong(((202101171336l / 10) + 1) + "0");
        System.out.println("=l4=" + l4);
        long l5 = 999999999990l-Long.parseLong(((202101171336l / 10) + 1) + "0");
        System.out.println("=l5=" + l5);

        long l6 = 999999999990l-Long.parseLong((202101171336l / 10) + "0");
        System.out.println("=l6=" + l6);


    }
}
