package com.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.customer.service.CustomerService;
import com.order.entity.Order;
import com.order.mapper.OrderMapper;
import com.order.service.OrderService;
import com.order.service.StreamFunctions;
import com.product.service.ProductService;
import common.BusinessException;
import customer.dto.CustomerDTO;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import order.dto.OrderDTO;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.modelmapper.ModelMapper;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import product.dto.ProductDTO;

import javax.annotation.Resource;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Slf4j
@DubboService
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Resource
    private OrderMapper orderMapper;

    @Resource
    private ModelMapper modelMapper;

    @DubboReference
    private ProductService productService;

    @DubboReference
    private CustomerService customerService;

    @Resource
    private StreamBridge streamBridge;

    @Resource
    private StreamFunctions streamFunctions;

    @Override
    @GlobalTransactional
    public Integer crateOrder(OrderDTO orderDTO) {
        ProductDTO product = productService.getProductById(orderDTO.getProductId());
        log.info("调用的product服务的端口{}", productService.getPort());
        CustomerDTO customer = customerService.getCustomerById(orderDTO.getCustomerId());
        if (Objects.nonNull(product) && Objects.nonNull(customer)) {
            if (product.getStock() > 0) {
                // 修改库存信息
                productService.updateStock(product.getId(), product.getStock() - 1);
                // 创建订单
                Order order = modelMapper.map(orderDTO, Order.class);
                orderMapper.insertRecord(order);
                return order.getId();
            }
            throw new BusinessException("产品库存不足");
        }
        throw new BusinessException("产品或用户信息错误");
    }


    @Override
    public Page<OrderDTO> getOrderPage(OrderDTO orderDTO,int pageNum, int pageSize) {
        // 1. 创建分页对象
        Page<Order> orderPage = new Page<>(pageNum, pageSize);
        // 2. 执行分页查询
        LambdaQueryWrapper<Order> queryWrapper = new LambdaQueryWrapper<>();
        if (Objects.nonNull(orderDTO.getCustomerId())) {
            queryWrapper.eq(Order::getCustomerId, orderDTO.getCustomerId());
        }
        if (Objects.nonNull(orderDTO.getProductId())) {
            queryWrapper.eq(Order::getProductId, orderDTO.getProductId());
        }
        if (Objects.nonNull(orderDTO.getAmount())) {
            queryWrapper.eq(Order::getAmount, orderDTO.getAmount());
        }
        orderMapper.selectPage(orderPage, queryWrapper);
        // 3. 转换为 DTO
        return modelMapper.map(orderPage, Page.class);
    }

    @Override
    public OrderDTO getOrderById(Integer id) {
//        Order order = orderMapper.selectById(id);
//        if (Objects.nonNull(order)) {
//            return modelMapper.map(order, OrderDTO.class);
//        }
//        return null;

//        streamFunctions.orderProducer();
        streamBridge.send("orderProducer-out-0", "test message");
        return orderMapper.selectById2(id);
    }


}
