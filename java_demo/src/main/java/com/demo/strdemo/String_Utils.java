package com.demo.strdemo;


import org.apache.tomcat.util.buf.StringUtils;

import java.util.Arrays;
import java.util.List;

public class String_Utils {
    public static void main(String[] args) {
        List<String> strings = Arrays.asList("a","b","c");
        System.out.println("strings==" + strings.toString());

        String join = StringUtils.join(Arrays.asList("a","b","c"),'\t');
        System.out.println("join==" + join);
    }
}
