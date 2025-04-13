package com.product.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

@Data
@TableName
public class Product {
    @TableId(value = "id", type = IdType.ASSIGN_ID) // 主键注解
    private Integer id;
    private String name;
    private BigDecimal price;
    private Integer stock;
}
