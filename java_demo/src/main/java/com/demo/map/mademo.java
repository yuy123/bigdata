package com.demo.map;

import java.util.HashMap;

public class mademo {
    public static void main(String[] args) {
        HashMap<String, String> map = new HashMap<>();
        map.put("a", "a");
        map.put("b", "b");
        map.put("c", "c");
        System.out.println("map==>" + (map.get("d") == null ? 0 : map.get("d")));

        String s1 = map.get("d") == null ? "1" : map.get("d");
        System.out.println("s1==>" + s1);
    }
}
