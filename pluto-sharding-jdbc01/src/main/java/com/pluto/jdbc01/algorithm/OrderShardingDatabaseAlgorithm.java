package com.pluto.jdbc01.algorithm;

import io.shardingsphere.api.algorithm.sharding.ListShardingValue;
import io.shardingsphere.api.algorithm.sharding.ShardingValue;
import io.shardingsphere.api.algorithm.sharding.complex.ComplexKeysShardingAlgorithm;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.LinkedHashSet;

/**
 * @author yaojian
 * @date 2019/7/2
 */
@Slf4j
public class OrderShardingDatabaseAlgorithm implements ComplexKeysShardingAlgorithm {
    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames, Collection<ShardingValue> shardingValues) {
        Collection<String> result = new LinkedHashSet<>(availableTargetNames.size());

        for (ShardingValue shardingValue : shardingValues) {
            if (shardingValue instanceof ListShardingValue) {
                ListShardingValue value = (ListShardingValue) shardingValue;
                Collection<Long> values = value.getValues();
                for (Long val : values) {
                    String suffix = String.valueOf( val % 2L);
                    for (String dbName : availableTargetNames) {
                        if (dbName.endsWith(suffix)) {
                            result.add(dbName);
                            return result;
                        }
                    }
                }

            }
        }




        return result;
    }
}
