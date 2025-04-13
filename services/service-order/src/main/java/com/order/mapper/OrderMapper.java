package com.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.order.entity.Order;
import order.dto.OrderDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderMapper extends BaseMapper<Order> {

    int insertRecord(Order order);

    List<Order> selectAll();

    OrderDTO selectById2(int id);
}
