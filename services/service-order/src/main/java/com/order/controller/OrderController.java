package com.order.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.order.service.OrderService;
import common.Result;
import lombok.extern.slf4j.Slf4j;
import order.dto.OrderDTO;
import org.apache.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Resource
    private OrderService orderService;

    @GetMapping("/getOrderById/{id}")
    public Result<OrderDTO> getOrderById(@PathVariable Integer id) {
        return Result.ok(orderService.getOrderById(id));
    }

    @PostMapping("/crateOrder")
    public Result<Integer> crateOrder(@RequestBody OrderDTO orderDTO,
                                      @RequestHeader("X-User-Name") String name,
                                      @RequestHeader("X-User-Pwd") String pwd) {
        try {
            return Result.ok(orderService.crateOrder(orderDTO));
        } catch (Exception e){
            log.error(e.getMessage(), e);
            return Result.failure(HttpStatus.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @GetMapping("/getOrderPage")
    public Result<Page<OrderDTO>> getOrderPage(@RequestBody OrderDTO orderDTO,
                                               @RequestParam int pageNum,
                                               @RequestParam int pageSize,
                                               @RequestHeader("X-User-Name") String name,
                                               @RequestHeader("X-User-Pwd") String pwd) {
        return Result.ok(orderService.getOrderPage(orderDTO, pageNum, pageSize));
    }
}
