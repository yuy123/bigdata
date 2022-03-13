package com.flink.app.job.join;

import com.alibaba.fastjson.JSON;
import com.flink.app.bean.AnalyticsAccessLogRecord;
import com.flink.app.bean.OrderDoneLogRecord;
import org.apache.commons.lang3.StringUtils;
import org.apache.flink.api.common.functions.CoGroupFunction;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.co.ProcessJoinFunction;
import org.apache.flink.streaming.api.windowing.assigners.TumblingProcessingTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.flink.util.Collector;

import java.util.Arrays;
import java.util.Properties;


/**
 * join() 和 coGroup() 都是基于窗口做关联的。但是在某些情况下，两条流的数据步调未必一致。
 * 例如，订单流的数据有可能在点击流的购买动作发生之后很久才被写入，如果用窗口来圈定，很容易 join 不上。
 * 所以 Flink 又提供了"Interval join"的语义，按照指定字段以及右流相对左流偏移的时间区间进行关联，即：
 */
public class flink_intervalJoin {
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

        clickRecordStream.keyBy(record -> record.getMerchandiseId())
                .intervalJoin(orderRecordStream.keyBy(record -> record.getMerchandiseId()))
                .between(Time.seconds(-30), Time.seconds(30))
                .process(new ProcessJoinFunction<AnalyticsAccessLogRecord, OrderDoneLogRecord, String>() {
                    @Override
                    public void processElement(AnalyticsAccessLogRecord accessRecord, OrderDoneLogRecord orderRecord, Context context, Collector<String> collector) throws Exception {
                        collector.collect(StringUtils.join(Arrays.asList(accessRecord.getMerchandiseId(),
                                orderRecord.getPrice(),
                                orderRecord.getCouponMoney(),
                                orderRecord.getRebateAmount()), '\t'));
                    }
                }).print().setParallelism(1);

        env.execute("flink intervalJoin ");
    }
}
