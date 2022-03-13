package com.demo.jihe.List;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class listdemo {

    public static List<String> types2 = Arrays.asList("SIP/2.0 100 Trying", "SIP/2.0 180 Ringing", "SIP/2.0 200 OK", "SIP/2.0 403 Forbidden",
            "SIP/2.0 480 Temporarily Unavailable", "SIP/2.0 486 Busy Here", "SIP/2.0 487 Request Terminated", "SIP/2.0 500 Server Internal Error",
            "SIP/2.0 503 Service Unavailable", "SIP/2.0 408 Request timeout", "SIP/2.0 504 Gateway timeout", "SIP/2.0 600 Busy everywhere",
            " INVITE sip:", " PRACK sip:", " BYE sip:", " NOTIFY sip:", "ACK sip:", " REFER sip:", " CANCEL sip:");

    public static void main(String[] args) {

        System.out.println("clo1ne2==" + types2.toString().trim());
        for (String words : types2) {
            if (words.contains("ACK sip:")) {
                System.out.println("clo1ne2=="+words);
            }else {
                System.out.println("clo1ne3=="+words);
            }

        }

    }
}
