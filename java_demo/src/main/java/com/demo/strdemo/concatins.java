package com.demo.strdemo;

public class concatins {

    public static String str = "SIP/2.0 100 Trying, SIP/2.0 180 Ringing, SIP/2.0 200 OK, SIP/2.0 403 Forbidden, SIP/2.0 200 OK,SIP/2.0 480 Temporarily Unavailable,SIP/2.0 486 Busy Here, " +
            "SIP/2.0 487 Request Terminated, SIP/2.0 500 Server Internal Error,SIP/2.0 503 Service Unavailable, SIP/2.0 408 Request timeout, SIP/2.0 504 Gateway timeout, " +
            "SIP/2.0 600 Busy everywhere,INVITE sip:,PRACK sip:,BYE sip:,NOTIFY sip:,ACK sip:, REFER sip:,  CANCEL sip:";

    public static void main(String[] args) {
        String str2 = "02/22 15:43:51.283 192.168.2.5 local0.notice [S=123993293] [SID:627607001] FER sip:1052@192.168.2.5:5060 SIP/2.0 From: <sip:31235856@192.168.2.16:5060>;tag=0073B644-3BB6-112B-929C-0E02A8C0AA77-14917322 To: <sip:398013809621116@192.168.2.16>;tag=1c651337781 Call-ID: 0073B63A-3BB6-112B-929C-0E02A8C0AA77-5998348@192.168.2.16 CSeq: 3 REFER Content-Length: 0 Via: SIP/2.0/UDP 192.168.2.16:5060;branch=z9hG4bK0073B64E-3BB6-112B-929C-0E02A8C0AA77-25706992 Contact: <sip:800061@192.168.2.16:5060> Refer-To: <sip:9001@192.168.2.16:5060;genesysid=01UB769RMO8IN4KS1O1AHG5AES05EIKL> Referred-By: <sip:9001@192.168.2.16:5060;genesysid=01UB769RMO8IN4KS1O1AHG5AES05EIKL> X-Genesys-CallUUID: 01UB761RMO8IN4KS1O1AHG5AES02D145 Max-Forwards: 70 \n";

        String str3 = "02/23 13:23:25.026 192.168.2.5 local0.notice [S=124578712] [SID:627614044] NOTIFY sip:800686@192.168.2.16:5060 SIP/2.0 Via: SIP/2.0/UDP 192.168.2.5:5060;branch=z9hG4bKac437090746 Max-Forwards: 70 From: <sip:113013003588718@192.168.2.16>;tag=1c1087933618 To: <sip:31589895@192.168.2.16>;tag=0073B644-3BB6-112B-929C-0E02A8C0AA77-14987504 Call-ID: 0073B63A-3BB6-112B-929C-0E02A8C0AA77-6034356@192.168.2.16 CSeq: 1 NOTIFY Contact: <sip:1242@192.168.2.5:5060> Supported: em,timer,replaces,path,resource-priority Allow: REGISTER,OPTIONS,INVITE,ACK,CANCEL,BYE,NOTIFY,PRACK,REFER,INFO,SUBSCRIBE,UPDATE Event: refer;id=3 Subscription-State: active;expires=9999 User-Agent: Mediant 2000/v.6.60A.228.011 Content-Type: message/sipfrag Content-Length: 20 ";

        if (str3.contains("SIP/2.0 100 Trying") && !str3.contains("NOTIFY sip:")) {
            System.out.println("123");
        } else {
            System.out.println("456");
        }

    }
}
