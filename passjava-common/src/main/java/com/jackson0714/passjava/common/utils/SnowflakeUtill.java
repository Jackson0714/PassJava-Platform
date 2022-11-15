package com.jackson0714.passjava.common.utils;

public class SnowflakeUtill {

    // 起始时间戳
    private final static long START_STMP = 1168391101267L;

    // 每部分的位数
    // 序列号占用位数
    private final static long SEQUENCE_BIT = 12;
    // 机器id占用位数
    private final static long MACHINE_BIT = 5;
    // 机房id占用位数
    private final static long DATACENTER_BIT = 5;

    // 每部分最大值
//    private final static long MAX_DATACENTER_NUM = ~(-1L << DATACENTER_BIT);
//    private final static long MAX_MACHINE_NUM = ~(-1L << MACHINE_BIT);
    private final static long MAX_SEQUENCE = ~(-1L << SEQUENCE_BIT);

    // 每部分向左的位移
    private final static long MACHINE_LEFT = SEQUENCE_BIT;
    private final static long DATACENTER_LEFT = SEQUENCE_BIT + MACHINE_BIT;
    private final static long TIMESTMP_LEFT = DATACENTER_LEFT + DATACENTER_BIT;
    // 机房id
    private final static long DATACENTERID = 1;
    // 机器id
    private final static long MACHINEID = 1;
    // 序列号
    private long sequence = 0L;
    // 上次的时间戳
    private long lastStmp = -1L;

    // 产生下一个ID
    public synchronized long getNextId() {
        long currStmp = getNewStmp();
        if (currStmp < lastStmp) {
            throw new RuntimeException("Clock moved backwards.Refusing to generate id");
        }
        if (currStmp == lastStmp) {
            // 若在相同毫秒内 序列号自增
            sequence = (sequence + 1) & MAX_SEQUENCE;
            // 同一毫秒的序列数已达到最大
            if (sequence == 0L) {
                currStmp = getNextMill();
            }
        } else {
            // 若在不同毫秒内 则序列号置为0
            sequence = 0L;
        }
        lastStmp = currStmp;
        /*
         * 时间戳部分 机房id部分 机器id部分 序列号部分
         */
        return (currStmp - START_STMP) << TIMESTMP_LEFT
                | DATACENTERID << DATACENTER_LEFT
                | MACHINEID << MACHINE_LEFT
                | sequence;
    }

    // 获取新的毫秒数
    private long getNextMill() {
        long mill = getNewStmp();
        while (mill <= lastStmp) {
            mill = getNewStmp();
        }
        return mill;
    }

    // 获取当前的毫秒数
    private long getNewStmp() {
        return System.currentTimeMillis();
    }
}