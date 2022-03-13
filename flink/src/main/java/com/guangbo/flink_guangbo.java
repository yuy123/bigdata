package com.guangbo;

import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.state.BroadcastState;
import org.apache.flink.api.common.state.MapStateDescriptor;
import org.apache.flink.api.common.state.ReadOnlyBroadcastState;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.BroadcastStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.co.KeyedBroadcastProcessFunction;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer010;
import org.apache.flink.table.planner.expressions.utils.People;
import org.apache.flink.util.Collector;

import java.sql.Driver;

public class flink_guangbo {

    public static void main(String[] args) {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);

        DataStreamSource<Integer> ele1 = env.fromElements(0, 1, 2, 3, 4, 5, 6);
        DataStreamSource<Integer> ele2 = env.fromElements(2, 3);
        StreamExecutionEnvironment environment = StreamExecutionEnvironment.getExecutionEnvironment();
        environment.setStreamTimeCharacteristic(TimeCharacteristic.IngestionTime);
        environment.enableCheckpointing(1000 * 180);
        FlinkKafkaConsumer010<String> location = KafkaUtil.getConsumer("event_stream", "test_1", "test");
        FlinkKafkaConsumer010<String> object = KafkaUtil.getConsumer("bro_stream", "test_2", "test");
        // 把事件流按key进行分流，这样相同的key会发到同一个节点
        KeyedStream<People, String> driverDatastream = environment.addSource(location).map(new MapFunction<String, Driver>() {
            @Override
            public People map(String s) throws Exception {
                return parse(s);
            }
        }).keyBy((KeySelector<People, String>) people -> people.id);

        // 描述这个map ,key value都为string
        MapStateDescriptor<String, String> mapStateDescriptor = new MapStateDescriptor<String, String>("register", Types.STRING, Types.STRING);
        BroadcastStream<String> broadcast = environment.addSource(object).broadcast(mapStateDescriptor);
        driverDatastream.connect(broadcast).process(new PatternEvaluator()).print();
        try {
            environment.execute("register collect");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class PatternEvaluator extends KeyedBroadcastProcessFunction<String, People, String, People> {

        MapStateDescriptor<String, String> mapStateDescriptor;

        @Override
        public void open(Configuration parameters) throws Exception {
            super.open(parameters);
            // 这里需要初始化map state 描述
            mapStateDescriptor = new MapStateDescriptor<String, String>("register", Types.STRING, Types.STRING);
        }

        // 处理每一个元素，看state是否有匹配的，有的话，下发到下一个节点
        @Override
        public void processElement(People value, ReadOnlyContext ctx, Collector<People> out) throws Exception {
            ReadOnlyBroadcastState<String, String> broadcastState = ctx.getBroadcastState(mapStateDescriptor);
            if ((value.getIdCard() != null && broadcastState.get(value.getIdCard()) != null) || (value.getPhone() != null && broadcastState.get(value.getPhone()) != null)) {
                System.out.println("匹配到" + value.toString());
                out.collect(value);
            }
        }

        // 新增加的广播元素，放入state中
        @Override
        public void processBroadcastElement(String value, Context ctx, Collector<People> out) throws Exception {
            System.out.println("新增加需要监控的" + value.toString());
            BroadcastState<String, String> broadcastState = ctx.getBroadcastState(mapStateDescriptor);
            broadcastState.put(value, value);
        }
    }

}
