package com.flink.app.source;

import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.source.RichSourceFunction;

public class getMysqlSource extends RichSourceFunction<String> {

    @Override
    public void open(Configuration parameters) {
    }

    @Override
    public void run(SourceContext ctx) throws Exception {
    }

    @Override
    public void cancel() {

    }
}
