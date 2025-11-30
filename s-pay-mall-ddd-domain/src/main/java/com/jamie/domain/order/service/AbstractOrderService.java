package com.jamie.domain.order.service;

import com.alipay.api.AlipayApiException;
import com.jamie.domain.order.adapter.port.IProductPort;
import com.jamie.domain.order.adapter.repository.IOrderRepository;
import com.jamie.domain.order.model.aggregate.CreateOrderAggregate;
import com.jamie.domain.order.model.entity.OrderEntity;
import com.jamie.domain.order.model.entity.PayOrderEntity;
import com.jamie.domain.order.model.entity.ProductEntity;
import com.jamie.domain.order.model.entity.ShopCartEntity;
import com.jamie.domain.order.model.valobj.OrderStatusVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;

import java.math.BigDecimal;

@Slf4j

public abstract class AbstractOrderService implements IOrderService{

    protected final IOrderRepository repository;
    protected final IProductPort port;

    public AbstractOrderService(IOrderRepository repository, IProductPort port) {
        this.repository = repository;
        this.port = port;
    }

    @Override
    public PayOrderEntity createOrder(ShopCartEntity shopCartEntity) throws Exception {
        //1,query if current user exists unpay order
        OrderEntity unpaidOrderEntity = repository.queryUnpayOrder(shopCartEntity);

        if(null != unpaidOrderEntity && OrderStatusVO.PAY_WAIT.equals(unpaidOrderEntity.getOrderStatusVO())){
            log.info("create order, unpaid order exists. userId:{} productId: {} orderId:{}", shopCartEntity.getUserId(), shopCartEntity.getProductId(), unpaidOrderEntity.getOrderId());
            return PayOrderEntity.builder()
                    .orderId(unpaidOrderEntity.getOrderId())
                    .payUrl(unpaidOrderEntity.getPayUrl())
                    .build();
        } else if (null != unpaidOrderEntity && OrderStatusVO.CREATE.equals(unpaidOrderEntity.getOrderStatusVO())){
            log.info("create order, create payorder. userId:{} productId: {} orderId:{}", shopCartEntity.getUserId(), shopCartEntity.getProductId(), unpaidOrderEntity.getOrderId());

            PayOrderEntity payOrderEntity = doPrepayOrder(shopCartEntity.getUserId(),shopCartEntity.getProductId(),unpaidOrderEntity.getProductName(),unpaidOrderEntity.getOrderId(),unpaidOrderEntity.getTotalAmount());

            return PayOrderEntity.builder()
                    .orderId(payOrderEntity.getOrderId())
                    .payUrl(payOrderEntity.getPayUrl())
                    .build();
        }

        ProductEntity productEntity =  port.queryProducByProductId(shopCartEntity.getProductId());

        OrderEntity orderEntity = CreateOrderAggregate.buildOrderEntity(productEntity.getProductId(), productEntity.getProductName());

        CreateOrderAggregate orderAggregate = CreateOrderAggregate.builder()
                .userId(shopCartEntity.getUserId())
                .productEntity(productEntity)
                .orderEntity(orderEntity)
                .build();

        this.doSaveOrder(orderAggregate);

        PayOrderEntity payOrderEntity = doPrepayOrder(shopCartEntity.getUserId(),productEntity.getProductId(),productEntity.getProductName(), orderEntity.getOrderId(),productEntity.getPrice());
        log.info("order created, create payorder. userId:{} productId: {} orderId:{}", shopCartEntity.getUserId(), orderEntity.getOrderId(), orderEntity.getOrderId());

        return PayOrderEntity.builder()
                .orderId(orderEntity.getOrderId())
                .payUrl(payOrderEntity.getPayUrl())
                .build();
    }


    protected abstract void doSaveOrder(CreateOrderAggregate orderAggregate);

    protected abstract PayOrderEntity doPrepayOrder(String userId, String productId, String productName, String orderId, BigDecimal totalAmount) throws AlipayApiException;

}
