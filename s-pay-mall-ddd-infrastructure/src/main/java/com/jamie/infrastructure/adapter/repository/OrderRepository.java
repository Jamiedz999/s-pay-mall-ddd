package com.jamie.infrastructure.adapter.repository;

import com.jamie.domain.order.adapter.repository.IOrderRepository;
import com.jamie.domain.order.model.aggregate.CreateOrderAggregate;
import com.jamie.domain.order.model.entity.OrderEntity;
import com.jamie.domain.order.model.entity.ProductEntity;
import com.jamie.domain.order.model.entity.ShopCartEntity;
import com.jamie.domain.order.model.valobj.OrderStatusVO;
import com.jamie.infrastructure.dao.IOrderDao;
import com.jamie.infrastructure.dao.po.PayOrder;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Date;

@Repository
public class OrderRepository implements IOrderRepository {

    @Resource
    private IOrderDao orderDao;

    @Override
    public void doSaveOrder(CreateOrderAggregate orderAggregate) {

        String userId = orderAggregate.getUserId();
        ProductEntity productEntity = orderAggregate.getProductEntity();
        OrderEntity orderEntity = orderAggregate.getOrderEntity();

        PayOrder order = new PayOrder();
        order.setUserId(userId);
        order.setProductId(productEntity.getProductId());
        order.setProductName(productEntity.getProductName());
        order.setOrderId(orderEntity.getOrderId());
        order.setOrderTime(orderEntity.getOrderTime());
        order.setTotalAmount(productEntity.getPrice());
        order.setStatus(orderEntity.getOrderStatusVO().getCode());

        orderDao.insert(order);

    }

    @Override
    public OrderEntity queryUnpayOrder(ShopCartEntity shopCartEntity) {
        PayOrder orderReq = new PayOrder();
        orderReq.setUserId(shopCartEntity.getUserId());
        orderReq.setProductId(shopCartEntity.getProductId());


        PayOrder order = orderDao.queryUnPayOrder(orderReq);
        if(null == order) return null;

        return OrderEntity.builder()
                .productId(order.getProductId())
                .productName(order.getProductName())
                .orderId(order.getOrderId())
                .orderStatusVO(OrderStatusVO.valueOf(order.getStatus()))
                .orderTime(order.getOrderTime())
                .totalAmount(order.getTotalAmount())
                .payUrl(order.getPayUrl())
                .build();

    }
}
