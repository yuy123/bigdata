package com.flink.app.job;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSink;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.timestamps.BoundedOutOfOrdernessTimestampExtractor;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.util.Collector;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class flink_sum {

    public static void main(String[] args) throws Exception {

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        DataStreamSource<String> stringDataStreamSource = env.readTextFile("src/data/simple.etl.csv");


        DataStream<String> filter = stringDataStreamSource.filter(a -> a.split(",").length == 11);
        DataStreamSink<String> print = filter.assignTimestampsAndWatermarks(new BoundedOutOfOrdernessTimestampExtractor<String>(Time.seconds(5)) {
                    @Override
                    public long extractTimestamp(String s) {
                        String s1 = s.split(",")[0];
                        long time = 0l;
                        try {
                            time = simpleDateFormat.parse(s1).getTime();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        return time;
                    }
                }).map(new MapFunction<String, Tuple2<String, Integer>>() {
                    @Override
                    public Tuple2<String, Integer> map(String s) throws Exception {
                        String key = s.split(",")[5].toString();
                        return Tuple2.of(key, 1);
                    }
                }).keyBy(x -> x.f0)
                .sum(1)
                .flatMap(new FlatMapFunction<Tuple2<String, Integer>, String>() {
                    @Override
                    public void flatMap(Tuple2<String, Integer> t1, Collector<String> collector) throws Exception {
                        String s = "地方：" + t1.f0 + ",数量：" + t1.f1;

                        collector.collect(s);
                    }
                })
                .print();

        env.execute("flink key ");
    }
}
