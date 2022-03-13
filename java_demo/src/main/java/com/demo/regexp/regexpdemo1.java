package com.demo.regexp;

import jdk.nashorn.internal.runtime.regexp.RegExp;

public class regexpdemo1 {
    public static void main(String[] args) {

/*        String reg = "/^[1-9]\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])\s+(20|21|22|23|[0-1]\d):[0-5]\d:[0-5]\d$/";
        String regExp = "";
        regExp.matches(reg);*/

        StringBuffer sbuff = new StringBuffer();
        sbuff.append("15:55:45.930  192.168.2.5     local0.notice  [S=115640169] [SID:627503241] (     sip_stack)(25983736  )  SDPBodyMedia::Delete - id = 156");
        sbuff.append(" b");
        sbuff.append(" c");
        System.out.println(sbuff.toString());

        sbuff = new StringBuffer();
        System.out.println(sbuff);

        if (sbuff.toString().contains(" local0.notice ")) {
            System.out.println("123");
        }

    }
}
