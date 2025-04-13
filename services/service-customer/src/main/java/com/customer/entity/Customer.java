package com.customer.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName
public class Customer {
    private Integer id;
    private String name;
    private String city;
}
