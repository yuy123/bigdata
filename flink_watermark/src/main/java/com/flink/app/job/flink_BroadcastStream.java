package com.flink.app.job;

import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.util.Properties;

/**
 * 广播流
 */
public class flink_BroadcastStream {
    public static void main(String[] args) {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);

        //kafka
        Properties prop = new Properties();
        prop.setProperty("brok.id", "");
        prop.setProperty("brok.id", "");
        prop.put("", "");


    }
}
