package com.jamie.domain.order.adapter.port;

import com.jamie.domain.order.model.entity.ProductEntity;

public interface IProductPort {
    ProductEntity queryProducByProductId(String productId);
}
