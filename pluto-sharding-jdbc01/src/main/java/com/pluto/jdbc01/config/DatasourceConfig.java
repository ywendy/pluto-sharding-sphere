package com.pluto.jdbc01.config;

import com.pluto.jdbc01.algorithm.OrderShardingDatabaseAlgorithm;
import com.pluto.jdbc01.algorithm.OrderShardingTableAlgorithm;
import io.shardingsphere.api.config.rule.ShardingRuleConfiguration;
import io.shardingsphere.api.config.rule.TableRuleConfiguration;
import io.shardingsphere.api.config.strategy.ComplexShardingStrategyConfiguration;
import io.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author yaojian
 * @date 2019/7/2
 */
@Configuration
@Slf4j
public class DatasourceConfig {


    @Bean(name = "ds_0")
    @Qualifier("ds_0")
    @ConfigurationProperties("spring.ds0")
    public DataSource zeroDataSource() {
        log.info("初始化数据库ds0");
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "ds_1")
    @Qualifier("ds_1")
    @ConfigurationProperties("spring.ds1")
    public DataSource firstDataSource() {
        log.info("初始化数据库ds1");
        return DataSourceBuilder.create().build();
    }


    @Bean
    @Primary
    public DataSource dataSource() throws SQLException {
        return shardingDataSource();
    }

    private DataSource shardingDataSource() throws SQLException {

        ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();
        Map<String, DataSource> dataSourceMap = new HashMap<>(8);
        dataSourceMap.put("ds_0", zeroDataSource());
        dataSourceMap.put("ds_1", firstDataSource());

        Collection<TableRuleConfiguration> tableRuleConfigs = shardingRuleConfig.getTableRuleConfigs();
        tableRuleConfigs.add(getOrderTableRulesConfiguration());


        return ShardingDataSourceFactory.createDataSource(dataSourceMap,
                shardingRuleConfig, new ConcurrentHashMap<>(), null);


    }


    TableRuleConfiguration getOrderTableRulesConfiguration() {
        TableRuleConfiguration result = new TableRuleConfiguration();
        result.setLogicTable("t_order");
        result.setActualDataNodes("ds_0.t_order_${0..3},ds_1.t_order_${0..3}");
        result.setDatabaseShardingStrategyConfig(new ComplexShardingStrategyConfiguration("id,user_id", new OrderShardingDatabaseAlgorithm()));
        result.setTableShardingStrategyConfig(new ComplexShardingStrategyConfiguration("id,user_id", new OrderShardingTableAlgorithm()));
        return result;
    }




}
