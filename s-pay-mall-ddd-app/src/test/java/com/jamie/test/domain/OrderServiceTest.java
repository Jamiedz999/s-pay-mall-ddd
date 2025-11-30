package com.jamie.test.domain;

import com.alibaba.fastjson.JSON;
import com.jamie.domain.order.model.entity.PayOrderEntity;
import com.jamie.domain.order.model.entity.ShopCartEntity;
import com.jamie.domain.order.service.IOrderService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderServiceTest {

    @Resource
    private IOrderService orderService;

    @Test
    public void test() throws Exception {
        ShopCartEntity shopCartEntity = new ShopCartEntity();
        shopCartEntity.setUserId("Jamie");
        shopCartEntity.setProductId("10001");
        PayOrderEntity payOrderEntity = orderService.createOrder(shopCartEntity);
        log.info("request param: {}", JSON.toJSONString(shopCartEntity));
        log.info("test result: {}", JSON.toJSONString(payOrderEntity));

    }
}
