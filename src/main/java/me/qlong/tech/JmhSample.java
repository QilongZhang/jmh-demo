/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package me.qlong.tech;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author qilong.zql 18/8/9-上午12:44
 */
@State(Scope.Benchmark)
public class JmhSample {

    public static void main(String[] args) throws RunnerException{
        Options options = new OptionsBuilder()
                .include(JmhSample.class.getCanonicalName())
                .forks(1)
                .warmupIterations(5)
                .measurementIterations(3)
                .measurementTime(new TimeValue(3, TimeUnit.MILLISECONDS))
                .threads(10)
                .timeUnit(TimeUnit.MILLISECONDS)
                .build();
        new Runner(options).run();
    }


    private Random random = new Random();

    @Param({"10", "100", "1000"})
    private int size;

    private Map<Integer, String> testMap;

    @Setup
    public void setup() {
        testMap = new HashMap<Integer, String>();
        for (int i = 0; i < size; ++i) {
            testMap.put(random.nextInt(), "a");
        }
    }

    @Benchmark
    public void measureEntrySet() {
        for (Map.Entry entry : testMap.entrySet()) {
            entry.getKey();
            entry.getValue();
        }
    }

    @Benchmark
    public void measureKeySet() {
        for (Integer i : testMap.keySet()) {
            testMap.get(i);
        }
    }
}