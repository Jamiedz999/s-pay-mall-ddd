package com.jamie.infrastructure.adapter.port;

import com.jamie.domain.order.adapter.port.IProductPort;
import com.jamie.domain.order.model.entity.ProductEntity;
import com.jamie.infrastructure.gateway.ProductRPC;
import com.jamie.infrastructure.gateway.dto.ProductDTO;
import org.springframework.stereotype.Component;

@Component
public class ProductPort implements IProductPort {

    private final ProductRPC productRPC;

    public ProductPort(ProductRPC productRPC) {
        this.productRPC = productRPC;
    }

    @Override
    public ProductEntity queryProducByProductId(String productId) {
        ProductDTO productDTO = productRPC.queryProductByProductId(productId);



        return ProductEntity.builder()
                .productId(productDTO.getProductId())
                .productName(productDTO.getProductName())
                .productDesc(productDTO.getProductDesc())
                .price(productDTO.getPrice())
                .build();
    }
}
