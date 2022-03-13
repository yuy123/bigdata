package com.flinkcdc;

import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.TableResult;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;
import org.apache.flink.table.planner.factories.TestValuesTableFactory;

/**
 * Description: Flink-CDC 测试
 * Date:
 *
 * @author guanyu.chang
 */
public class flinkcdc103Demo {

    public static void main(String[] args) throws Exception {
        EnvironmentSettings fsSettings = EnvironmentSettings.newInstance().useBlinkPlanner().inStreamingMode().build();
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);
        StreamTableEnvironment tableEnv = StreamTableEnvironment.create(env, fsSettings);

        // 数据源表
        //dcrm_deviplines_map 并行mapper表
        String dcrm_deviplines_map_sourceDDL = "CREATE TABLE dcrm_deviplines_map (\n" +
                " id INT NOT NULL,\n" +
                " device_ip STRING,\n" +
                " trunkgroup STRING,\n" +
                " line_code STRING,\n" +
                " create_times STRING,\n" +
                " update_times STRING\n" +
                ") WITH ('connector' = 'mysql-cdc',\n" +
                " 'hostname' = '192.168.3.140',\n" +
                " 'port' = '3306',\n" +
                " 'username' = 'all',\n" +
                " 'password' = '112233',\n" +
                " 'database-name' = 'dcrm',\n" +
                " 'table-name' = 'dcrm_deviplines_map')";

        //dcrm_devip_paralle并行总数表
        String dcrm_devip_parallelnums_sourceDDL = "CREATE TABLE dcrm_devip_parallelnums (\n" +
                " id INT NOT NULL,\n" +
                " device_ip STRING,\n" +
                " trunkgroup STRING,\n" +
                " parallelnums BIGINT,\n" +
                " create_times STRING,\n" +
                " update_times STRING\n" +
                ") WITH ('connector' = 'mysql-cdc',\n" +
                " 'hostname' = '192.168.3.140',\n" +
                " 'port' = '3306',\n" +
                " 'username' = 'all',\n" +
                " 'password' = '112233',\n" +
                " 'database-name' = 'dcrm',\n" +
                " 'table-name' = 'dcrm_devip_parallelnums')";

        String dcrm_telnums_sourceDDL = "CREATE TABLE dcrm_telnums (\n" +
                " id INT NOT NULL,\n" +
                " company_ip STRING,\n" +
                " device_ip STRING,\n" +
                " line_code STRING,\n" +
                " line_nums BIGINT,\n" +
                " event_times STRING,\n" +
                " create_times STRING,\n" +
                " update_times STRING,\n" +
                ") WITH ( 'connector' = 'mysql-cdc',\n" +
                " 'hostname' = '192.168.3.140',\n" +
                " 'port' = '3306',\n" +
                " 'username' = 'all',\n" +
                " 'password' = '112233',\n" +
                " 'database-name' = 'dcrm',\n" +
                " 'table-name' = 'dcrm_telnums')";

        // 输出目标表
        String sinkDDL =
                "CREATE TABLE dcrm_devip_unusedparallelnums (\n" +
                        " id STRING,\n" +
                        " device_ip STRING,\n" +
                        " trunkgroup STRING,\n" +
                        " parallelnums BIGINT,\n" +
                        " usedparallelnums BIGINT,\n" +
                        " nousedparallelnums BIGINT,\n" +
                        " create_times STRING,\n" +
                        " update_times STRING \n" +
                        ") WITH ('connector' = 'print')";

        // 简单的聚合处理
        String transformSQL =
                "INSERT OVERWRITE TABLE  dcrm_devip_unusedparallelnums " +
                        "select t1.device_ip ,t1.trunkgroup ,t1.parallelnums ,t1.parallelnums - sum(t3.line_nums) , sum(t3.line_nums) ,1 as create_times ,1 as update_times \n" +
                        "from dcrm_devip_parallelnums t1\n" +
                        "left join dcrm_deviplines_map t2\n" +
                        "on t1.device_ip =t2.device_ip \n" +
                        "and t1.trunkgroup =t2.trunkgroup \n" +
                        "left join dcrm_telnums t3\n" +
                        "on t2.device_ip = t3.device_ip \n" +
                        "and t2.line_code =t3.line_code \n" +
                        "group by t1.device_ip ,t1.trunkgroup ,t1.parallelnums ;";

        tableEnv.executeSql(dcrm_deviplines_map_sourceDDL);
        tableEnv.executeSql(dcrm_devip_parallelnums_sourceDDL);
        tableEnv.executeSql(dcrm_telnums_sourceDDL);
        tableEnv.executeSql(sinkDDL);
        TableResult result = tableEnv.executeSql(transformSQL);


/*        // 等待flink-cdc完成快照
        waitForSnapshotStarted("tb_sink");
        result.print();
        result.getJobClient().get().cancel().get();*/
    }

    private static void waitForSnapshotStarted(String sinkName) throws InterruptedException {
        while (sinkSize(sinkName) == 0) {
            Thread.sleep(100);
        }
    }

    private static int sinkSize(String sinkName) {
        synchronized (TestValuesTableFactory.class) {
            try {
                return TestValuesTableFactory.getRawResults(sinkName).size();
            } catch (IllegalArgumentException e) {
                // job is not started yet
                return 0;
            }
        }
    }

}
