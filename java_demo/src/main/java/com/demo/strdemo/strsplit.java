package com.demo.strdemo;

import com.alibaba.fastjson.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class strsplit {
    public static void main(String[] args) {

        String str = "02/22 16:46:13.223 192.168.2.5 local0.notice [S=124036081] [SID:627607333] NOTIFY sip:800061@192.168.2.16:5060 SIP/2.0 Via: SIP/2.0/UDP 192.168.2.5:5060;branch=z9hG4bKac1831764297 Max-Forwards: 70 From: <sip:398018601359008@192.168.2.16>;tag=1c1867102558 To: <sip:31235856@192.168.2.16>;tag=0073B644-3BB6-112B-929C-0E02A8C0AA77-14926386 Call-ID: 0073B63A-3BB6-112B-929C-0E02A8C0AA77-6003326@192.168.2.16 CSeq: 1 NOTIFY Contact: <sip:1114@192.168.2.5:5060> Supported: em,timer,replaces,path,resource-priority Allow: REGISTER,OPTIONS,INVITE,ACK,CANCEL,BYE,NOTIFY,PRACK,REFER,INFO,SUBSCRIBE,UPDATE Event: refer;id=3 Subscription-State: active;expires=9999 User-Agent: Mediant 2000/v.6.60A.228.011 Content-Type: message/sipfrag Content-Length: 20 SIP/2.0 100 Trying \n";
        String title = "NOTIFY sip:";
        JSONObject jsonObject = new JSONObject();
        //标题前数据
        String[] split_title = str.split(title);
        for (int i = 0; i < split_title.length; i++) {
            System.out.println("=split_title=" + i + "," + split_title[i]);
        }
        Date date = new Date();
        DateFormat format = new SimpleDateFormat("yyyy");
        jsonObject.put("event_times", format.format(date) + "-" + split_title[0].split(" ")[0].replace("/", "-") + " " + split_title[0].split(" ")[1]);
        jsonObject.put("Titles_ip", split_title[0].split(" ")[2]);
        jsonObject.put("Titles_sid", split_title[0].split(" ")[5]);

        //标题后数据
        String[] split_from = split_title[1].split(": ");
        for (int i = 0; i < split_from.length; i++) {
            System.out.println("=words=" + i + "," + split_from[i]);
        }
        jsonObject.put("Titles", title + split_from[0].substring(0, split_from[0].lastIndexOf(" ")));
        jsonObject.put(split_from[0].substring(split_from[0].lastIndexOf(" ") + 1), split_from[1].substring(0, split_from[1].lastIndexOf(" ")));
        jsonObject.put(split_from[1].substring(split_from[1].lastIndexOf(" ") + 1), split_from[2].substring(0, split_from[2].lastIndexOf(" ")));
        jsonObject.put(split_from[2].substring(split_from[2].lastIndexOf(" ") + 1), split_from[3].substring(0, split_from[3].lastIndexOf(" ")));
        jsonObject.put(split_from[3].substring(split_from[3].lastIndexOf(" ") + 1), split_from[4].substring(0, split_from[4].lastIndexOf(" ")));
        jsonObject.put(split_from[4].substring(split_from[4].lastIndexOf(" ") + 1), split_from[5].substring(0, split_from[5].lastIndexOf(" ")));
        jsonObject.put(split_from[5].substring(split_from[5].lastIndexOf(" ") + 1), split_from[6].substring(0, split_from[6].lastIndexOf(" ")));
        jsonObject.put(split_from[6].substring(split_from[6].lastIndexOf(" ") + 1), split_from[7].substring(0, split_from[7].lastIndexOf(" ")));
        //jsonObject.put(split_from[7].substring(split_from[7].lastIndexOf(" ") + 1), split_from[8].substring(0, split_from[8].lastIndexOf(" ")));
        System.out.println("=jsonObject=" + jsonObject);

    }
}
