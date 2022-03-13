package com.demo.strdemo;

public class split2 {

    public static void main(String[] args) {
        String str2 = "02/22 15:16:59.538 192.168.2.5 local0.notice [S=123978488] [SID:627606823] CANCEL sip:396015018285763@192.168.2.5:5060 SIP/2.0 Via: SIP/2.0/UDP 10.0.7.43:5080;rport;branch=z9hG4bKQBUQX6etgjFpe Max-Forwards: 69 From: \"31235853\" <sip:31235853@10.0.7.43>;tag=XB3gQN63D35pN To: <sip:396015018285763@192.168.2.5:5060> Call-ID: 30071954-0e52-123b-2a94-00163e1a0b5c CSeq: 48203300 CANCEL Reason: SIP;cause=487;text=\"ORIGINATOR_CANCEL\" Content-Length: 0 \n";

        String[] split = str2.split(" CANCEL sip:396015018285763@192.168.2.5:5060 SIP/2.0 ");
        for (String lines : split) {
            System.out.println("=lines=" + lines);
        }





    }
}
