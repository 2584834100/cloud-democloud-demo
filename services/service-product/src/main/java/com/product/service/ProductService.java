package com.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import product.dto.ProductDTO;
import com.product.entity.Product;


public interface ProductService extends IService<Product> {

    Integer getPort();

    ProductDTO getProductById(Integer productId);

    ProductDTO getProductByName(String name);

    boolean updateStock(Integer productId, Integer stock);

    boolean createProduct(ProductDTO productDTO);
}
