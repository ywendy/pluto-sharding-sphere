package org.pluto.common.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * @author yaojian
 * @date 2019/7/2
 */
public class IdGenerator {

    private long sequence = 0L;
    private long epoch = 1420041600L;

    private long genBit = 11L;
    private long workerIdBit = 5L;
    private long sequenceBit = 13L;

    private long sequenceShift = genBit;
    private long workerIdShift = sequenceBit + genBit;
    private long timestampShift = sequenceBit + workerIdBit + genBit;

    private long workerIdMask = -1L ^ (-1L << workerIdBit);
    private long sequenceMask = -1L ^ (-1L << sequenceBit);
    private long genMask = -1L ^ (-1L << genBit);


    private long lastTimestamp = -1L;


    public synchronized long nextId(long workerId, long userId) {
        //获取时间戳
        long timestamp = timeGen();

        if (timestamp < lastTimestamp) {
            throw new RuntimeException(String.format("Clock moved backwards.  Refusing to generate id for %d " +
                    "milliseconds", lastTimestamp - timestamp));
        }

        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & sequenceMask;
            if (sequence == 0) {
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0L;
        }

        lastTimestamp = timestamp;
        return (timestamp - epoch) << timestampShift | workerId << workerIdShift | sequence << sequenceShift | userId & genMask;
    }


    public long[] decodeId(long id) {

        long[] result = new long[4];

        result[0] = (id >>> timestampShift) + epoch;
        result[1] = (id >>> workerIdShift) & workerIdMask;
        result[2] = (id >>> sequenceShift) & sequenceMask;
        result[3] = id & genMask;

        return result;

    }


    /**
     * 如果时间延后了，需要等待时间超过最后更新时间，等待
     *
     * @param lastTimestamp
     * @return
     */
    protected long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp ;
    }

    protected long timeGen() {
        return System.currentTimeMillis() / 1000L;
    }


    public static String convertTimeToString(Long time) {
        DateTimeFormatter ftf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return ftf.format(LocalDateTime.ofInstant(Instant.ofEpochMilli(time), ZoneId.systemDefault()));
    }


    public static void main(String[] args) {
        IdGenerator idGenerator = new IdGenerator();
        long id = idGenerator.nextId(1L, 9875642L);
        System.out.println(id);
        long[] result = idGenerator.decodeId(id);
        System.out.println(convertTimeToString(result[0]*1000)+","+result[1]+","+result[2]+","+result[3]);
    }


}
