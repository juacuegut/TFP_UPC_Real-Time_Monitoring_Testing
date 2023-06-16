package com.orangehrm.utils;

import io.github.cdimascio.dotenv.Dotenv;
import org.influxdb.BatchOptions;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Point;
import org.influxdb.dto.Query;

public class InfluxDBUtils {

    private static final InfluxDB INFLUXDB = InfluxDBFactory.connect("http://"+EnvUtils.getEnvVariable("HOST_IP")+":8086", "admin", "admin123");
    private static final String DATABASE = "selenium";

    public static void initializeDatabase() {
        INFLUXDB.query(new Query("DROP DATABASE \"selenium\""));
        INFLUXDB.query(new Query("CREATE DATABASE \"selenium\""));
        INFLUXDB.setDatabase(DATABASE);
        INFLUXDB.enableBatch(
                BatchOptions.DEFAULTS
                        .threadFactory(runnable -> {
                            Thread thread = new Thread(runnable);
                            thread.setDaemon(true);
                            return thread;
                        }));
    }


    public static void post(final Point point){
        INFLUXDB.write(point);

        try {
            Thread.sleep(5_000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
