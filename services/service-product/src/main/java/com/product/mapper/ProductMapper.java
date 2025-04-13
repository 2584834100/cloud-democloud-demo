package com.product.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.product.entity.Product;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ProductMapper extends BaseMapper<Product> {

//    Product selecsstById(Long id);
    Product selectByName(String name);

    int updateStock(Integer id, Integer stock);
}
