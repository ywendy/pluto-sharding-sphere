package com.pluto.jdbc01.config;

import org.pluto.common.util.IdGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author yaojian
 * @date 2019/7/2
 */
@Configuration
public class BeanConfig {


    @Bean
    public IdGenerator idGenerator(){
        return new IdGenerator();
    }

}
