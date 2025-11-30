package com.jamie.infrastructure.gateway;


import com.jamie.infrastructure.gateway.dto.ProductDTO;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service

public class ProductRPC {
    public ProductDTO queryProductByProductId(String product) {

        ProductDTO productVO = new ProductDTO();
        productVO.setProductId(product);
        productVO.setProductName("TestProduct");
        productVO.setProductDesc("This is a test product");
        productVO.setPrice(new BigDecimal("1.68"));
        return productVO;
    }
}
