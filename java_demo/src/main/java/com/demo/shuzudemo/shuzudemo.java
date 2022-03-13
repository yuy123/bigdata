package com.demo.shuzudemo;

public class shuzudemo {

    public static void main(String[] args) {
        System.out.println("hello");
        int[] arr = new int[3];
        arr[0] = 1;
        arr[1] = 2;
        arr[2] = 3;
        System.out.println("=arr.length=" + arr.length);
        System.out.println("=arr[2]=" + arr[2]);
        int[] clone = arr.clone();
        System.out.println("=clone=" + clone.toString());

        int[] arr2 = new int[arr.length * 2];
        for (int i = 0; i < arr.length; i++) {
            arr2[i] = arr[i];
        }
        System.out.println("=arr2.length=" + arr2.length);

    }
}
