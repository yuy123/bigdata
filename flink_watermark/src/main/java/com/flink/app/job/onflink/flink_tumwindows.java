package com.flink.app.job.onflink;

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

import static org.apache.flink.streaming.api.windowing.time.Time.milliseconds;

public class flink_tumwindows {

    public static void main(String[] args) throws Exception {

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        DataStreamSource<String> stringDataStreamSource = env.readTextFile("src/data/simple.etl.csv");

        DataStream<String> filter = stringDataStreamSource.filter(a -> a.split(",").length == 11);
        filter.assignTimestampsAndWatermarks(new BoundedOutOfOrdernessTimestampExtractor<String>(Time.seconds(5)) {
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
                }).map(new MapFunction<String, Tuple2<Integer, Integer>>() {
                    @Override
                    public Tuple2<Integer, Integer> map(String s) throws Exception {
                        Integer key = Integer.parseInt(s.split(",")[1].split(":")[1]);
                        return Tuple2.of(key, 1);
                    }
                }).keyBy(x -> x.f0)
                .sum(1)
/*
                    .window(TumblingEventTimeWindows.of(Time.seconds(3)))
                    .reduce(new ReduceFunction<Tuple2<Integer, Integer>>() {
                     @Override
                     public Tuple2<Integer, Integer> reduce(Tuple2<Integer, Integer> t1, Tuple2<Integer, Integer> t2) throws Exception {
                         return Tuple2.of(t1.f0, t1.f1 + t1.f1);
                     }
                 }, new ProcessWindowFunction<Tuple2<Integer, Integer>, String, Integer, TimeWindow>() {
                     @Override
                     public void process(Integer integer, ProcessWindowFunction<Tuple2<Integer, Integer>, String, Integer, TimeWindow>.Context context, Iterable<Tuple2<Integer, Integer>> iterable, Collector<String> collector) throws Exception {
                         long start = context.window().getStart();
                         for (Tuple2<Integer, Integer> tmp : iterable) {
                             collector.collect("TimeWindow:" + start + ",key:" + tmp.f0 + ",count:" + tmp.f1);
                         }
                     }
                 })
*/
                .print();

        env.execute("flink key ");
    }
}
