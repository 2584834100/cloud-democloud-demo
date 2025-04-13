package com.product.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.product.mapper.ProductMapper;
import com.product.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import product.dto.ProductDTO;
import com.product.entity.Product;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

@Slf4j
@DubboService
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {

    @Resource
    private ProductMapper productMapper;

    @Resource
    private ModelMapper modelMapper;

    @Value("${server.port}")
    private Integer port;

    @Override
    public Integer getPort() {
        return port;
    }

    @Override
    public ProductDTO getProductById(Integer id) {
        log.info("当前服务端口{}", port);
        Product product = productMapper.selectById(id);
        if (Objects.nonNull(product)) {
            return modelMapper.map(product, ProductDTO.class);
        }
        return null;
    }

    @Override
    public ProductDTO getProductByName(String name) {
        Product product = productMapper.selectByName(name);
        if (Objects.nonNull(product)) {
            return modelMapper.map(product, ProductDTO.class);
        }
        return null;
    }

    @Override
    public boolean updateStock(Integer productId, Integer stock) {
        return productMapper.updateStock(productId, stock) == 1;
    }

    @Override
    public boolean createProduct(ProductDTO productDTO) {
        return productMapper.insert(modelMapper.map(productDTO, Product.class)) == 1;
    }


}
