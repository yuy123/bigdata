package com.flink.app.job;

import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.timestamps.BoundedOutOfOrdernessTimestampExtractor;
import org.apache.flink.streaming.api.functions.windowing.ProcessWindowFunction;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Flink_AppJob {
    public static void main(String[] args) throws Exception {
        //1.env
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);

        //2.source
        DataStreamSource<String> stringDataStreamSource = env.readTextFile("src/data/simple.etl.csv");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        //3.deal
        //etl——）过滤脏数据
        DataStream<String> filter = stringDataStreamSource.filter(a -> {
            return a.split(",").length == 11;
        });

        //添加水位线
        filter.assignTimestampsAndWatermarks(new BoundedOutOfOrdernessTimestampExtractor<String>(Time.seconds(5)) {
            @Override
            public long extractTimestamp(String s) {
                String times = s.split(",")[0];
                long event_time = 0L;
                try {
                    event_time = simpleDateFormat.parse(times).getTime();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return event_time;
            }
        }).map(new MapFunction<String, Tuple2<Integer, Integer>>() {
            @Override
            public Tuple2<Integer, Integer> map(String s) throws Exception {
                //获取leval
                Integer key = Integer.parseInt(s.split(",")[8].split(":")[1]);
                return Tuple2.of(key, 1);
            }
        }).keyBy(x -> x.f0)
                .window(TumblingEventTimeWindows.of(Time.seconds(3)))
                .reduce(new ReduceFunction<Tuple2<Integer, Integer>>() {
                    @Override
                    public Tuple2<Integer, Integer> reduce(Tuple2<Integer, Integer> t1, Tuple2<Integer, Integer> t2) throws Exception {
                        return Tuple2.of(t1.f0, t1.f1 + t1.f1);
                    }
                }, new ProcessWindowFunction<Tuple2<Integer, Integer>, String, Integer, TimeWindow>() {
                    @Override
                    public void process(Integer integer, Context context, Iterable<Tuple2<Integer, Integer>> iterable, Collector<String> collector) throws Exception {
                        long starttime = context.window().getStart();
                        for (Tuple2<Integer, Integer> temp : iterable) {
                            collector.collect("TimeWindow:" + starttime + " key:" + temp.f0 + " count:" + temp.f1);
                        }
                    }
                })
                .print();

        //4.execute
        env.execute("Flink app etl");
    }

}
