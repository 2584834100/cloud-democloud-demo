package com.order.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("b_order")
public class Order {
    private Integer id;
    private Integer customerId;
    private Integer productId;
    private BigDecimal amount;
    private Date orderDate;

}
