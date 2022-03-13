package com.demo.jsondemo;

import com.alibaba.fastjson.JSONObject;

public class jsondemo {
    public static void main(String[] args) {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("a1", 5);
        jsonObject.put("a2", 2);
        jsonObject.put("a3", null);
//        jsonObject.put(null, "a4");
        System.out.println("jsonObject==" + jsonObject);
        System.out.println("a1==" + jsonObject.get("a3"));
        if (jsonObject.get("a3") == null) {
            System.out.println("=====");
        }
        System.out.println("jsonObject==" + jsonObject.size());
        if (jsonObject.size() > 0) {
            System.out.println("jsonObject1==");
        }

        if (jsonObject.containsKey("a5")) {
            System.out.println("jsonObject1=32=");
        }


    }
}
