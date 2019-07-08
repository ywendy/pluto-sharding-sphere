package com.pluto.jdbc01.order.mapper;

import com.pluto.jdbc01.Jdbc01Application;
import com.pluto.jdbc01.order.domain.Order;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.pluto.common.util.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @author yaojian
 * @date 2019/7/2
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Jdbc01Application.class)
public class OrderMapperTest {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private IdGenerator idGenerator;



    @Test
    public void test(){
        long id = idGenerator.nextId(1, 9875642L);

        Order order = new Order();
        order.setId(id);
        order.setStatus("2");
        order.setUserId(9875642L);
        int insert = orderMapper.insert(order);
        System.err.println("result=>"+insert);
    }

    @Test
    public void testFind(){
        long id = idGenerator.nextId(2L, 98L);
        Order order = new Order();
        order.setId(id);
        order.setStatus("2");
        order.setUserId(98L);
        int insert = orderMapper.insert(order);
        System.err.println("result=>"+insert);


        Order result = orderMapper.selectByPrimaryKey(id);
        System.err.println(result);
    }
}