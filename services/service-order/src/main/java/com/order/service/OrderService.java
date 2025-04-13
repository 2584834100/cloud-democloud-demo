package com.order.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.order.entity.Order;
import order.dto.OrderDTO;

public interface OrderService extends IService<Order> {

    Integer crateOrder(OrderDTO orderDTO);

    Page<OrderDTO> getOrderPage(OrderDTO orderDTO,int pageNum, int pageSize);

    OrderDTO getOrderById(Integer id);
}
