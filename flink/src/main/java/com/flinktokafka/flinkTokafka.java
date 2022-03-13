package com.flinktokafka;

import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer011;

import java.util.Properties;

/**
 * flink 数据 有端口写入kafka
 */

public class flinkTokafka {
    public static void main(String[] args) throws Exception {
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
/*
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("zookeeper.connect", "localhost:2181");
        props.put("group.id", "student-group");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("auto.offset.reset", "latest");

        DataStreamSource<String> student = env.addSource(new FlinkKafkaConsumer011<>(
                READ_TOPIC,   //这个 kafka topic 需要和上面的工具类的 topic 一致
                new SimpleStringSchema(),
                props)).setParallelism(1);
        student.print();*/

        DataStreamSource<String> outputds = env.socketTextStream("192.168.129.103", 9999);
        outputds.print();

        outputds.addSink(new FlinkKafkaProducer011<>("192.168.129.103:9092", "ods_g_telephonelogs",new SimpleStringSchema())).name("flink-connectors-kafka")
                .setParallelism(1);

        env.execute("flink learning connectors kafka");
    }
}
