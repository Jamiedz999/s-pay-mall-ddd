package com.jamie.domain.order.service;

import com.jamie.domain.order.model.entity.PayOrderEntity;
import com.jamie.domain.order.model.entity.ShopCartEntity;

public interface IOrderService {

    PayOrderEntity createOrder(ShopCartEntity shopCartEntity) throws Exception;
}
