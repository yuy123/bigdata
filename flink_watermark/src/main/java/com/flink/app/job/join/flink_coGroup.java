package com.flink.app.job.join;

import com.alibaba.fastjson.JSON;
import com.flink.app.bean.AnalyticsAccessLogRecord;
import com.flink.app.bean.OrderDoneLogRecord;
import org.apache.flink.api.common.functions.CoGroupFunction;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.assigners.TumblingProcessingTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.flink.util.Collector;

import java.util.Properties;


/**
 * left/right outer join
 * 它的调用方式类似于 join() 算子，也需要开窗，但是 CoGroupFunction 比 JoinFunction 更加灵活，可以按照用户指定的逻辑匹配左流和/或右流的数据并输出
 */
public class flink_coGroup {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(4);
        env.enableCheckpointing(5000);
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);

        //kafka
        Properties props = new Properties();
        props.setProperty("bootstrap.servers", "localhost:9092");
        props.setProperty("group.id", "test");

        DataStream<String> clickSourceStream = env.addSource(new FlinkKafkaConsumer<>("ods_analytics_access_log", new SimpleStringSchema(), props).setStartFromLatest());
        DataStream<String> orderSourceStream = env.addSource(new FlinkKafkaConsumer<>("ods_ms_order_done", new SimpleStringSchema(), props).setStartFromLatest());
        DataStream<AnalyticsAccessLogRecord> clickRecordStream = clickSourceStream.map(message -> JSON.parseObject(message, AnalyticsAccessLogRecord.class));
        DataStream<OrderDoneLogRecord> orderRecordStream = orderSourceStream.map(message -> JSON.parseObject(message, OrderDoneLogRecord.class));

        clickRecordStream.coGroup(orderRecordStream)
                .where(record -> record.getMerchandiseId())
                .equalTo(record -> record.getMerchandiseId())
                .window(TumblingProcessingTimeWindows.of(Time.seconds(10)))
                .apply(new CoGroupFunction<AnalyticsAccessLogRecord, OrderDoneLogRecord, Tuple2<String, Long>>() {
                    @Override
                    public void coGroup(Iterable<AnalyticsAccessLogRecord> accessRecords, Iterable<OrderDoneLogRecord> orderRecords, Collector<Tuple2<String, Long>> collector) throws Exception {
                        for (AnalyticsAccessLogRecord accessRecord : accessRecords) {
                            boolean isMatched = false;
                            for (OrderDoneLogRecord orderRecord : orderRecords) {
                                // 右流中有对应的记录
                                collector.collect(new Tuple2<>(accessRecord.getMerchandiseName(), orderRecord.getPrice()));
                                isMatched = true;
                            }
                            if (!isMatched) {
                                // 右流中没有对应的记录
                                collector.collect(new Tuple2<>(accessRecord.getMerchandiseName(), null));
                            }
                        }
                    }
                })
                .print().setParallelism(1);

        env.execute("flink left join ");
    }
}
