package com.flink.app.job.join;

import com.alibaba.fastjson.JSON;
import com.flink.app.bean.AnalyticsAccessLogRecord;
import com.flink.app.bean.OrderDoneLogRecord;
import org.apache.commons.lang3.StringUtils;
import org.apache.flink.api.common.functions.JoinFunction;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.assigners.TumblingProcessingTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;

import java.util.Arrays;
import java.util.Properties;

public class flink_join {
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

        /**
         * join() 算子提供的语义为"Window join"，即按照指定字段和（滚动/滑动/会话）窗口进行 inner join，支持处理时间和事件时间两种时间特征。
         * 以下示例以10秒滚动窗口，将两个流通过商品 ID 关联，取得订单流中的售价相关字段。
         */
        clickRecordStream.join(orderRecordStream)
                .where(record -> record.getMerchandiseId())
                .equalTo(record -> record.getMerchandiseId())
                .window(TumblingProcessingTimeWindows.of(Time.seconds(10)))
                .apply(new JoinFunction<AnalyticsAccessLogRecord, OrderDoneLogRecord, String>() {
                    @Override
                    public String join(AnalyticsAccessLogRecord accessRecord, OrderDoneLogRecord orderRecord) throws Exception {
                        return StringUtils.join(Arrays.asList(accessRecord.getMerchandiseId(),
                                orderRecord.getPrice(),
                                orderRecord.getCouponMoney(),
                                orderRecord.getRebateAmount()), '\t');
                    }
                }).print().setParallelism(1);


        env.execute("flink to broadcast");
    }
}
