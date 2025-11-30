package com.jamie.domain.order.adapter.repository;

import com.jamie.domain.order.model.aggregate.CreateOrderAggregate;
import com.jamie.domain.order.model.entity.OrderEntity;
import com.jamie.domain.order.model.entity.ShopCartEntity;

public interface IOrderRepository {
    void doSaveOrder(CreateOrderAggregate orderAggregate);

    OrderEntity queryUnpayOrder(ShopCartEntity shopCartEntity);
}
